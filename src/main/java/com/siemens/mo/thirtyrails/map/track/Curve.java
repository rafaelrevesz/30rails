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

public class Curve extends TrackItem implements Svg {
    public Curve(Position position, Rotation rotation) {
        super(position, rotation);
        if (rotation == NONE) {
            connections.put(DOWN, List.of(RIGHT));
            connections.put(RIGHT, List.of(DOWN));
        } else if (rotation == DEG90) {
            connections.put(DOWN, List.of(LEFT));
            connections.put(LEFT, List.of(DOWN));
        } else if (rotation == DEG180) {
            connections.put(UP, List.of(LEFT));
            connections.put(LEFT, List.of(UP));
        } else if (rotation == DEG270) {
            connections.put(UP, List.of(RIGHT));
            connections.put(RIGHT, List.of(UP));
        }
    }

    public static Curve of(TrackDto trackDto) {
        return new Curve(trackDto.position(), trackDto.rotation());
    }

    @Override
    public String toSvg(int x, int y) {
        int px = x + 1 + (position.col() - 1) * 100;
        int py = y + 1 + (position.row() - 1) * 100;
        if (rotation == NONE) {
            return "<path d=\"M " + (px + 40) + " " + (py + 98) + " a 59 59 0 0 1 59 -59 l 0 20 a 39 39 0 0 0 -39 39" + "\" fill=\"black\"/>\n";
        } else if (rotation == DEG90) {
            return "<path d=\"M " + (px + 1) + " " + (py + 40) + " a 59 59 0 0 1 59 59 l -20 0 a 39 39 0 0 0 -39 -39" + "\" fill=\"black\"/>\n";
        } else if (rotation == DEG180) {
            return "<path d=\"M " + (px + 1) + " " + (py + 60) + " a 59 59 0 0 0 59 -59 l -20 0 a 39 39 0 0 1 -39 39" + "\" fill=\"black\"/>\n";
        } else if (rotation == DEG270) {
            return "<path d=\"M " + (px + 40) + " " + (py + 1) + " a 59 59 0 0 0 59 59 l 0 -20 a 39 39 0 0 1 -39 -39" + "\" fill=\"black\"/>\n";
        } else {
            throw new IllegalStateException("Unsupported rotation: " + rotation);
        }
    }

    @Override
    public String toString() {
        return "Curve on " + position;
    }

    @Override
    public int allowedRedDiceValue() {
        return 1;
    }
}
