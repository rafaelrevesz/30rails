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
import static com.siemens.mo.thirtyrails.map.Rotation.NONE;

public class Straight extends TrackItem implements Svg {

    public Straight(Position position, Rotation rotation) {
        super(position, rotation);
        if (rotation == Rotation.DEG90 || rotation == Rotation.DEG270) {
            connections.put(LEFT, List.of(RIGHT));
            connections.put(RIGHT, List.of(LEFT));
        } else {
            connections.put(UP, List.of(DOWN));
            connections.put(DOWN, List.of(UP));
        }
    }

    public static Straight of(TrackDto trackDto) {
        return new Straight(trackDto.position(), trackDto.rotation());
    }

    @Override
    public String toSvg(int x, int y) {
        if (rotation == NONE || rotation == DEG180) {
            return "<rect x=\"" + (x + 41 + (position.col() - 1) * 100) + "\" y=\"" + (y + 1 + (position.row() - 1) * 100) + "\" width=\"20\" height=\"98\" fill=\"black\"/>\n";
        } else {
            return "<rect x=\"" + (x + 1 + (position.col() - 1) * 100) + "\" y=\"" + (y + 41 + (position.row() - 1) * 100) + "\" width=\"98\" height=\"20\" fill=\"black\"/>\n";
        }
    }

    @Override
    public String toString() {
        return "Straight on " + position;
    }

    @Override
    public int allowedRedDiceValue() {
        return 2;
    }
}
