package com.sporty.jackpotservice.service;

import com.sporty.jackpotservice.domain.ContributionType;
import com.sporty.jackpotservice.domain.strategy.ContributionStrategy;
import com.sporty.jackpotservice.domain.strategy.FixedContributionStrategy;
import com.sporty.jackpotservice.domain.strategy.VariableContributionStrategy;
import org.springframework.stereotype.Component;

@Component
public class ContributionStrategyResolver {

    private final FixedContributionStrategy fixedContributionStrategy;
    private final VariableContributionStrategy variableContributionStrategy;

    public ContributionStrategyResolver(FixedContributionStrategy fixedContributionStrategy,
                                        VariableContributionStrategy variableContributionStrategy) {
        this.fixedContributionStrategy = fixedContributionStrategy;
        this.variableContributionStrategy = variableContributionStrategy;
    }

    public ContributionStrategy resolve(ContributionType type) {
        return switch (type) {
            case FIXED -> fixedContributionStrategy;
            case VARIABLE -> variableContributionStrategy;
        };
    }
}
