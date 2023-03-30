package com.siemens.mo.thirtyrails.map.track;

import com.siemens.mo.thirtyrails.map.Rotate;
import com.siemens.mo.thirtyrails.map.track.TrackType;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;

import static com.siemens.mo.thirtyrails.map.Rotate.DEG180;
import static com.siemens.mo.thirtyrails.map.Rotate.DEG270;
import static com.siemens.mo.thirtyrails.map.Rotate.DEG90;
import static com.siemens.mo.thirtyrails.map.Rotate.NONE;

public class DoubleCurve extends TrackType implements Svg {

    public DoubleCurve(Position position, Rotate rotate) {
        super(position, rotate);
    }

    @Override
    public String toSvg(int x, int y) {
        int px = x + 1 + (position.col() - 1) * 100;
        int py = y + 1 + (position.row() - 1) * 100;
        if (rotate == NONE || rotate == DEG180) {
            return "<path d=\"M " + (px + 40) + " " + (py + 98) + " a 59 59 0 0 1 59 -59 l 0 20 a 39 39 0 0 0 -39 39" + "\" fill=\"black\"/>\n" +
                   "<path d=\"M " + (px + 1) + " " + (py + 60) + " a 59 59 0 0 0 59 -59 l -20 0 a 39 39 0 0 1 -39 39" + "\" fill=\"black\"/>\n";
        } else if (rotate == DEG90 || rotate == DEG270) {
            return "<path d=\"M " + (px + 1) + " " + (py + 40) + " a 59 59 0 0 1 59 59 l -20 0 a 39 39 0 0 0 -39 -39" + "\" fill=\"black\"/>\n" +
                   "<path d=\"M " + (px + 40) + " " + (py + 1) + " a 59 59 0 0 0 59 59 l 0 -20 a 39 39 0 0 1 -39 -39" + "\" fill=\"black\"/>\n";
        } else {
            throw new IllegalStateException("Unsupported rotation: " + rotate);
        }
    }
}
