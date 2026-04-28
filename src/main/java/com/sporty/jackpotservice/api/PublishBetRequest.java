package com.sporty.jackpotservice.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PublishBetRequest(
        @NotBlank String betId,
        @NotBlank String userId,
        @NotBlank String jackpotId,
        @NotNull @DecimalMin(value = "0.01") BigDecimal betAmount
) {
}
