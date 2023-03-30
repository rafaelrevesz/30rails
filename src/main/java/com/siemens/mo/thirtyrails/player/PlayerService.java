package com.siemens.mo.thirtyrails.player;

import com.siemens.mo.thirtyrails.game.persistence.GameRepository;
import com.siemens.mo.thirtyrails.player.persistence.PlayerEntity;
import com.siemens.mo.thirtyrails.player.persistence.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    @Transactional
    public Player register(int gameId, String name) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setGame(gameRepository.findById(gameId).orElseThrow());
        playerEntity.setName(name);
        playerEntity.setTurn(1);
        return playerRepository.save(playerEntity).asPlayer();
    }

    public void nextTurn(int gameId, String playerName) {
        var player = playerRepository.findByGameIdAndName(gameId, playerName).orElseThrow();
        player.setTurn(player.getTurn() + 1);
        playerRepository.save(player);
    }

    public Player getPlayer(int gameId, String playerName) {
        return playerRepository.findByGameIdAndName(gameId, playerName).orElseThrow().asPlayer();
    }

    public void skipTurn(int gameId, String playerName) {
        var player = playerRepository.findByGameIdAndName(gameId, playerName).orElseThrow();
        if (player.isMountainSkipped()) {
            throw new IllegalStateException("A mountain was already skipped");
        }
        if (player.getTurn() > 6) {
            throw new IllegalStateException("Mountain setup is already done");
        }
        player.setTurn(player.getTurn() + 1);
        player.setMountainSkipped(true);
        playerRepository.save(player);
    }
}
