package com.sporty.jackpotservice.api;

import com.sporty.jackpotservice.service.RandomNumberGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BetControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Test
    void postBetsTriggersProcessingFlow() throws IOException, InterruptedException {
        String betId = UUID.randomUUID().toString();
        String requestBody = """
                {
                  "betId":"%s",
                  "userId":"user-1",
                  "jackpotId":"JACKPOT-1",
                  "betAmount":100
                }
                """.formatted(betId);

        HttpResponse<String> response = post("/bets", requestBody);

        assertThat(response.statusCode()).isEqualTo(202);
        assertThat(response.body()).contains("\"status\":\"PUBLISHED\"");
        assertThat(response.body()).contains("\"betId\":\"%s\"".formatted(betId));
    }

    @Test
    void evaluateEndpointReturnsExpectedStructure() throws IOException, InterruptedException {
        String betId = UUID.randomUUID().toString();
        String requestBody = """
                {
                  "betId":"%s",
                  "userId":"user-2",
                  "jackpotId":"JACKPOT-1",
                  "betAmount":50
                }
                """.formatted(betId);

        HttpResponse<String> publishResponse = post("/bets", requestBody);
        assertThat(publishResponse.statusCode()).isEqualTo(202);

        HttpResponse<String> evaluateResponse = post("/bets/%s/evaluate".formatted(betId), "");

        assertThat(evaluateResponse.statusCode()).isEqualTo(200);
        assertThat(evaluateResponse.body()).contains("\"betId\":\"%s\"".formatted(betId));
        assertThat(evaluateResponse.body()).contains("\"winner\":");
        assertThat(evaluateResponse.body()).contains("\"rewardAmount\":");
        assertThat(evaluateResponse.body()).contains("\"currentJackpotAmount\":");
    }

    private HttpResponse<String> post(String path, String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:%d%s".formatted(port, path)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        RandomNumberGenerator deterministicRandom() {
            return () -> BigDecimal.valueOf(50);
        }
    }
}
