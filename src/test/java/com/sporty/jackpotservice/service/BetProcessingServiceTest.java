package com.sporty.jackpotservice.service;

import com.sporty.jackpotservice.domain.ContributionType;
import com.sporty.jackpotservice.domain.RewardType;
import com.sporty.jackpotservice.domain.strategy.FixedContributionStrategy;
import com.sporty.jackpotservice.domain.strategy.FixedRewardStrategy;
import com.sporty.jackpotservice.domain.strategy.VariableContributionStrategy;
import com.sporty.jackpotservice.domain.strategy.VariableRewardStrategy;
import com.sporty.jackpotservice.persistence.JackpotContributionEntity;
import com.sporty.jackpotservice.persistence.JackpotContributionRepository;
import com.sporty.jackpotservice.persistence.JackpotEntity;
import com.sporty.jackpotservice.persistence.JackpotRepository;
import com.sporty.jackpotservice.persistence.JackpotRewardRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BetProcessingServiceTest {

    @Test
    void jackpotResetsAfterWin() {
        JackpotRepository jackpotRepository = Mockito.mock(JackpotRepository.class);
        JackpotContributionRepository contributionRepository = Mockito.mock(JackpotContributionRepository.class);
        JackpotRewardRepository rewardRepository = Mockito.mock(JackpotRewardRepository.class);
        RandomNumberGenerator random = () -> BigDecimal.ZERO;

        JackpotEntity jackpot = new JackpotEntity();
        jackpot.setJackpotId("J1");
        jackpot.setInitialPoolAmount(BigDecimal.valueOf(100));
        jackpot.setCurrentPoolAmount(BigDecimal.valueOf(150));
        jackpot.setContributionType(ContributionType.FIXED);
        jackpot.setRewardType(RewardType.FIXED);
        jackpot.setFixedRewardChancePercentage(BigDecimal.valueOf(100));

        JackpotContributionEntity contribution = new JackpotContributionEntity();
        contribution.setBetId("B1");
        contribution.setUserId("U1");
        contribution.setJackpotId("J1");
        contribution.setStakeAmount(BigDecimal.TEN);
        contribution.setContributionAmount(BigDecimal.ONE);
        contribution.setCurrentJackpotAmount(BigDecimal.valueOf(150));
        contribution.setCreatedAt(Instant.now());

        when(contributionRepository.findById("B1")).thenReturn(Optional.of(contribution));
        when(jackpotRepository.findById("J1")).thenReturn(Optional.of(jackpot));
        when(jackpotRepository.save(any(JackpotEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BetProcessingService service = new BetProcessingService(
                jackpotRepository,
                contributionRepository,
                rewardRepository,
                new ContributionStrategyResolver(new FixedContributionStrategy(), new VariableContributionStrategy()),
                new RewardStrategyResolver(new FixedRewardStrategy(), new VariableRewardStrategy()),
                random
        );

        var result = service.evaluateReward("B1");

        assertThat(result.winner()).isTrue();
        assertThat(result.rewardAmount()).isEqualByComparingTo("150.00");
        assertThat(result.currentJackpotAmount()).isEqualByComparingTo("100.00");
        assertThat(jackpot.getCurrentPoolAmount()).isEqualByComparingTo("100.00");
    }
}
