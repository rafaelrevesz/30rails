package com.siemens.mo.thirtyrails.diceroll.persistence;

import com.siemens.mo.thirtyrails.game.persistence.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiceRollRepository extends JpaRepository<DiceRollEntity, Integer> {

    Optional<DiceRollEntity> findByGameAndNumberOfTurn(GameEntity game, int numberOfTurn);
    List<DiceRollEntity> findByGame(GameEntity game);
}
