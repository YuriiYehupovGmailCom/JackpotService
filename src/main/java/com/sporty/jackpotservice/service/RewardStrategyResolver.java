package com.sporty.jackpotservice.service;

import com.sporty.jackpotservice.domain.RewardType;
import com.sporty.jackpotservice.domain.strategy.FixedRewardStrategy;
import com.sporty.jackpotservice.domain.strategy.RewardStrategy;
import com.sporty.jackpotservice.domain.strategy.VariableRewardStrategy;
import org.springframework.stereotype.Component;

@Component
public class RewardStrategyResolver {

    private final FixedRewardStrategy fixedRewardStrategy;
    private final VariableRewardStrategy variableRewardStrategy;

    public RewardStrategyResolver(FixedRewardStrategy fixedRewardStrategy,
                                  VariableRewardStrategy variableRewardStrategy) {
        this.fixedRewardStrategy = fixedRewardStrategy;
        this.variableRewardStrategy = variableRewardStrategy;
    }

    public RewardStrategy resolve(RewardType type) {
        return switch (type) {
            case FIXED -> fixedRewardStrategy;
            case VARIABLE -> variableRewardStrategy;
        };
    }
}
