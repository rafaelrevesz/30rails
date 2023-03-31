package com.siemens.mo.thirtyrails.map;

import com.siemens.mo.thirtyrails.map.track.TrackItem;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;

import java.util.List;

import static com.siemens.mo.thirtyrails.map.Direction.DOWN;
import static com.siemens.mo.thirtyrails.map.Direction.LEFT;
import static com.siemens.mo.thirtyrails.map.Direction.RIGHT;
import static com.siemens.mo.thirtyrails.map.Direction.STOP;
import static com.siemens.mo.thirtyrails.map.Direction.UP;

public class Mine extends TrackItem implements Svg {
    protected Mine(Position position) {
        super(position, null);
        connections.put(DOWN, List.of(STOP));
        connections.put(UP, List.of(STOP));
        connections.put(RIGHT, List.of(STOP));
        connections.put(LEFT, List.of(STOP));
    }

    @Override
    public String toSvg(int x, int y) {
        return null;
    }

    @Override
    public String toString() {
        return "Mine on " + position;
    }
}
