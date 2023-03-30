package com.siemens.mo.thirtyrails.game;

import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollEntity;
import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollRepository;
import com.siemens.mo.thirtyrails.game.persistence.GameEntity;
import com.siemens.mo.thirtyrails.game.persistence.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final DiceRollRepository diceRollRepository;

    public Game create() {
        GameEntity gameEntity = new GameEntity();
        GameEntity persistedGameEntity = gameRepository.save(gameEntity);
        for (int i = 0; i < 35; i++) {
            var diceRollEntity = DiceRollEntity.of(i + 1, getRandomIntegerBetween0and6(), getRandomIntegerBetween0and6());
            diceRollEntity.setGame(persistedGameEntity);
            diceRollRepository.save(diceRollEntity);
        }
        return persistedGameEntity.asGame();
    }

    private int getRandomIntegerBetween0and6() {
        return (int) Math.round(Math.random() * 5) + 1;
    }

    public Game start(int gameId) {
        var game = gameRepository.findById(gameId).orElseThrow();
        game.setState(GameState.PLAY);
        return gameRepository.save(game).asGame();
    }
}
