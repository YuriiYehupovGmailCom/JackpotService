package com.sporty.jackpotservice.service;

import com.sporty.jackpotservice.domain.BetPayload;

public interface BetProducer {

    void publish(BetPayload payload);
}
