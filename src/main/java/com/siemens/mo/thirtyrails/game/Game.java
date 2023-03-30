package com.siemens.mo.thirtyrails.game;

import com.siemens.mo.thirtyrails.diceroll.DicePair;
import com.siemens.mo.thirtyrails.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class Game {
    @Getter
    private final int id;
    private java.util.Map<String, Player> players = new HashMap<>();
    private List<DicePair> diceRolls = new ArrayList<>();
    private final GameState state;

}
