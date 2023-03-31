package com.siemens.mo.thirtyrails.map;

import com.siemens.mo.thirtyrails.map.track.TrackItem;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;
import lombok.Getter;

import java.util.List;

import static com.siemens.mo.thirtyrails.map.Direction.DOWN;
import static com.siemens.mo.thirtyrails.map.Direction.LEFT;
import static com.siemens.mo.thirtyrails.map.Direction.RIGHT;
import static com.siemens.mo.thirtyrails.map.Direction.STOP;
import static com.siemens.mo.thirtyrails.map.Direction.UP;

public class Station extends TrackItem implements Svg {

    @Getter
    private final int id;
    @Getter
    private final Direction startDirection;

    protected Station(Position position, int id) {
        super(position, Rotation.NONE);
        this.id = id;

        if (position.row() == 0) {
            connections.put(DOWN, List.of(STOP));
            connections.put(STOP, List.of(DOWN));
            this.startDirection = DOWN;
        } else if (position.row() == 7) {
            connections.put(UP, List.of(STOP));
            connections.put(STOP, List.of(UP));
            this.startDirection = UP;
        } else if (position.col() == 0) {
            connections.put(RIGHT, List.of(STOP));
            connections.put(STOP, List.of(RIGHT));
            this.startDirection = RIGHT;
        } else if (position.col() == 7) {
            connections.put(LEFT, List.of(STOP));
            connections.put(STOP, List.of(LEFT));
            this.startDirection = LEFT;
        } else {
            throw new IllegalArgumentException("Invalid station postion");
        }

    }

    @Override
    public String toSvg(int x, int y) {
        return "<text x=\"" + (130 + position.col() * 100) + "\" y=\"" + (180 + position.row() * 100) + "\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"blue\">" +
                id + "</text>\n";
    }

    @Override
    public String toString() {
        return "Station " + id + " on " + position;
    }
}
