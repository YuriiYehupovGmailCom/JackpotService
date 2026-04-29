package com.sporty.jackpotservice.api;

import java.math.BigDecimal;

public record RewardEvaluationResponse(String betId, boolean winner, BigDecimal rewardAmount) {
}
