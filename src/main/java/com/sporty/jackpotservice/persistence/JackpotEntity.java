package com.sporty.jackpotservice.persistence;

import com.sporty.jackpotservice.domain.ContributionType;
import com.sporty.jackpotservice.domain.RewardType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "jackpots")
public class JackpotEntity {

    @Id
    @Column(name = "jackpot_id", nullable = false, unique = true)
    private String jackpotId;

    @Column(name = "initial_pool_amount", nullable = false)
    private BigDecimal initialPoolAmount;

    @Column(name = "current_pool_amount", nullable = false)
    private BigDecimal currentPoolAmount;

    @Column(name = "contribution_type", nullable = false)
    private ContributionType contributionType;

    @Column(name = "fixed_contribution_percentage")
    private BigDecimal fixedContributionPercentage;

    @Column(name = "variable_contribution_base_percentage")
    private BigDecimal variableContributionBasePercentage;

    @Column(name = "variable_contribution_decrease_rate_percentage")
    private BigDecimal variableContributionDecreaseRatePercentage;

    @Column(name = "variable_contribution_floor_percentage")
    private BigDecimal variableContributionFloorPercentage;

    @Column(name = "variable_contribution_decrease_step")
    private BigDecimal variableContributionDecreaseStep;

    @Column(name = "reward_type", nullable = false)
    private RewardType rewardType;

    @Column(name = "fixed_reward_chance_percentage")
    private BigDecimal fixedRewardChancePercentage;

    @Column(name = "variable_reward_base_chance_percentage")
    private BigDecimal variableRewardBaseChancePercentage;

    @Column(name = "variable_reward_increase_rate_percentage")
    private BigDecimal variableRewardIncreaseRatePercentage;

    @Column(name = "variable_reward_increase_step")
    private BigDecimal variableRewardIncreaseStep;

    @Column(name = "variable_reward_guaranteed_pool_limit")
    private BigDecimal variableRewardGuaranteedPoolLimit;

    public String getJackpotId() {
        return jackpotId;
    }

    public void setJackpotId(String jackpotId) {
        this.jackpotId = jackpotId;
    }

    public BigDecimal getInitialPoolAmount() {
        return initialPoolAmount;
    }

    public void setInitialPoolAmount(BigDecimal initialPoolAmount) {
        this.initialPoolAmount = initialPoolAmount;
    }

    public BigDecimal getCurrentPoolAmount() {
        return currentPoolAmount;
    }

    public void setCurrentPoolAmount(BigDecimal currentPoolAmount) {
        this.currentPoolAmount = currentPoolAmount;
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
