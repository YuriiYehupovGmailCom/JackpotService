package com.sporty.jackpotservice.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "jackpot_contributions")
public class JackpotContributionEntity {

    @Id
    @Column(name = "bet_id", nullable = false, unique = true)
    private String betId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "jackpot_id", nullable = false)
    private String jackpotId;

    @Column(name = "stake_amount", nullable = false)
    private BigDecimal stakeAmount;

    @Column(name = "contribution_amount", nullable = false)
    private BigDecimal contributionAmount;

    @Column(name = "current_jackpot_amount", nullable = false)
    private BigDecimal currentJackpotAmount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJackpotId() {
        return jackpotId;
    }

    public void setJackpotId(String jackpotId) {
        this.jackpotId = jackpotId;
    }

    public BigDecimal getStakeAmount() {
        return stakeAmount;
    }

    public void setStakeAmount(BigDecimal stakeAmount) {
        this.stakeAmount = stakeAmount;
    }

    public BigDecimal getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(BigDecimal contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    public BigDecimal getCurrentJackpotAmount() {
        return currentJackpotAmount;
    }

    public void setCurrentJackpotAmount(BigDecimal currentJackpotAmount) {
        this.currentJackpotAmount = currentJackpotAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
