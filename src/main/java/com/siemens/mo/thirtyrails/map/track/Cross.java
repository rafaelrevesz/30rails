package com.siemens.mo.thirtyrails.map.track;

import com.siemens.mo.thirtyrails.map.Rotate;
import com.siemens.mo.thirtyrails.map.track.TrackType;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;

public class Cross extends TrackType implements Svg {

    public Cross(Position position, Rotate rotate) {
        super(position, rotate);
    }

    @Override
    public String toSvg(int x, int y) {
        return "<rect x=\"" + (x + 1 + (position.col() - 1) * 100) + "\" y=\"" + (y + 41 + (position.row() - 1) * 100) + "\" width=\"98\" height=\"20\" fill=\"black\"/>\n" +
               "<rect x=\"" + (x + 41 + (position.col() - 1) * 100) + "\" y=\"" + (y + 1 + (position.row() - 1) * 100) + "\" width=\"20\" height=\"98\" fill=\"black\"/>\n";
    }
}
