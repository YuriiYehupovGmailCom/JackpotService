package com.sporty.jackpotservice.kafka;

import com.sporty.jackpotservice.domain.BetPayload;
import com.sporty.jackpotservice.service.BetProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class KafkaBetProducer implements BetProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaBetProducer.class);
    private static final String TOPIC = "jackpot-bets";

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public KafkaBetProducer(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(BetPayload payload) {
        try {
            var result = kafkaTemplate.send(TOPIC, payload.betId(), payload).get(10, TimeUnit.SECONDS);
            log.info(
                    "Published bet {} to Kafka topic {} partition {} offset {}",
                    payload.betId(),
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to publish bet to Kafka topic " + TOPIC, e);
        }
    }
}
