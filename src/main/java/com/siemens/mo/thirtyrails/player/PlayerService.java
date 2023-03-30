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
        MapEntity playerEntity = new MapEntity();
        playerEntity.setGame(gameRepository.findById(gameId).orElseThrow());
        playerEntity.setPlayerName(name);
        playerEntity.setTurn(1);
        return mapRepository.save(playerEntity).asPlayer();
    }

    public void nextTurn(int gameId, String playerName) {
        var player = mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow();
        player.setTurn(player.getTurn() + 1);
        mapRepository.save(player);
    }

    public Player getPlayer(int gameId, String playerName) {
        return mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow().asPlayer();
    }

    public void skipTurn(int gameId, String playerName) {
        var player = mapRepository.findByGameIdAndPlayerName(gameId, playerName).orElseThrow();
        if (player.isMountainSkipped()) {
            throw new IllegalStateException("A mountain was already skipped");
        }
        if (player.getTurn() > 6) {
            throw new IllegalStateException("Mountain setup is already done");
        }
        player.setTurn(player.getTurn() + 1);
        player.setMountainSkipped(true);
        mapRepository.save(player);
    }
}
