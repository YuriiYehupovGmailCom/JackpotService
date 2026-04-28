package com.sporty.jackpotservice.config;

import com.sporty.jackpotservice.persistence.JackpotEntity;
import com.sporty.jackpotservice.persistence.JackpotRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;

@Configuration
@EnableConfigurationProperties(JackpotDefaultsProperties.class)
public class JackpotBootstrapConfig {

    private final JackpotDefaultsProperties defaults;
    private final JackpotRepository jackpotRepository;

    public JackpotBootstrapConfig(JackpotDefaultsProperties defaults, JackpotRepository jackpotRepository) {
        this.defaults = defaults;
        this.jackpotRepository = jackpotRepository;
    }

    @PostConstruct
    public void seedJackpots() {
        if (!jackpotRepository.findAll().isEmpty()) {
            return;
        }

        var entities = new ArrayList<JackpotEntity>();
        for (JackpotDefaultsProperties.JackpotConfig config : defaults.getJackpots()) {
            JackpotEntity entity = new JackpotEntity();
            entity.setJackpotId(config.getId());
            entity.setInitialPoolAmount(config.getInitialPoolAmount());
            entity.setCurrentPoolAmount(config.getInitialPoolAmount());
            entity.setContributionType(config.getContributionType());
            entity.setFixedContributionPercentage(config.getFixedContributionPercentage());
            entity.setVariableContributionBasePercentage(config.getVariableContributionBasePercentage());
            entity.setVariableContributionDecreaseRatePercentage(config.getVariableContributionDecreaseRatePercentage());
            entity.setVariableContributionFloorPercentage(config.getVariableContributionFloorPercentage());
            entity.setVariableContributionDecreaseStep(config.getVariableContributionDecreaseStep());
            entity.setRewardType(config.getRewardType());
            entity.setFixedRewardChancePercentage(config.getFixedRewardChancePercentage());
            entity.setVariableRewardBaseChancePercentage(config.getVariableRewardBaseChancePercentage());
            entity.setVariableRewardIncreaseRatePercentage(config.getVariableRewardIncreaseRatePercentage());
            entity.setVariableRewardIncreaseStep(config.getVariableRewardIncreaseStep());
            entity.setVariableRewardGuaranteedPoolLimit(config.getVariableRewardGuaranteedPoolLimit());
            entities.add(entity);
        }

        jackpotRepository.saveAll(entities);
    }
}
