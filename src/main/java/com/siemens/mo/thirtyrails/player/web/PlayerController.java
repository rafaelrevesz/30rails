package com.siemens.mo.thirtyrails.player.web;

import com.siemens.mo.thirtyrails.diceroll.DiceRollService;
import com.siemens.mo.thirtyrails.map.Map;
import com.siemens.mo.thirtyrails.map.MapService;
import com.siemens.mo.thirtyrails.map.TrackWalker;
import com.siemens.mo.thirtyrails.player.PlayerState;
import com.siemens.mo.thirtyrails.player.Score;
import com.siemens.mo.thirtyrails.player.ScoreService;
import com.siemens.mo.thirtyrails.svg.SvgDrawer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/games/{gameId}/players/{playerName}")
@RequiredArgsConstructor
public class PlayerController {
    private final SvgDrawer svgDrawer;
    private final DiceRollService diceRollService;
    private final ScoreService scoreService;
    private final MapService mapService;

    @GetMapping(value = "/dicerolls/image", produces = "image/svg+xml")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> diceRolls(@PathVariable Integer gameId,
                                            @PathVariable String playerName) {

        var diceRoll = diceRollService.getDiceRollByPlayer(gameId, playerName);
        return ResponseEntity.ok(svgDrawer.drawSvg(diceRoll, 200, 100));
    }

    @GetMapping(value = "/score/image", produces = "image/svg+xml")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> playerScore(@PathVariable Integer gameId,
                                              @PathVariable String playerName) {

        Map map = mapService.findByGameIdAndPlayerName(gameId, playerName);
        TrackWalker trackWalker = new TrackWalker(map);
        Score score = scoreService.calculateScore(trackWalker.walk(), map.getBonusPosition());
        return ResponseEntity.ok(svgDrawer.drawSvg(score, 600, 1000));
    }

    @GetMapping(value = "/state/image", produces = "image/svg+xml")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> playerState(@PathVariable Integer gameId,
                                              @PathVariable String playerName) {

        PlayerState state = mapService.getState(gameId, playerName);
        return ResponseEntity.ok(svgDrawer.drawSvg(state, 200, 300));
    }

}
