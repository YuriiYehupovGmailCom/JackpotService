package com.sporty.jackpotservice.config;

import com.sporty.jackpotservice.domain.ContributionType;
import com.sporty.jackpotservice.domain.RewardType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "jackpot.defaults")
public class JackpotDefaultsProperties {

    private List<JackpotConfig> jackpots = new ArrayList<>();

    public List<JackpotConfig> getJackpots() {
        return jackpots;
    }

    public void setJackpots(List<JackpotConfig> jackpots) {
        this.jackpots = jackpots;
    }

    public static class JackpotConfig {
        private String id;
        private BigDecimal initialPoolAmount;
        private ContributionType contributionType;
        private BigDecimal fixedContributionPercentage;
        private BigDecimal variableContributionBasePercentage;
        private BigDecimal variableContributionDecreaseRatePercentage;
        private BigDecimal variableContributionFloorPercentage;
        private BigDecimal variableContributionDecreaseStep;
        private RewardType rewardType;
        private BigDecimal fixedRewardChancePercentage;
        private BigDecimal variableRewardBaseChancePercentage;
        private BigDecimal variableRewardIncreaseRatePercentage;
        private BigDecimal variableRewardIncreaseStep;
        private BigDecimal variableRewardGuaranteedPoolLimit;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public BigDecimal getInitialPoolAmount() {
            return initialPoolAmount;
        }

        public void setInitialPoolAmount(BigDecimal initialPoolAmount) {
            this.initialPoolAmount = initialPoolAmount;
        }

        public ContributionType getContributionType() {
            return contributionType;
        }

        public void setContributionType(ContributionType contributionType) {
            this.contributionType = contributionType;
        }

        public BigDecimal getFixedContributionPercentage() {
            return fixedContributionPercentage;
        }

        public void setFixedContributionPercentage(BigDecimal fixedContributionPercentage) {
            this.fixedContributionPercentage = fixedContributionPercentage;
        }

        public BigDecimal getVariableContributionBasePercentage() {
            return variableContributionBasePercentage;
        }

        public void setVariableContributionBasePercentage(BigDecimal variableContributionBasePercentage) {
            this.variableContributionBasePercentage = variableContributionBasePercentage;
        }

        public BigDecimal getVariableContributionDecreaseRatePercentage() {
            return variableContributionDecreaseRatePercentage;
        }

        public void setVariableContributionDecreaseRatePercentage(BigDecimal variableContributionDecreaseRatePercentage) {
            this.variableContributionDecreaseRatePercentage = variableContributionDecreaseRatePercentage;
        }

        public BigDecimal getVariableContributionFloorPercentage() {
            return variableContributionFloorPercentage;
        }

        public void setVariableContributionFloorPercentage(BigDecimal variableContributionFloorPercentage) {
            this.variableContributionFloorPercentage = variableContributionFloorPercentage;
        }

        public BigDecimal getVariableContributionDecreaseStep() {
            return variableContributionDecreaseStep;
        }

        public void setVariableContributionDecreaseStep(BigDecimal variableContributionDecreaseStep) {
            this.variableContributionDecreaseStep = variableContributionDecreaseStep;
        }

        public RewardType getRewardType() {
            return rewardType;
        }

        public void setRewardType(RewardType rewardType) {
            this.rewardType = rewardType;
        }

        public BigDecimal getFixedRewardChancePercentage() {
            return fixedRewardChancePercentage;
        }

        public void setFixedRewardChancePercentage(BigDecimal fixedRewardChancePercentage) {
            this.fixedRewardChancePercentage = fixedRewardChancePercentage;
        }

        public BigDecimal getVariableRewardBaseChancePercentage() {
            return variableRewardBaseChancePercentage;
        }

        public void setVariableRewardBaseChancePercentage(BigDecimal variableRewardBaseChancePercentage) {
            this.variableRewardBaseChancePercentage = variableRewardBaseChancePercentage;
        }

        public BigDecimal getVariableRewardIncreaseRatePercentage() {
            return variableRewardIncreaseRatePercentage;
        }

        public void setVariableRewardIncreaseRatePercentage(BigDecimal variableRewardIncreaseRatePercentage) {
            this.variableRewardIncreaseRatePercentage = variableRewardIncreaseRatePercentage;
        }

        public BigDecimal getVariableRewardIncreaseStep() {
            return variableRewardIncreaseStep;
        }

        public void setVariableRewardIncreaseStep(BigDecimal variableRewardIncreaseStep) {
            this.variableRewardIncreaseStep = variableRewardIncreaseStep;
        }

        public BigDecimal getVariableRewardGuaranteedPoolLimit() {
            return variableRewardGuaranteedPoolLimit;
        }

        public void setVariableRewardGuaranteedPoolLimit(BigDecimal variableRewardGuaranteedPoolLimit) {
            this.variableRewardGuaranteedPoolLimit = variableRewardGuaranteedPoolLimit;
        }
    }
}
