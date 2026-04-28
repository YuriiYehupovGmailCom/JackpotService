package com.sporty.jackpotservice.domain.strategy;

import com.sporty.jackpotservice.persistence.JackpotEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class FixedContributionStrategy implements ContributionStrategy {

    @Override
    public BigDecimal calculateContribution(BigDecimal betAmount, JackpotEntity jackpot) {
        return betAmount
                .multiply(jackpot.getFixedContributionPercentage())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
