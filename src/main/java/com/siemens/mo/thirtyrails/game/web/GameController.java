package com.siemens.mo.thirtyrails.game.web;

import com.siemens.mo.thirtyrails.diceroll.DicePair;
import com.siemens.mo.thirtyrails.diceroll.DiceRollService;
import com.siemens.mo.thirtyrails.game.Game;
import com.siemens.mo.thirtyrails.game.GameService;
import com.siemens.mo.thirtyrails.map.track.TrackItemFactory;
import com.siemens.mo.thirtyrails.map.web.TrackDto;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final DiceRollService diceRollService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<GameResource> createGame() {

        GameResource gameResource = GameResource.of(gameService.create());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/games/{gameId}")
                .buildAndExpand(gameResource.id())
                .toUri();
        log.info("Game initialized: {}", gameResource.id());

        return ResponseEntity.created(location).body(gameResource);
    }

    @PostMapping("/{gameId}/start")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<GameResource> startGame(@PathVariable Integer gameId) {

        log.info("Game start: {}", gameId);
        return ResponseEntity.ok(GameResource.of(gameService.start(gameId)));
    }

    @PostMapping("/{gameId}/close")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<GameResource>closeGame(@PathVariable Integer gameId) {

        log.info("Game close: {}", gameId);
        return ResponseEntity.ok(GameResource.of(gameService.close(gameId)));
    }

    @GetMapping("/{gameId}/dicerolls")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<DicePair>> diceRolls(@PathVariable Integer gameId) {

        return ResponseEntity.ok(diceRollService.getDiceRollsByGame(gameId));
    }

}
