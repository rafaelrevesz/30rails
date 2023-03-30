package com.siemens.mo.thirtyrails.map;

import com.siemens.mo.thirtyrails.diceroll.DiceRollService;
import com.siemens.mo.thirtyrails.game.GameState;
import com.siemens.mo.thirtyrails.game.persistence.GameRepository;
import com.siemens.mo.thirtyrails.map.persistence.MapEntity;
import com.siemens.mo.thirtyrails.map.persistence.MapItemEntity;
import com.siemens.mo.thirtyrails.map.persistence.MapItemRepository;
import com.siemens.mo.thirtyrails.map.persistence.MapRepository;
import com.siemens.mo.thirtyrails.player.Player;
import com.siemens.mo.thirtyrails.player.PlayerService;
import com.siemens.mo.thirtyrails.player.persistence.PlayerRepository;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.station.StationOrientation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.siemens.mo.thirtyrails.station.StationOrientation.BOTTOM;
import static com.siemens.mo.thirtyrails.station.StationOrientation.LEFT;
import static com.siemens.mo.thirtyrails.station.StationOrientation.RIGHT;
import static com.siemens.mo.thirtyrails.station.StationOrientation.TOP;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final MapItemRepository mapItemRepository;
    private final PlayerService playerService;
    private final DiceRollService diceRollService;

    @Transactional
    public void create(int gameId, String playerName) {
        var playerEntity = playerRepository.findByGameIdAndName(gameId, playerName).orElseThrow();
        MapEntity mapEntity = new MapEntity();
        mapEntity.setPlayer(playerEntity);
        mapRepository.save(mapEntity);
        playerEntity.setMap(mapEntity);
        log.info("Saved");
    }

    @Transactional
    public void addMountain(int gameId, String playerName, Position position) {
        var game = gameRepository.findById(gameId).orElseThrow();
        if (game.getState() != GameState.PLAY) {
            throw new IllegalStateException("Game is not active");
        }
        MapEntity map = getMapByGameIdAnPlayerName(gameId, playerName);
        if (isMountainSetupReady(map)) {
            throw new IllegalStateException("Mountain setup is already done");
        }
        Player player = playerService.getPlayer(gameId, playerName);
        if (position.row() != player.getTurn()) {
            throw new IllegalArgumentException("Position corresponds to the wrong turn");
        }
        var dicePair = diceRollService.getDiceRollByPlayer(gameId, playerName);
        if (position.row() != dicePair.bwDice().getValue() && position.col() != dicePair.bwDice().getValue()) {
            throw new IllegalArgumentException("Dice roll must be used");
        }
        var mapItem = new MapItemEntity();
        mapItem.setMap(map);
        mapItem.setX(position.col());
        mapItem.setY(position.row());
        mapItem.setType(Mountain.class.getName());
        mapItemRepository.save(mapItem);
        playerService.nextTurn(gameId, playerName);
    }

    @Transactional
    public void setMine(int gameId, String playerName, Position position) {
        var game = gameRepository.findById(gameId).orElseThrow();
        if (game.getState() != GameState.PLAY) {
            throw new IllegalStateException("Game is not active");
        }
        MapEntity map = getMapByGameIdAnPlayerName(gameId, playerName);
        if (!isMountainSetupReady(map)) {
            throw new IllegalStateException("Mountain setup is still missing");
        }
        if (isMineSetupReady(map)) {
            throw new IllegalStateException("Mine setup is already done");
        }
        var field = mapItemRepository.findByMapAndXAndY(map, position.col(), position.row());
        if (field.isPresent()) {
            throw new IllegalArgumentException("Field is already set by " + field.get().getType());
        }
        var mapItem = new MapItemEntity();
        mapItem.setMap(map);
        mapItem.setX(position.col());
        mapItem.setY(position.row());
        mapItem.setType(Mine.class.getName());
        mapItemRepository.save(mapItem);
    }

    @Transactional
    public void setBonus(int gameId, String playerName, Position position) {
        var game = gameRepository.findById(gameId).orElseThrow();
        if (game.getState() != GameState.PLAY) {
            throw new IllegalStateException("Game is not active");
        }
        MapEntity map = getMapByGameIdAnPlayerName(gameId, playerName);
        if (!isMineSetupReady(map)) {
            throw new IllegalStateException("Mine setup is still missing");
        }
        if (isBonusSetupReady(map)) {
            throw new IllegalStateException("Bonus setup is already done");
        }
        var field = mapItemRepository.findByMapAndXAndY(map, position.col(), position.row());
        if (field.isPresent()) {
            throw new IllegalArgumentException("Field is already set by " + field.get().getType());
        }
        var mapItem = new MapItemEntity();
        mapItem.setMap(map);
        mapItem.setX(position.col());
        mapItem.setY(position.row());
        mapItem.setType("Bonus");
        mapItemRepository.save(mapItem);
    }

    @Transactional
    public void setStation(int gameId, String playerName, int stationId, StationOrientation orientation, int sidePosition) {
        var game = gameRepository.findById(gameId).orElseThrow();
        if (game.getState() != GameState.PLAY) {
            throw new IllegalStateException("Game is not active");
        }
        MapEntity map = getMapByGameIdAnPlayerName(gameId, playerName);
        if (!isBonusSetupReady(map)) {
            throw new IllegalStateException("Bonus setup is still missing");
        }
        if (isStationSetupReady(map)) {
            throw new IllegalStateException("Station setup is already done");
        }
        Position position;
        if (orientation == LEFT) {
            position = new Position(0, sidePosition);
        } else if (orientation == TOP) {
            position = new Position(sidePosition, 0);
        } else if (orientation == RIGHT) {
            position = new Position(7, sidePosition);
        } else if (orientation == BOTTOM) {
            position = new Position(sidePosition, 7);
        } else {
            throw new IllegalArgumentException("Unsupported station orientation: " + orientation);
        }
        if (isStationSet(map, stationId, orientation, position)) {
            throw new IllegalArgumentException("Station or side is already set");
        }
        var mapItem = new MapItemEntity();
        mapItem.setMap(map);
        mapItem.setX(position.col());
        mapItem.setY(position.row());
        mapItem.setType("Station" + stationId);
        mapItemRepository.save(mapItem);
    }

    private MapEntity getMapByGameIdAnPlayerName(int gameId, String playerName) {
        var player = playerRepository.findByGameIdAndName(gameId, playerName).orElseThrow();
        var map = mapRepository.findByPlayer(player).orElseThrow();
        return map;
    }

    private boolean isMountainSetupReady(MapEntity map) {
        var mountainCount = mapItemRepository.findByMap(map).stream().filter(mapItem -> mapItem.getType().equals(Mountain.class.getName())).count();
        return mountainCount == 5;
    }

    private boolean isMineSetupReady(MapEntity map) {
        var mineCount = mapItemRepository.findByMap(map).stream().filter(mapItem -> mapItem.getType().equals(Mine.class.getName())).count();
        return mineCount == 1;
    }

    private boolean isBonusSetupReady(MapEntity map) {
        var bonusCount = mapItemRepository.findByMap(map).stream().filter(mapItem -> mapItem.getType().equals("Bonus")).count();
        return bonusCount == 1;
    }

    private boolean isStationSet(MapEntity map, int stationId, StationOrientation orientation, Position position) {
        List<MapItemEntity> mapItems = mapItemRepository.findByMap(map);
        if (mapItems.stream().filter(mapItem -> mapItem.getType().equals("Station" + stationId)).count() == 1) {
            return true;
        }
        if (orientation == BOTTOM || orientation == TOP) {
            if (mapItems.stream().filter(mapItem -> mapItem.getY() == position.row()).count() == 1) {
                return true;
            }
        } else {
            if (mapItems.stream().filter(mapItem -> mapItem.getX() == position.col()).count() == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean isStationSetupReady(MapEntity map) {
        var stationCount = mapItemRepository.findByMap(map).stream().filter(mapItem -> mapItem.getType().startsWith("Station")).count();
        return stationCount == 4;
    }

    public Map findByGameIdAndPlayerName(int gameId, String playerName) {
        var mapItems = mapItemRepository.findByMap(getMapByGameIdAnPlayerName(gameId, playerName));
        Map map = new Map();
        for (MapItemEntity mapItem : mapItems) {
            if (mapItem.getType().equals(Mountain.class.getName())) {
                map.addMountain(mapItem.asPosition());
            } else if (mapItem.getType().equals(Mine.class.getName())) {
                map.setMine(mapItem.asPosition());
            } else if (mapItem.getType().equals("Bonus")) {
                map.setBonus(mapItem.asPosition());
            } else if (mapItem.getType().startsWith("Station")) {
                map.setStation(mapItem.asPosition(), Integer.parseInt(mapItem.getType().replace("Station", "")));
            }
        }
        return map;
    }
}
