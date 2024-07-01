package com.siemens.mo.thirtyrails.game;

import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollEntity;
import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollRepository;
import com.siemens.mo.thirtyrails.game.persistence.GameEntity;
import com.siemens.mo.thirtyrails.game.persistence.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final DiceRollRepository diceRollRepository;

    public Game create() {
        Random randomGenerator = new Random(System.currentTimeMillis());
        GameEntity gameEntity = new GameEntity();
        GameEntity persistedGameEntity = gameRepository.save(gameEntity);
        for (int i = 0; i < 36; i++) {
            var diceRollEntity = DiceRollEntity.of(i + 1, randomGenerator.nextInt(6) + 1, randomGenerator.nextInt(6) + 1);
            diceRollEntity.setGame(persistedGameEntity);
            diceRollRepository.save(diceRollEntity);
        }
        return persistedGameEntity.asGame();
    }

    public Game start(int gameId) {
        var game = gameRepository.findById(gameId).orElseThrow();
        game.setState(GameState.PLAY);
        return gameRepository.save(game).asGame();
    }
}
