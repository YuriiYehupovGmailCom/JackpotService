package com.sporty.jackpotservice.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DefaultRandomNumberGenerator implements RandomNumberGenerator {

    @Override
    public BigDecimal nextPercentage() {
        double raw = ThreadLocalRandom.current().nextDouble(0.0, 100.0);
        return BigDecimal.valueOf(raw).setScale(4, RoundingMode.HALF_UP);
    }
}
