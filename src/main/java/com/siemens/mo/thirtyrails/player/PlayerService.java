package com.siemens.mo.thirtyrails.player;

import com.siemens.mo.thirtyrails.game.persistence.GameRepository;
import com.siemens.mo.thirtyrails.map.persistence.MapEntity;
import com.siemens.mo.thirtyrails.map.persistence.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final MapRepository mapRepository;
    private final GameRepository gameRepository;

    @Transactional
    public Player register(int gameId, String name) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.setGame(gameRepository.findById(gameId).orElseThrow());
        mapEntity.setPlayerName(name);
        mapEntity.setTurn(1);
        return mapRepository.save(mapEntity).asPlayer();
    }

    public int nextTurn(int gameId, String playerName) {
        var mapEntity = mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow();
        int turn = mapEntity.getTurn() + 1;
        mapEntity.setTurn(turn);
        mapRepository.save(mapEntity);
        return turn;
    }

    public Player getPlayer(int gameId, String playerName) {
        return mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow().asPlayer();
    }
}
