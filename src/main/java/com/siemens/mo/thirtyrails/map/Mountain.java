package com.siemens.mo.thirtyrails.map;

import com.siemens.mo.thirtyrails.map.track.TrackType;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;

public class Mountain extends TrackType implements Svg {
    protected Mountain(Position position) {
        super(position, null);
    }

    @Override
    public String toString() {
        return "A";
    }

    @Override
    public String toSvg(int x, int y) {
        return null;
    }
}
