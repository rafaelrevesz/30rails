package com.siemens.mo.thirtyrails.player;

import com.siemens.mo.thirtyrails.diceroll.DicePair;
import com.siemens.mo.thirtyrails.map.Rotation;
import com.siemens.mo.thirtyrails.map.track.Crossover;
import com.siemens.mo.thirtyrails.map.track.Curve;
import com.siemens.mo.thirtyrails.map.track.DoubleCurve;
import com.siemens.mo.thirtyrails.map.track.LeftJunction;
import com.siemens.mo.thirtyrails.map.track.RightJunction;
import com.siemens.mo.thirtyrails.map.track.Straight;
import com.siemens.mo.thirtyrails.map.track.YJunction;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;

import static com.siemens.mo.thirtyrails.map.Rotation.NONE;

public record PlayerState(DicePair currentDicePair, boolean whiteOverrideAvailable, boolean redOverrideAvailable) implements Svg {

    @Override
    public String toSvg(int x, int y) {
        StringBuilder svg = new StringBuilder();
        svg.append(currentDicePair.toSvg(x, y));
        switch (currentDicePair.redDice().getValue()) {
            case 1 -> svg.append(((Svg)new Curve(new Position(1, 1), NONE)).toSvg(x + 100, y + 100));
            case 2 -> svg.append(((Svg)new Straight(new Position(1, 1), NONE)).toSvg(x + 100, y + 100));
            case 3 -> svg.append(((Svg)new DoubleCurve(new Position(1, 1), NONE)).toSvg(x + 100, y + 100));
            case 4 -> svg.append(((Svg)new Crossover(new Position(1, 1), NONE)).toSvg(x + 100, y + 100));
            case 5 -> svg.append(((Svg)new YJunction(new Position(1, 1), NONE)).toSvg(x + 100, y + 100));
            case 6 -> svg.append(((Svg)new LeftJunction(new Position(1, 1), NONE)).toSvg(x + 0, 100)).append(((Svg)new RightJunction(new Position(1, 1), NONE)).toSvg(x + 100, y + 100));
        }
        svg.append("<rect x=\"").append(x).append("\" y=\"").append(y + 210).append("\" width=\"90\" height=\"90\" rx=\"15\" fill=\"white\" stroke=\"#444444\" />");
        svg.append("<rect x=\"").append(x + 100).append("\" y=\"").append(y + 210).append("\" width=\"90\" height=\"90\" rx=\"15\" fill=\"red\" stroke=\"#444444\" />");
        svg.append("<text x=\"").append(x + 18).append("\" y=\"").append(y + 280).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"black\">?</text>\n");
        svg.append("<text x=\"").append(x + 118).append("\" y=\"").append(y + 280).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"white\">?</text>\n");

        if (!whiteOverrideAvailable) {
            svg.append("<line x1=\"").append(x).append("\" y1=\"").append(y + 300).append("\" x2=\"").append(x + 90).append("\" y2=\"").append(y + 210).append("\" stroke=\"blue\" stroke-width=\"6\" />");
        }
        if (!redOverrideAvailable) {
            svg.append("<line x1=\"").append(x + 100).append("\" y1=\"").append(y + 300).append("\" x2=\"").append(x + 190).append("\" y2=\"").append(y + 210).append("\" stroke=\"blue\" stroke-width=\"6\" />");
        }

        return svg.toString();
    }
}
