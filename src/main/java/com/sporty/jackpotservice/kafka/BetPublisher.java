package com.sporty.jackpotservice.kafka;

import com.sporty.jackpotservice.domain.BetPayload;

public interface BetPublisher {

    void publish(BetPayload payload);
}
