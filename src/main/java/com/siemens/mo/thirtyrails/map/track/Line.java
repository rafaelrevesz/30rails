package com.siemens.mo.thirtyrails.map.track;

import com.siemens.mo.thirtyrails.map.Connection;
import com.siemens.mo.thirtyrails.map.Rotation;
import com.siemens.mo.thirtyrails.map.web.TrackDto;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;

import static com.siemens.mo.thirtyrails.map.Rotation.DEG180;
import static com.siemens.mo.thirtyrails.map.Rotation.NONE;

public class Line extends TrackItem implements Svg {

    public Line(Position position, Rotation rotation) {
        super(position, rotation);
        if (rotation == Rotation.DEG90 || rotation == Rotation.DEG270) {
            connections.add(Connection.UP);
            connections.add(Connection.DOWN);
        } else {
            connections.add(Connection.LEFT);
            connections.add(Connection.RIGHT);
        }
    }

    public static Line of(TrackDto trackDto) {
        return new Line(trackDto.position(), trackDto.rotation());
    }

    @Override
    public String toSvg(int x, int y) {
        if (rotation == NONE || rotation == DEG180) {
            return "<rect x=\"" + (x + 1 + (position.col() - 1) * 100) + "\" y=\"" + (y + 41 + (position.row() - 1) * 100) + "\" width=\"98\" height=\"20\" fill=\"black\"/>\n";
        } else {
            return "<rect x=\"" + (x + 41 + (position.col() - 1) * 100) + "\" y=\"" + (y + 1 + (position.row() - 1) * 100) + "\" width=\"20\" height=\"98\" fill=\"black\"/>\n";
        }
    }
}
