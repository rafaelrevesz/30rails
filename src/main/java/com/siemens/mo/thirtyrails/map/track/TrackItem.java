package com.siemens.mo.thirtyrails.map.track;

import com.siemens.mo.thirtyrails.map.Direction;
import com.siemens.mo.thirtyrails.map.Rotation;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
public abstract class TrackItem implements Svg {
    @Getter
    protected final Position position;
    @Getter
    protected final Rotation rotation;
    protected Map<Direction, List<Direction>> connections = new HashMap<>();

    public boolean canConnectedFrom(Direction direction) {
        return connections.containsKey(direction);
    }

    public List<Direction> getConnectionsFrom(Direction direction) {
        return connections.get(direction);
    }

    public abstract int allowedRedDiceValue();
}
