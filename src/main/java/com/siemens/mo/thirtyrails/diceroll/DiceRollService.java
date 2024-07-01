package com.siemens.mo.thirtyrails.diceroll;

import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollEntity;
import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollRepository;
import com.siemens.mo.thirtyrails.game.persistence.GameRepository;
import com.siemens.mo.thirtyrails.map.persistence.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.siemens.mo.thirtyrails.diceroll.DiceType.RED;
import static com.siemens.mo.thirtyrails.diceroll.DiceType.WHITE;
import static com.siemens.mo.thirtyrails.game.GameState.CLOSED;

@Service
@RequiredArgsConstructor
public class DiceRollService {

    private final DiceRollRepository diceRollRepository;
    private final GameRepository gameRepository;
    private final MapRepository mapRepository;

    public Optional<DicePair> getDiceRollByPlayer(int gameId, String playerName) {
        var player = mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow();
        return getDiceRollByTurn(gameId, player.getTurn());
    }

    public Optional<DicePair> getDiceRollByTurn(int gameId, int turn) {
        var game = gameRepository.findById(gameId).orElseThrow();
        if (game.getState() == CLOSED) {
            return Optional.of(new DicePair(new Dice(6, WHITE), new Dice(6, RED)));
        }
        return diceRollRepository.findByGameAndNumberOfTurn(game, turn).map(DiceRollEntity::asDicePair);
    }

    public List<DicePair> getDiceRollsByGame(int gameId) {
        var game = gameRepository.findById(gameId).orElseThrow();
        return diceRollRepository.findByGame(game).stream()
                .map(DiceRollEntity::asDicePair)
                .toList();
    }
}
