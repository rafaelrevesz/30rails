package com.siemens.mo.thirtyrails.map.track;

import com.siemens.mo.thirtyrails.map.Rotation;
import com.siemens.mo.thirtyrails.map.web.TrackDto;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;

import java.util.List;

import static com.siemens.mo.thirtyrails.map.Direction.DOWN;
import static com.siemens.mo.thirtyrails.map.Direction.LEFT;
import static com.siemens.mo.thirtyrails.map.Direction.RIGHT;
import static com.siemens.mo.thirtyrails.map.Direction.UP;
import static com.siemens.mo.thirtyrails.map.Rotation.DEG180;
import static com.siemens.mo.thirtyrails.map.Rotation.DEG270;
import static com.siemens.mo.thirtyrails.map.Rotation.DEG90;
import static com.siemens.mo.thirtyrails.map.Rotation.NONE;

public class LeftJunction extends TrackItem implements Svg {
    public LeftJunction(Position position, Rotation rotation) {
        super(position, rotation);
        if (rotation == NONE) {
            connections.put(DOWN, List.of(LEFT, UP));
            connections.put(UP, List.of(DOWN));
            connections.put(LEFT, List.of(DOWN));
        } else if (rotation == DEG90) {
            connections.put(LEFT, List.of(UP, RIGHT));
            connections.put(UP, List.of(LEFT));
            connections.put(RIGHT, List.of(LEFT));
        } else if (rotation == DEG180) {
            connections.put(UP, List.of(RIGHT, DOWN));
            connections.put(RIGHT, List.of(UP));
            connections.put(DOWN, List.of(UP));
        } else if (rotation == DEG270) {
            connections.put(RIGHT, List.of(DOWN, LEFT));
            connections.put(DOWN, List.of(RIGHT));
            connections.put(LEFT, List.of(RIGHT));
        }
    }

    public static LeftJunction of(TrackDto trackDto) {
        return new LeftJunction(trackDto.position(), trackDto.rotation());
    }

    @Override
    public String toSvg(int x, int y) {
        int px = x + 1 + (position.col() - 1) * 100;
        int py = y + 1 + (position.row() - 1) * 100;
        if (rotation == NONE) {
            return "<path d=\"M " + (px + 1) + " " + (py + 40) + " a 59 59 0 0 1 59 59 l -20 0 a 39 39 0 0 0 -39 -39" + "\" fill=\"black\"/>\n" +
                    "<rect x=\"" + (x + 41 + (position.col() - 1) * 100) + "\" y=\"" + (y + 1 + (position.row() - 1) * 100) + "\" width=\"20\" height=\"98\" fill=\"black\"/>\n";
        } else if (rotation == DEG90) {
            return "<path d=\"M " + (px + 1) + " " + (py + 60) + " a 59 59 0 0 0 59 -59 l -20 0 a 39 39 0 0 1 -39 39" + "\" fill=\"black\"/>\n" +
                    "<rect x=\"" + (x + 1 + (position.col() - 1) * 100) + "\" y=\"" + (y + 41 + (position.row() - 1) * 100) + "\" width=\"98\" height=\"20\" fill=\"black\"/>\n";
        } else if (rotation == DEG180) {
            return "<path d=\"M " + (px + 40) + " " + (py + 1) + " a 59 59 0 0 0 59 59 l 0 -20 a 39 39 0 0 1 -39 -39" + "\" fill=\"black\"/>\n" +
                    "<rect x=\"" + (x + 41 + (position.col() - 1) * 100) + "\" y=\"" + (y + 1 + (position.row() - 1) * 100) + "\" width=\"20\" height=\"98\" fill=\"black\"/>\n";
        } else if (rotation == DEG270) {
            return "<path d=\"M " + (px + 40) + " " + (py + 98) + " a 59 59 0 0 1 59 -59 l 0 20 a 39 39 0 0 0 -39 39" + "\" fill=\"black\"/>\n" +
                    "<rect x=\"" + (x + 1 + (position.col() - 1) * 100) + "\" y=\"" + (y + 41 + (position.row() - 1) * 100) + "\" width=\"98\" height=\"20\" fill=\"black\"/>\n";
        } else {
            throw new IllegalStateException("Unsupported rotation: " + rotation);
        }
    }

    @Override
    public String toString() {
        return "Left junction on " + position;
    }
}
