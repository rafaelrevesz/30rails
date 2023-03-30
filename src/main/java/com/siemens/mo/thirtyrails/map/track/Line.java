package com.siemens.mo.thirtyrails.map.track;

import com.siemens.mo.thirtyrails.map.Connection;
import com.siemens.mo.thirtyrails.map.Rotate;
import com.siemens.mo.thirtyrails.map.track.TrackType;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;

import static com.siemens.mo.thirtyrails.map.Rotate.DEG180;
import static com.siemens.mo.thirtyrails.map.Rotate.NONE;

public class Line extends TrackType implements Svg {

    public Line(Position position, Rotate rotate) {
        super(position, rotate);
        if (rotate == Rotate.DEG90 || rotate == Rotate.DEG270) {
            connections.add(Connection.UP);
            connections.add(Connection.DOWN);
        } else {
            connections.add(Connection.LEFT);
            connections.add(Connection.RIGHT);
        }
    }

    @Override
    public String toSvg(int x, int y) {
        if (rotate == NONE || rotate == DEG180) {
            return "<rect x=\"" + (x + 1 + (position.col() - 1) * 100) + "\" y=\"" + (y + 41 + (position.row() - 1) * 100) + "\" width=\"98\" height=\"20\" fill=\"black\"/>\n";
        } else {
            return "<rect x=\"" + (x + 41 + (position.col() - 1) * 100) + "\" y=\"" + (y + 1 + (position.row() - 1) * 100) + "\" width=\"20\" height=\"98\" fill=\"black\"/>\n";
        }
    }
}
