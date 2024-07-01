package com.siemens.mo.thirtyrails.map;

import com.siemens.mo.thirtyrails.diceroll.Dice;
import com.siemens.mo.thirtyrails.diceroll.DicePair;
import com.siemens.mo.thirtyrails.diceroll.DiceRollService;
import com.siemens.mo.thirtyrails.game.persistence.GameEntity;
import com.siemens.mo.thirtyrails.game.persistence.GameRepository;
import com.siemens.mo.thirtyrails.map.persistence.MapEntity;
import com.siemens.mo.thirtyrails.map.persistence.MapItemEntity;
import com.siemens.mo.thirtyrails.map.persistence.MapItemRepository;
import com.siemens.mo.thirtyrails.map.persistence.MapRepository;
import com.siemens.mo.thirtyrails.map.track.Crossover;
import com.siemens.mo.thirtyrails.map.track.Curve;
import com.siemens.mo.thirtyrails.map.track.DoubleCurve;
import com.siemens.mo.thirtyrails.map.track.LeftJunction;
import com.siemens.mo.thirtyrails.map.track.RightJunction;
import com.siemens.mo.thirtyrails.map.track.Straight;
import com.siemens.mo.thirtyrails.map.track.TrackItem;
import com.siemens.mo.thirtyrails.map.track.YJunction;
import com.siemens.mo.thirtyrails.player.PlayerService;
import com.siemens.mo.thirtyrails.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.siemens.mo.thirtyrails.diceroll.DiceType.RED;
import static com.siemens.mo.thirtyrails.diceroll.DiceType.WHITE;
import static com.siemens.mo.thirtyrails.game.GameState.PLAY;
import static com.siemens.mo.thirtyrails.map.Rotation.NONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MapSer1viceTest {

    @Mock
    private MapRepository mapRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private DiceRollService diceRollService;
    @Mock
    private PlayerService playerService;
    @Mock
    private MapItemRepository mapItemRepository;

    @Test
    void shouldAllowAnyPositionIfRowAndColAreFull() {
        // Given
        MapService mapService = new MapService(mapRepository, gameRepository, mapItemRepository, playerService, diceRollService);
        int gameId = 42;
        String playerName = "readyPlayerOne";

        GameEntity gameEntity = new GameEntity();
        gameEntity.setState(PLAY);

        when(gameRepository.findById(eq(gameId))).thenReturn(Optional.of(gameEntity));
        when(diceRollService.getDiceRollByPlayer(eq(gameId), eq(playerName))).thenReturn(
                Optional.of(new DicePair(new Dice(3, WHITE), new Dice(3, RED)))
        );
        MapEntity mapEntity = new MapEntity();
        mapEntity.setRedDiceOverrode(true);
        mapEntity.setWhiteDiceOverrode(true);
        when(mapRepository.findByGameIdAndPlayerName(eq(gameId), eq(playerName))).thenReturn(Optional.of(mapEntity));

        when(mapItemRepository.findByMap(eq(mapEntity))).thenReturn(List.of(
                mapItemEntity(2, 1, Mountain.class.getName()),
                mapItemEntity(1, 2, Mountain.class.getName()),
                mapItemEntity(3, 4, Mountain.class.getName()),
                mapItemEntity(5, 5, Mountain.class.getName()),
                mapItemEntity(2, 6, Mountain.class.getName()),
                mapItemEntity(1, 3, Mine.class.getName()),
                mapItemEntity(1, 0, "Station1"),
                mapItemEntity(0, 2, "Station2"),
                mapItemEntity(3, 7, "Station3"),
                mapItemEntity(7, 4, "Station4"),

                mapItemEntity(1, 1, Straight.class.getName()),
                mapItemEntity(3, 1, Straight.class.getName()),
                mapItemEntity(4, 1, Curve.class.getName()),
                mapItemEntity(5, 1, Curve.class.getName()),
                mapItemEntity(2, 2, YJunction.class.getName()),
                mapItemEntity(3, 2, YJunction.class.getName()),
                mapItemEntity(4, 2, LeftJunction.class.getName()),
                mapItemEntity(5, 2, Crossover.class.getName()),
                mapItemEntity(6, 2, DoubleCurve.class.getName()),
                mapItemEntity(2, 3, YJunction.class.getName()),
                mapItemEntity(3, 3, YJunction.class.getName()),
                mapItemEntity(4, 3, Straight.class.getName()),
                mapItemEntity(5, 3, Crossover.class.getName()),
                mapItemEntity(6, 3, YJunction.class.getName()),
                mapItemEntity(1, 4, DoubleCurve.class.getName()),
                mapItemEntity(2, 4, Straight.class.getName()),
                mapItemEntity(4, 4, Crossover.class.getName()),
                mapItemEntity(5, 4, DoubleCurve.class.getName()),
                mapItemEntity(6, 4, YJunction.class.getName()),
                mapItemEntity(1, 5, Straight.class.getName()),
                mapItemEntity(2, 5, DoubleCurve.class.getName()),
                mapItemEntity(3, 5, DoubleCurve.class.getName()),
                mapItemEntity(6, 5, Straight.class.getName()),
                mapItemEntity(3, 6, RightJunction.class.getName()),
                mapItemEntity(4, 6, Straight.class.getName()),
                mapItemEntity(5, 6, Crossover.class.getName())
        ));

        when(mapItemRepository.findByMapAndXAndY(eq(mapEntity), anyInt(), anyInt())).thenReturn(Optional.empty());

        // When
        mapService.setTrack(gameId, playerName, new DoubleCurve(new Position(6, 6), NONE), false);
    }

    private MapItemEntity mapItemEntity(int x, int y, String itemName) {
        MapItemEntity mapItemEntity = new MapItemEntity();
        mapItemEntity.setX(x);
        mapItemEntity.setY(y);
        mapItemEntity.setRotation(NONE);
        mapItemEntity.setType(itemName);
        return mapItemEntity;
    }
}
