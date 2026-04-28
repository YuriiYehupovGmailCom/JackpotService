package com.sporty.jackpotservice.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "jackpot_rewards")
public class JackpotRewardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bet_id", nullable = false)
    private String betId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "jackpot_id", nullable = false)
    private String jackpotId;

    @Column(name = "jackpot_reward_amount", nullable = false)
    private BigDecimal jackpotRewardAmount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Long getId() {
        return id;
    }

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

    public BigDecimal getJackpotRewardAmount() {
        return jackpotRewardAmount;
    }

    public void setJackpotRewardAmount(BigDecimal jackpotRewardAmount) {
        this.jackpotRewardAmount = jackpotRewardAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
