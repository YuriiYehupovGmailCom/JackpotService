package com.sporty.jackpotservice.domain.strategy;

import com.sporty.jackpotservice.persistence.JackpotEntity;

import java.math.BigDecimal;

public interface ContributionStrategy {

    BigDecimal calculateContribution(BigDecimal betAmount, JackpotEntity jackpot);
}
