package com.siemens.mo.thirtyrails.diceroll;

import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollEntity;
import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollRepository;
import com.siemens.mo.thirtyrails.game.persistence.GameRepository;
import com.siemens.mo.thirtyrails.map.persistence.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.siemens.mo.thirtyrails.diceroll.DiceType.RED;
import static com.siemens.mo.thirtyrails.diceroll.DiceType.WHITE;
import static com.siemens.mo.thirtyrails.game.GameState.CLOSED;

@Service
@RequiredArgsConstructor
public class DiceRollService {

    private final DiceRollRepository diceRollRepository;
    private final GameRepository gameRepository;
    private final MapRepository mapRepository;

    public DicePair getDiceRollByPlayer(int gameId, String playerName) {
        var player = mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow();
        return getDiceRollByTurn(gameId, player.getTurn());
    }

    public DicePair getDiceRollByTurn(int gameId, int turn) {
        var game = gameRepository.findById(gameId).orElseThrow();
        if (game.getState() == CLOSED) {
            return new DicePair(new Dice(6, WHITE), new Dice(6, RED));
        }
        return diceRollRepository.findByGameAndNumberOfTurn(game, turn).map(DiceRollEntity::asDicePair).orElseThrow();
    }
}
