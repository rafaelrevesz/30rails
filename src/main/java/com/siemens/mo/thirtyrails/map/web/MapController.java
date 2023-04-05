package com.siemens.mo.thirtyrails.map.web;

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

        mapService.setTrack(gameId, playerName, TrackItemFactory.get(trackDto));
        return ResponseEntity.noContent().build();
    }


}
