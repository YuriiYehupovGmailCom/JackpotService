package com.sporty.jackpotservice.domain;

import java.math.BigDecimal;

public record JackpotRewardResult(boolean winner, BigDecimal rewardAmount, BigDecimal currentJackpotAmount) {
}
