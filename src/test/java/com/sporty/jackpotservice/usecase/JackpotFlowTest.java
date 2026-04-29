package com.sporty.jackpotservice.usecase;

import com.sporty.jackpotservice.domain.BetPayload;
import com.sporty.jackpotservice.domain.ContributionType;
import com.sporty.jackpotservice.domain.RewardType;
import com.sporty.jackpotservice.domain.JackpotContributionResult;
import com.sporty.jackpotservice.domain.JackpotRewardResult;
import com.sporty.jackpotservice.domain.strategy.FixedContributionStrategy;
import com.sporty.jackpotservice.domain.strategy.FixedRewardStrategy;
import com.sporty.jackpotservice.domain.strategy.VariableContributionStrategy;
import com.sporty.jackpotservice.domain.strategy.VariableRewardStrategy;
import com.sporty.jackpotservice.persistence.JackpotContributionEntity;
import com.sporty.jackpotservice.persistence.JackpotContributionRepository;
import com.sporty.jackpotservice.persistence.JackpotEntity;
import com.sporty.jackpotservice.persistence.JackpotRewardRepository;
import com.sporty.jackpotservice.persistence.JackpotRepository;
import com.sporty.jackpotservice.service.BetProcessingService;
import com.sporty.jackpotservice.service.ContributionStrategyResolver;
import com.sporty.jackpotservice.service.RandomNumberGenerator;
import com.sporty.jackpotservice.service.RewardStrategyResolver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class JackpotFlowTest {

    @Test
    void shouldWinWhenGuaranteedLimitReached() {
        AtomicReference<JackpotEntity> jackpotRef = new AtomicReference<>(buildJackpot(
                "J1",
                BigDecimal.valueOf(100), // initial pool
                BigDecimal.valueOf(140)  // current pool (close to limit)
        ));

        Map<String, JackpotContributionEntity> contributions = new HashMap<>();

        // Deterministic: random is always <= chance (when chance == 100 we always win).
        RandomNumberGenerator randomNumberGenerator = () -> BigDecimal.valueOf(50);

        BetProcessingService service = createService(jackpotRef, contributions, randomNumberGenerator);

        // Given: pool is 140, limit is 150, and fixed contribution for betAmount=10 is +10 => currentPool becomes 150.
        BetPayload bet = new BetPayload("B1", "U1", "J1", BigDecimal.valueOf(10));

        JackpotContributionResult contributionResult = service.processBet(bet);
        assertThat(contributionResult.currentPoolAmount()).isEqualByComparingTo("150.00");

        // When: evaluating reward for the contributing bet.
        JackpotRewardResult result = service.evaluateReward("B1");

        // Then: guaranteed win; reward equals pool before reset; jackpot is reset to initial pool.
        assertThat(result.winner()).isTrue();
        assertThat(result.rewardAmount()).isEqualByComparingTo("150.00");
        assertThat(result.currentJackpotAmount()).isEqualByComparingTo("100.00");
        assertThat(jackpotRef.get().getCurrentPoolAmount()).isEqualByComparingTo("100.00");
    }

    @Test
    void shouldAccumulatePoolAndWinOnThirdBet() {
        AtomicReference<JackpotEntity> jackpotRef = new AtomicReference<>(buildJackpot(
                "J1",
                BigDecimal.valueOf(100), // initial pool
                BigDecimal.valueOf(120)  // current pool (needs 3 bets to reach limit)
        ));

        Map<String, JackpotContributionEntity> contributions = new HashMap<>();

        // Deterministic: random is always <= chance (when chance == 100 we always win).
        RandomNumberGenerator randomNumberGenerator = () -> BigDecimal.valueOf(50);

        BetProcessingService service = createService(jackpotRef, contributions, randomNumberGenerator);

        // Given: limit is 150; fixed contribution percentage is 100% so contribution == betAmount.
        // Current pool starts at 120 => after bet1+bet2+bet3 (+10 each) we hit 150.
        service.processBet(new BetPayload("B1", "U1", "J1", BigDecimal.valueOf(10)));
        service.processBet(new BetPayload("B2", "U2", "J1", BigDecimal.valueOf(10)));
        service.processBet(new BetPayload("B3", "U3", "J1", BigDecimal.valueOf(10)));

        assertThat(jackpotRef.get().getCurrentPoolAmount()).isEqualByComparingTo("150.00");

        // When: rewards are evaluated after all three bets were published.
        JackpotRewardResult firstResult = service.evaluateReward("B1");
        JackpotRewardResult secondResult = service.evaluateReward("B2");
        JackpotRewardResult thirdResult = service.evaluateReward("B3");

        // Then: only the third bet reaches the guaranteed limit and resets the jackpot.
        assertThat(firstResult.winner()).isFalse();
        assertThat(firstResult.rewardAmount()).isEqualByComparingTo("0.00");
        assertThat(firstResult.currentJackpotAmount()).isEqualByComparingTo("150.00");
        assertThat(secondResult.winner()).isFalse();
        assertThat(secondResult.rewardAmount()).isEqualByComparingTo("0.00");
        assertThat(secondResult.currentJackpotAmount()).isEqualByComparingTo("150.00");
        assertThat(thirdResult.winner()).isTrue();
        assertThat(thirdResult.rewardAmount()).isEqualByComparingTo("150.00");
        assertThat(thirdResult.currentJackpotAmount()).isEqualByComparingTo("100.00");
        assertThat(jackpotRef.get().getCurrentPoolAmount()).isEqualByComparingTo("100.00");
    }

    private static JackpotEntity buildJackpot(String jackpotId, BigDecimal initialPool, BigDecimal currentPool) {
        JackpotEntity jackpot = new JackpotEntity();
        jackpot.setJackpotId(jackpotId);
        jackpot.setInitialPoolAmount(initialPool.setScale(2, RoundingMode.HALF_UP));
        jackpot.setCurrentPoolAmount(currentPool.setScale(2, RoundingMode.HALF_UP));

        jackpot.setContributionType(ContributionType.FIXED);
        jackpot.setFixedContributionPercentage(BigDecimal.valueOf(100)); // contribution == betAmount

        jackpot.setRewardType(RewardType.VARIABLE);
        jackpot.setVariableRewardGuaranteedPoolLimit(BigDecimal.valueOf(150));

        // Not used for guaranteed path (>= limit), but set for completeness.
        jackpot.setVariableRewardBaseChancePercentage(BigDecimal.ZERO);
        jackpot.setVariableRewardIncreaseRatePercentage(BigDecimal.valueOf(0));
        jackpot.setVariableRewardIncreaseStep(BigDecimal.valueOf(1));

        return jackpot;
    }

    private static BetProcessingService createService(
            AtomicReference<JackpotEntity> jackpotRef,
            Map<String, JackpotContributionEntity> contributions,
            RandomNumberGenerator randomNumberGenerator
    ) {
        JackpotRepository jackpotRepository = Mockito.mock(JackpotRepository.class);
        when(jackpotRepository.findById("J1")).thenAnswer(inv -> Optional.of(jackpotRef.get()));
        when(jackpotRepository.save(any(JackpotEntity.class))).thenAnswer(inv -> {
            JackpotEntity saved = inv.getArgument(0);
            jackpotRef.set(saved);
            return saved;
        });

        JackpotContributionRepository contributionRepository = Mockito.mock(JackpotContributionRepository.class);
        when(contributionRepository.save(any(JackpotContributionEntity.class))).thenAnswer(inv -> {
            JackpotContributionEntity saved = inv.getArgument(0);
            contributions.put(saved.getBetId(), saved);
            return saved;
        });
        when(contributionRepository.findById(any())).thenAnswer(inv -> {
            String betId = inv.getArgument(0);
            return Optional.ofNullable(contributions.get(betId));
        });

        JackpotRewardRepository rewardRepository = Mockito.mock(JackpotRewardRepository.class);
        when(rewardRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        return new BetProcessingService(
                jackpotRepository,
                contributionRepository,
                rewardRepository,
                new ContributionStrategyResolver(
                        new FixedContributionStrategy(),
                        new VariableContributionStrategy()
                ),
                new RewardStrategyResolver(
                        new FixedRewardStrategy(),
                        new VariableRewardStrategy()
                ),
                randomNumberGenerator
        );
    }
}
