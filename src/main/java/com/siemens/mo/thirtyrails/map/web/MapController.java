package com.siemens.mo.thirtyrails.map.web;

import com.siemens.mo.thirtyrails.map.Map;
import com.siemens.mo.thirtyrails.map.MapService;
import com.siemens.mo.thirtyrails.svg.SvgDrawer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> mapImage(@PathVariable Integer gameId,
                                           @PathVariable String playerName) {

        Map map = mapService.findByGameIdAndPlayerName(gameId, playerName);
        return ResponseEntity.ok(svgDrawer.drawSvg(map));
    }

}
