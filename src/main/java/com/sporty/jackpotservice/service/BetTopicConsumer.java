package com.sporty.jackpotservice.service;

import com.sporty.jackpotservice.domain.BetPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BetTopicConsumer {

    private static final Logger log = LoggerFactory.getLogger(BetTopicConsumer.class);

    private final BetProcessingService betProcessingService;

    public BetTopicConsumer(BetProcessingService betProcessingService) {
        this.betProcessingService = betProcessingService;
    }

    public void consume(BetPayload payload) {
        log.info("Consuming bet from topic jackpot-bets: {}", payload);
        betProcessingService.processBet(payload);
    }
}
