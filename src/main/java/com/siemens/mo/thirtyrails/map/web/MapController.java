package com.siemens.mo.thirtyrails.map.web;

import com.siemens.mo.thirtyrails.diceroll.Dice;
import com.siemens.mo.thirtyrails.diceroll.DicePair;
import com.siemens.mo.thirtyrails.diceroll.DiceRollService;
import com.siemens.mo.thirtyrails.map.Map;
import com.siemens.mo.thirtyrails.map.MapService;
import com.siemens.mo.thirtyrails.map.track.TrackItemFactory;
import com.siemens.mo.thirtyrails.svg.SvgDrawer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/games/{gameId}/players/{playerName}/map")
@RequiredArgsConstructor
public class MapController {

    private final SvgDrawer svgDrawer;
    private final MapService mapService;
    private final DiceRollService diceRollService;

    @GetMapping("/image")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> mapImage(@PathVariable Integer gameId,
                                           @PathVariable String playerName) {

        Map map = mapService.findByGameIdAndPlayerName(gameId, playerName);
        return ResponseEntity.ok(svgDrawer.drawSvg(map));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> setTrack(@PathVariable Integer gameId,
                                         @PathVariable String playerName,
                                         @RequestBody @Valid TrackDto trackDto) {

        DicePair dicePair = diceRollService.getDiceRollByPlayer(gameId, playerName);
        if (!isSelectedTrackValidForTheRedDice(trackDto.type(), dicePair.redDice())) {
            throw new IllegalArgumentException("Invalid track type, check the red dice");
        }

        mapService.setTrack(gameId, playerName, TrackItemFactory.get(trackDto));
        return ResponseEntity.noContent().build();
    }

    private boolean isSelectedTrackValidForTheRedDice(TrackType trackType, Dice redDice) {
        return switch (trackType) {
            case CURVE -> redDice.getValue() == 1;
            case STRAIGHT -> redDice.getValue() == 2;
            case DOUBLE_CURVE -> redDice.getValue() == 3;
            case CROSSOVER -> redDice.getValue() == 4;
            case Y_JUNCTION -> redDice.getValue() == 5;
            case RIGHT_JUNCTION, LEFT_JUNCTION -> redDice.getValue() == 6;
        };
    }

}
