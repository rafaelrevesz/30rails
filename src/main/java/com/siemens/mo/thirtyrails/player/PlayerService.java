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

    public void nextTurn(int gameId, String playerName) {
        var mapEntity = mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow();
        mapEntity.setTurn(mapEntity.getTurn() + 1);
        mapRepository.save(mapEntity);
    }

    public Player getPlayer(int gameId, String playerName) {
        return mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow().asPlayer();
    }

    public void skipTurn(int gameId, String playerName) {
        var mapEntity = mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow();
        if (mapEntity.isMountainSkipped()) {
            throw new IllegalStateException("A mountain was already skipped");
        }
        if (mapEntity.getTurn() > 6) {
            throw new IllegalStateException("Mountain setup is already done");
        }
        mapEntity.setTurn(mapEntity.getTurn() + 1);
        mapEntity.setMountainSkipped(true);
        mapRepository.save(mapEntity);
    }

}
