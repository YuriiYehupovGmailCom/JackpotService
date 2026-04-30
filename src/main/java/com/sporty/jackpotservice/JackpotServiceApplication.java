package com.sporty.jackpotservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class JackpotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JackpotServiceApplication.class, args);
    }

}
