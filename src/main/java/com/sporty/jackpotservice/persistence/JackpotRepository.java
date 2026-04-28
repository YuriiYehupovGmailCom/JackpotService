package com.sporty.jackpotservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JackpotRepository extends JpaRepository<JackpotEntity, String> {
}
