package com.siemens.mo.thirtyrails.game.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Integer> {
}
