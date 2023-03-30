package com.siemens.mo.thirtyrails.diceroll;

import com.siemens.mo.thirtyrails.svg.Svg;

public record DicePair(Dice bwDice, Dice redDice) implements Svg {

    @Override
    public String toSvg(int x, int y) {

        return bwDice.toSvg(x, y) + redDice.toSvg(x + 110, y);
    }
}
