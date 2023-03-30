package com.siemens.mo.thirtyrails.player.web;

import com.siemens.mo.thirtyrails.diceroll.DiceRollService;
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
@RequestMapping(path = "/games/{gameId}/players/{playerName}")
@RequiredArgsConstructor
public class PlayerController {
    private final SvgDrawer svgDrawer;
    private final DiceRollService diceRollService;

    @GetMapping("/dicerolls")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> diceRolls(@PathVariable Integer gameId,
                                            @PathVariable String playerName) {

        var diceRoll = diceRollService.getDiceRollByPlayer(gameId, playerName);
        return ResponseEntity.ok(svgDrawer.drawSvg(diceRoll));
    }

}
