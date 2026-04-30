package com.sporty.jackpotservice.kafka;

import com.sporty.jackpotservice.domain.BetPayload;
import com.sporty.jackpotservice.service.BetProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaBetConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaBetConsumer.class);

    private final BetProcessingService betProcessingService;

    public KafkaBetConsumer(BetProcessingService betProcessingService) {
        this.betProcessingService = betProcessingService;
    }

    @KafkaListener(topics = "jackpot-bets")
    public void consume(BetPayload payload) {
        log.info("Received bet {} from Kafka", payload.betId());
        betProcessingService.processBet(payload);
    }
}
