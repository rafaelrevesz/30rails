package com.siemens.mo.thirtyrails.player;

import com.siemens.mo.thirtyrails.svg.Svg;
import com.siemens.mo.thirtyrails.map.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.siemens.mo.thirtyrails.player.PlayerState.DONE;
import static com.siemens.mo.thirtyrails.player.PlayerState.PLAYING;
import static com.siemens.mo.thirtyrails.player.PlayerState.SETUP;

@Getter
@RequiredArgsConstructor
public class Player implements Svg {
    private final int id;
    private final String name;
    private Map map = new Map();

    private final int turn;

    public void nextRound() {
        //turn++;
    }

    public PlayerState state() {
        if (turn <= 6) {
            return SETUP;
        } else if (turn <= 36) {
            return PLAYING;
        } else {
            return DONE;
        }
    }

    @Override
    public String toSvg(int x, int y) {
        return null;
    }
}
