package com.sporty.jackpotservice.strategy;

import com.sporty.jackpotservice.domain.strategy.FixedRewardStrategy;
import com.sporty.jackpotservice.domain.strategy.VariableRewardStrategy;
import com.sporty.jackpotservice.persistence.JackpotEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class RewardStrategyTest {

    private final FixedRewardStrategy fixedRewardStrategy = new FixedRewardStrategy();
    private final VariableRewardStrategy variableRewardStrategy = new VariableRewardStrategy();

    @Test
    void fixedRewardReturnsConfiguredChance() {
        JackpotEntity jackpot = new JackpotEntity();
        jackpot.setFixedRewardChancePercentage(BigDecimal.valueOf(7.5));

        assertThat(fixedRewardStrategy.calculateChancePercentage(jackpot)).isEqualByComparingTo("7.5");
    }

    @Test
    void variableRewardIncreasesWithPoolAndCapsAtHundred() {
        JackpotEntity jackpot = new JackpotEntity();
        jackpot.setCurrentPoolAmount(BigDecimal.valueOf(1000));
        jackpot.setVariableRewardBaseChancePercentage(BigDecimal.valueOf(1));
        jackpot.setVariableRewardIncreaseRatePercentage(BigDecimal.valueOf(0.5));
        jackpot.setVariableRewardIncreaseStep(BigDecimal.valueOf(100));
        jackpot.setVariableRewardGuaranteedPoolLimit(BigDecimal.valueOf(2000));

        assertThat(variableRewardStrategy.calculateChancePercentage(jackpot)).isEqualByComparingTo("6.0");

        jackpot.setCurrentPoolAmount(BigDecimal.valueOf(2000));
        assertThat(variableRewardStrategy.calculateChancePercentage(jackpot)).isEqualByComparingTo("100");
    }
}
