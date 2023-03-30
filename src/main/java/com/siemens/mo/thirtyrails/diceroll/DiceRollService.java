package com.siemens.mo.thirtyrails.diceroll;

import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollEntity;
import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollRepository;
import com.siemens.mo.thirtyrails.game.persistence.GameRepository;
import com.siemens.mo.thirtyrails.map.persistence.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiceRollService {

    private final DiceRollRepository diceRollRepository;
    private final GameRepository gameRepository;
    private final MapRepository mapRepository;

    public DicePair getDiceRollByPlayer(int gameId, String playerName) {
        var game = gameRepository.findById(gameId).orElseThrow();
        var player = mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow();
        return diceRollRepository.findByGameAndNumberOfTurn(game, player.getTurn()).map(DiceRollEntity::asDicePair).orElseThrow();
    }
}
