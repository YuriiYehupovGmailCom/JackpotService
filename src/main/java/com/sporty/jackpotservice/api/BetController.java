package com.sporty.jackpotservice.api;

import com.sporty.jackpotservice.domain.BetPayload;
import com.sporty.jackpotservice.domain.JackpotRewardResult;
import com.sporty.jackpotservice.kafka.BetPublisher;
import com.sporty.jackpotservice.service.BetProcessingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bets")
public class BetController {

    private final BetPublisher betPublisher;
    private final BetProcessingService betProcessingService;

    public BetController(BetPublisher betPublisher, BetProcessingService betProcessingService) {
        this.betPublisher = betPublisher;
        this.betProcessingService = betProcessingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PublishBetResponse publishBet(@Valid @RequestBody PublishBetRequest request) {
        BetPayload payload = new BetPayload(request.betId(), request.userId(), request.jackpotId(), request.betAmount());
        betPublisher.publish(payload);
        return new PublishBetResponse("PUBLISHED", request.betId());
    }

    @PostMapping("/{betId}/evaluate")
    public RewardEvaluationResponse evaluateBet(@PathVariable String betId) {
        JackpotRewardResult result = betProcessingService.evaluateReward(betId);
        return new RewardEvaluationResponse(betId, result.winner(), result.rewardAmount(), result.currentJackpotAmount());
    }
}
