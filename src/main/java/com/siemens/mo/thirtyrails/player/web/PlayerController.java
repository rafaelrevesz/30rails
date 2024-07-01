package com.siemens.mo.thirtyrails.player.web;

import com.siemens.mo.thirtyrails.diceroll.DiceRollService;
import com.siemens.mo.thirtyrails.map.Map;
import com.siemens.mo.thirtyrails.map.MapService;
import com.siemens.mo.thirtyrails.map.TrackWalker;
import com.siemens.mo.thirtyrails.player.Player;
import com.siemens.mo.thirtyrails.player.PlayerService;
import com.siemens.mo.thirtyrails.player.PlayerState;
import com.siemens.mo.thirtyrails.player.Score;
import com.siemens.mo.thirtyrails.player.ScoreService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(path = "/games/{gameId}/players")
@RequiredArgsConstructor
public class PlayerController {
    private final SvgDrawer svgDrawer;
    private final DiceRollService diceRollService;
    private final ScoreService scoreService;
    private final MapService mapService;
    private final PlayerService playerService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<PlayerResource> registerPlayer(@PathVariable Integer gameId,
                                                       @RequestBody @Valid PlayerDto playerDto) {

        Player player = playerService.register(gameId, playerDto.name());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/games/{gameId}/players/{playerId}")
                .buildAndExpand(gameId, player.getName())
                .toUri();

        return ResponseEntity.created(location).body(new PlayerResource(player.getName()));
    }


    @GetMapping(value = "/{playerName}/dicerolls/image", produces = "image/svg+xml")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> diceRolls(@PathVariable Integer gameId,
                                            @PathVariable String playerName) {

        var diceRoll = diceRollService.getDiceRollByPlayer(gameId, playerName).orElseThrow();
        return ResponseEntity.ok(svgDrawer.drawSvg(diceRoll, 200, 100));
    }

    @GetMapping(value = "/{playerName}/score/image", produces = "image/svg+xml")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> playerScore(@PathVariable Integer gameId,
                                              @PathVariable String playerName) {

        Map map = mapService.findByGameIdAndPlayerName(gameId, playerName);
        TrackWalker trackWalker = new TrackWalker(map);
        Score score = scoreService.calculateScore(trackWalker.walk(), map.getBonusPosition());
        return ResponseEntity.ok(svgDrawer.drawSvg(score, 600, 1000));
    }

    @GetMapping(value = "/{playerName}/state/image", produces = "image/svg+xml")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> playerState(@PathVariable Integer gameId,
                                              @PathVariable String playerName) {

        PlayerState state = mapService.getState(gameId, playerName);
        return ResponseEntity.ok(svgDrawer.drawSvg(state, 200, 300));
    }

}
