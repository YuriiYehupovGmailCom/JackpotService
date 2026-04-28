package com.sporty.jackpotservice.domain.strategy;

import com.sporty.jackpotservice.persistence.JackpotEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class VariableRewardStrategy implements RewardStrategy {

    @Override
    public BigDecimal calculateChancePercentage(JackpotEntity jackpot) {
        if (jackpot.getCurrentPoolAmount().compareTo(jackpot.getVariableRewardGuaranteedPoolLimit()) >= 0) {
            return BigDecimal.valueOf(100);
        }

        BigDecimal steps = jackpot.getCurrentPoolAmount()
                .divide(jackpot.getVariableRewardIncreaseStep(), 8, RoundingMode.DOWN);

        BigDecimal chance = jackpot.getVariableRewardBaseChancePercentage()
                .add(jackpot.getVariableRewardIncreaseRatePercentage().multiply(steps));

        return chance.min(BigDecimal.valueOf(100));
    }
}
