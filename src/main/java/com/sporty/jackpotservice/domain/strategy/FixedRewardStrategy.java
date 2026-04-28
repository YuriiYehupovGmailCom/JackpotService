package com.sporty.jackpotservice.domain.strategy;

import com.sporty.jackpotservice.persistence.JackpotEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FixedRewardStrategy implements RewardStrategy {

    @Override
    public BigDecimal calculateChancePercentage(JackpotEntity jackpot) {
        return jackpot.getFixedRewardChancePercentage();
    }
}
