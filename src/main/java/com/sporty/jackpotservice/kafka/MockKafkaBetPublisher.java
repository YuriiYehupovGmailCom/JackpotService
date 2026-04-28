package com.sporty.jackpotservice.kafka;

import com.sporty.jackpotservice.domain.BetPayload;
import com.sporty.jackpotservice.service.BetTopicConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockKafkaBetPublisher implements BetPublisher {

    private static final Logger log = LoggerFactory.getLogger(MockKafkaBetPublisher.class);

    private final BetTopicConsumer betTopicConsumer;

    public MockKafkaBetPublisher(BetTopicConsumer betTopicConsumer) {
        this.betTopicConsumer = betTopicConsumer;
    }

    @Override
    public void publish(BetPayload payload) {
        log.info("Publishing bet to topic jackpot-bets: {}", payload);
        betTopicConsumer.consume(payload);
    }
}
