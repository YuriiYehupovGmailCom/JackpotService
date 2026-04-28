package com.sporty.jackpotservice.strategy;

import com.sporty.jackpotservice.domain.strategy.FixedContributionStrategy;
import com.sporty.jackpotservice.domain.strategy.VariableContributionStrategy;
import com.sporty.jackpotservice.persistence.JackpotEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ContributionStrategyTest {

    private final FixedContributionStrategy fixedContributionStrategy = new FixedContributionStrategy();
    private final VariableContributionStrategy variableContributionStrategy = new VariableContributionStrategy();

    @Test
    void fixedContributionCalculatesByPercentage() {
        JackpotEntity jackpot = new JackpotEntity();
        jackpot.setFixedContributionPercentage(BigDecimal.valueOf(5));

        BigDecimal result = fixedContributionStrategy.calculateContribution(BigDecimal.valueOf(200), jackpot);

        assertThat(result).isEqualByComparingTo("10.00");
    }

    @Test
    void variableContributionStartsHigherThenDropsUntilFloor() {
        JackpotEntity jackpot = new JackpotEntity();
        jackpot.setCurrentPoolAmount(BigDecimal.valueOf(1000));
        jackpot.setVariableContributionBasePercentage(BigDecimal.valueOf(10));
        jackpot.setVariableContributionDecreaseRatePercentage(BigDecimal.ONE);
        jackpot.setVariableContributionFloorPercentage(BigDecimal.valueOf(2));
        jackpot.setVariableContributionDecreaseStep(BigDecimal.valueOf(200));

        BigDecimal result = variableContributionStrategy.calculateContribution(BigDecimal.valueOf(100), jackpot);

        assertThat(result).isEqualByComparingTo("5.00");
    }
}
