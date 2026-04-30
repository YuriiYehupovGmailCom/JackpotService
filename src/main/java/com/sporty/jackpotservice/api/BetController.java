package com.sporty.jackpotservice.api;

import com.sporty.jackpotservice.domain.BetPayload;
import com.sporty.jackpotservice.domain.JackpotRewardResult;
import com.sporty.jackpotservice.service.BetProcessingService;
import com.sporty.jackpotservice.service.BetProducer;
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

    private final BetProducer betProducer;
    private final BetProcessingService betProcessingService;

    public BetController(BetProducer betProducer, BetProcessingService betProcessingService) {
        this.betProducer = betProducer;
        this.betProcessingService = betProcessingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PublishBetResponse publishBet(@Valid @RequestBody PublishBetRequest request) {
        BetPayload payload = new BetPayload(request.betId(), request.userId(), request.jackpotId(), request.betAmount());
        betProducer.publish(payload);
        return new PublishBetResponse("PUBLISHED", request.betId());
    }

    @PostMapping("/{betId}/evaluate")
    public RewardEvaluationResponse evaluateBet(@PathVariable String betId) {
        JackpotRewardResult result = betProcessingService.evaluateReward(betId);
        return new RewardEvaluationResponse(betId, result.winner(), result.rewardAmount());
    }
}
