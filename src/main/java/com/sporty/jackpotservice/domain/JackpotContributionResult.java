package com.sporty.jackpotservice.domain;

import java.math.BigDecimal;

public record JackpotContributionResult(BigDecimal contributionAmount, BigDecimal currentPoolAmount) {
}
