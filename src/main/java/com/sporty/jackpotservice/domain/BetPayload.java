package com.sporty.jackpotservice.domain;

import java.math.BigDecimal;

public record BetPayload(String betId, String userId, String jackpotId, BigDecimal betAmount) {
}
