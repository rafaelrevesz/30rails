package com.siemens.mo.thirtyrails.diceroll;

import com.siemens.mo.thirtyrails.svg.Svg;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.siemens.mo.thirtyrails.diceroll.DiceType.RED;

@Getter
@RequiredArgsConstructor
public class Dice implements Svg {
    private final int value;
    private final DiceType diceType;

    @Override
    public String toSvg(int x, int y) {
        int size = 90;
        int top = 20;
        int bottom = size - top;
        StringBuilder svg = new StringBuilder();

        String bgColor = "white";
        String dotEnding = "\" r=\"10\" stroke=\"black\" fill=\"black\" />";
        if (diceType == RED) {
            bgColor = "red";
            dotEnding = "\" r=\"10\" stroke=\"white\" fill=\"white\" />";
        }

        svg.append("<rect x=\"").append(x).append("\" y=\"").append(y).append("\" width=\"").append(size).append("\" height=\"").append(size).append("\" rx=\"15\" fill=\"").append(bgColor).append("\" stroke=\"#444444\" />");
        if (value == 1 || value == 3 || value == 5) {
            svg.append("<circle cx=\"").append(x + size / 2).append("\" cy=\"").append(y + size / 2).append(dotEnding);
        }
        if (value == 4 || value == 5 || value == 6) {
            svg.append("<circle cx=\"").append(x + top).append("\" cy=\"").append(y + top).append(dotEnding);
            svg.append("<circle cx=\"").append(x + bottom).append("\" cy=\"").append(y + bottom).append(dotEnding);
        }
        if (value == 2 || value == 3 || value == 4 || value == 5 || value == 6) {
            svg.append("<circle cx=\"").append(x + bottom).append("\" cy=\"").append(y + top).append(dotEnding);
            svg.append("<circle cx=\"").append(x + top).append("\" cy=\"").append(y + bottom).append(dotEnding);
        }
        if (value == 6) {
            svg.append("<circle cx=\"").append(x + top).append("\" cy=\"").append(y + size / 2).append(dotEnding);
            svg.append("<circle cx=\"").append(x + bottom).append("\" cy=\"").append(y + size / 2).append(dotEnding);
        }
        return svg.toString();
    }
}
