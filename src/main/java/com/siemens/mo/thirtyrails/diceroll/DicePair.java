package com.siemens.mo.thirtyrails.diceroll;

import com.siemens.mo.thirtyrails.svg.Svg;

public record DicePair(Dice whiteDice, Dice redDice) implements Svg {

    @Override
    public String toSvg(int x, int y) {

        return whiteDice.toSvg(x, y) + redDice.toSvg(x + 110, y);
    }
}
