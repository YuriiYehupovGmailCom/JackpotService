package com.sporty.jackpotservice.domain.strategy;

import com.sporty.jackpotservice.persistence.JackpotEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class VariableContributionStrategy implements ContributionStrategy {

    @Override
    public BigDecimal calculateContribution(BigDecimal betAmount, JackpotEntity jackpot) {
        BigDecimal steps = jackpot.getCurrentPoolAmount()
                .divide(jackpot.getVariableContributionDecreaseStep(), 8, RoundingMode.DOWN);

        BigDecimal dynamicPercentage = jackpot.getVariableContributionBasePercentage()
                .subtract(jackpot.getVariableContributionDecreaseRatePercentage().multiply(steps));

        BigDecimal effectivePercentage = dynamicPercentage.max(jackpot.getVariableContributionFloorPercentage());

        return betAmount.multiply(effectivePercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
