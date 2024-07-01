package com.siemens.mo.thirtyrails.map.web;

import com.siemens.mo.thirtyrails.diceroll.DiceRollService;
import com.siemens.mo.thirtyrails.map.Map;
import com.siemens.mo.thirtyrails.map.MapService;
import com.siemens.mo.thirtyrails.map.track.TrackItemFactory;
import com.siemens.mo.thirtyrails.position.Position;
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

    @GetMapping(value = "/image", produces = "image/svg+xml")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> mapImage(@PathVariable Integer gameId,
                                           @PathVariable String playerName) {

        Map map = mapService.findByGameIdAndPlayerName(gameId, playerName);
        return ResponseEntity.ok(svgDrawer.drawSvg(map, 1000, 1000));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> setTrack(@PathVariable Integer gameId,
                                         @PathVariable String playerName,
                                         @RequestBody @Valid TrackDto trackDto) {

        mapService.setTrack(gameId, playerName, TrackItemFactory.get(trackDto), trackDto.override());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/mountain")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> setMountain(@PathVariable Integer gameId,
                                            @PathVariable String playerName,
                                            @RequestBody @Valid Position position) {

        mapService.addMountain(gameId, playerName, position);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/mountain/skip")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> skipMountain(@PathVariable Integer gameId,
                                            @PathVariable String playerName) {

        mapService.skipMountain(gameId, playerName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/mine")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> setMine(@PathVariable Integer gameId,
                                        @PathVariable String playerName,
                                        @RequestBody @Valid Position position) {

        mapService.setMine(gameId, playerName, position);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bonus")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> setBonus(@PathVariable Integer gameId,
                                         @PathVariable String playerName,
                                         @RequestBody @Valid Position position) {

        mapService.setBonus(gameId, playerName, position);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/station")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> setStation(@PathVariable Integer gameId,
                                           @PathVariable String playerName,
                                           @RequestBody @Valid StationDto stationDto) {

        mapService.setStation(gameId, playerName, stationDto.stationId(), stationDto.orientation(), stationDto.position());
        return ResponseEntity.noContent().build();
    }

}
