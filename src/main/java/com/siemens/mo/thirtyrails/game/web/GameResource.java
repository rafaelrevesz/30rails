package com.siemens.mo.thirtyrails.game.web;

import com.siemens.mo.thirtyrails.game.Game;
import com.siemens.mo.thirtyrails.game.GameState;

public record GameResource(int id, GameState state) {

    public static GameResource of(Game game) {
        return new GameResource(game.getId(), game.getState());
    }
}
