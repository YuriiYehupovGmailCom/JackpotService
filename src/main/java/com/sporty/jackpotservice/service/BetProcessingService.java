package com.sporty.jackpotservice.service;

import com.sporty.jackpotservice.domain.BetPayload;
import com.sporty.jackpotservice.domain.JackpotContributionResult;
import com.sporty.jackpotservice.domain.JackpotRewardResult;
import com.sporty.jackpotservice.persistence.JackpotContributionEntity;
import com.sporty.jackpotservice.persistence.JackpotContributionRepository;
import com.sporty.jackpotservice.persistence.JackpotEntity;
import com.sporty.jackpotservice.persistence.JackpotRepository;
import com.sporty.jackpotservice.persistence.JackpotRewardEntity;
import com.sporty.jackpotservice.persistence.JackpotRewardRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Service
public class BetProcessingService {

    private final JackpotRepository jackpotRepository;
    private final JackpotContributionRepository jackpotContributionRepository;
    private final JackpotRewardRepository jackpotRewardRepository;
    private final ContributionStrategyResolver contributionStrategyResolver;
    private final RewardStrategyResolver rewardStrategyResolver;
    private final RandomNumberGenerator randomNumberGenerator;

    public BetProcessingService(JackpotRepository jackpotRepository,
                                JackpotContributionRepository jackpotContributionRepository,
                                JackpotRewardRepository jackpotRewardRepository,
                                ContributionStrategyResolver contributionStrategyResolver,
                                RewardStrategyResolver rewardStrategyResolver,
                                RandomNumberGenerator randomNumberGenerator) {
        this.jackpotRepository = jackpotRepository;
        this.jackpotContributionRepository = jackpotContributionRepository;
        this.jackpotRewardRepository = jackpotRewardRepository;
        this.contributionStrategyResolver = contributionStrategyResolver;
        this.rewardStrategyResolver = rewardStrategyResolver;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    @Transactional
    public JackpotContributionResult processBet(BetPayload payload) {
        JackpotEntity jackpot = jackpotRepository.findById(payload.jackpotId())
                .orElseThrow(() -> new IllegalArgumentException("Jackpot not found for id: " + payload.jackpotId()));

        BigDecimal contribution = contributionStrategyResolver.resolve(jackpot.getContributionType())
                .calculateContribution(payload.betAmount(), jackpot)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal currentPool = jackpot.getCurrentPoolAmount().add(contribution).setScale(2, RoundingMode.HALF_UP);
        jackpot.setCurrentPoolAmount(currentPool);
        jackpotRepository.save(jackpot);

        JackpotContributionEntity contributionEntity = new JackpotContributionEntity();
        contributionEntity.setBetId(payload.betId());
        contributionEntity.setUserId(payload.userId());
        contributionEntity.setJackpotId(payload.jackpotId());
        contributionEntity.setStakeAmount(payload.betAmount().setScale(2, RoundingMode.HALF_UP));
        contributionEntity.setContributionAmount(contribution);
        contributionEntity.setCurrentJackpotAmount(currentPool);
        contributionEntity.setCreatedAt(Instant.now());
        jackpotContributionRepository.save(contributionEntity);

        return new JackpotContributionResult(contribution, currentPool);
    }

    @Transactional
    public JackpotRewardResult evaluateReward(String betId) {
        JackpotContributionEntity contribution = jackpotContributionRepository.findById(betId)
                .orElseThrow(() -> new IllegalArgumentException("Contributing bet not found for id: " + betId));

        JackpotEntity jackpot = jackpotRepository.findById(contribution.getJackpotId())
                .orElseThrow(() -> new IllegalStateException("Jackpot not found for id: " + contribution.getJackpotId()));

        BigDecimal chance = rewardStrategyResolver.resolve(jackpot.getRewardType()).calculateChancePercentage(jackpot);
        BigDecimal random = randomNumberGenerator.nextPercentage();
        boolean winner = random.compareTo(chance) <= 0;

        BigDecimal rewardAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        if (winner) {
            rewardAmount = jackpot.getCurrentPoolAmount().setScale(2, RoundingMode.HALF_UP);

            JackpotRewardEntity rewardEntity = new JackpotRewardEntity();
            rewardEntity.setBetId(contribution.getBetId());
            rewardEntity.setUserId(contribution.getUserId());
            rewardEntity.setJackpotId(contribution.getJackpotId());
            rewardEntity.setJackpotRewardAmount(rewardAmount);
            rewardEntity.setCreatedAt(Instant.now());
            jackpotRewardRepository.save(rewardEntity);

            jackpot.setCurrentPoolAmount(jackpot.getInitialPoolAmount().setScale(2, RoundingMode.HALF_UP));
            jackpotRepository.save(jackpot);
        }

        return new JackpotRewardResult(winner, rewardAmount, jackpot.getCurrentPoolAmount().setScale(2, RoundingMode.HALF_UP));
    }
}
