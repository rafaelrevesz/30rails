package com.siemens.mo.thirtyrails.game;

import com.siemens.mo.thirtyrails.diceroll.DicePair;
import com.siemens.mo.thirtyrails.diceroll.DiceRollService;
import com.siemens.mo.thirtyrails.map.MapService;
import com.siemens.mo.thirtyrails.player.PlayerService;
import com.siemens.mo.thirtyrails.position.Position;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import static com.siemens.mo.thirtyrails.station.StationOrientation.BOTTOM;
import static com.siemens.mo.thirtyrails.station.StationOrientation.LEFT;
import static com.siemens.mo.thirtyrails.station.StationOrientation.RIGHT;
import static com.siemens.mo.thirtyrails.station.StationOrientation.TOP;

@Configuration
@RequiredArgsConstructor
public class GameInitConfiguration {

    private final GameService gameService;
    private final PlayerService playerService;
    private final DiceRollService diceRollService;
    private final MapService mapService;

    @PostConstruct
    public void setup() {
        var game = gameService.create();
        var player = playerService.register(game.getId(), "player1");
        gameService.start(game.getId());
        DicePair roll = diceRollService.getDiceRollByPlayer(game.getId(), player.getName());

        mapService.addMountain(game.getId(), player.getName(), new Position(roll.whiteDice().getValue(), 1));
        roll = diceRollService.getDiceRollByPlayer(game.getId(), player.getName());
        Position mountPosition = new Position(roll.whiteDice().getValue(), 2);
        mapService.addMountain(game.getId(), player.getName(), mountPosition);

        playerService.skipTurn(game.getId(), player.getName());

        roll = diceRollService.getDiceRollByPlayer(game.getId(), player.getName());
        mapService.addMountain(game.getId(), player.getName(), new Position(roll.whiteDice().getValue(), 4));
        roll = diceRollService.getDiceRollByPlayer(game.getId(), player.getName());
        mapService.addMountain(game.getId(), player.getName(), new Position(roll.whiteDice().getValue(), 5));
        roll = diceRollService.getDiceRollByPlayer(game.getId(), player.getName());
        mapService.addMountain(game.getId(), player.getName(), new Position(roll.whiteDice().getValue(), 6));

        mapService.setMine(game.getId(), player.getName(), new Position(mountPosition.col(), 3));
        mapService.setBonus(game.getId(), player.getName(), new Position(mountPosition.col() == 4 ? 3 : 4,3));

        mapService.setStation(game.getId(), player.getName(), 3, BOTTOM, 3);
        mapService.setStation(game.getId(), player.getName(), 2, LEFT, 2);
        mapService.setStation(game.getId(), player.getName(), 1, TOP, 1);
        mapService.setStation(game.getId(), player.getName(), 4, RIGHT, 4);

    }
}
