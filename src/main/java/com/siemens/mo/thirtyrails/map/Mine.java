package com.siemens.mo.thirtyrails.map;

import com.siemens.mo.thirtyrails.map.track.TrackItem;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;

public class Mine extends TrackItem implements Svg {
    protected Mine(Position position) {
        super(position, null);
    }

    @Override
    public String toString() {
        return "M";
    }

    @Override
    public String toSvg(int x, int y) {
        return null;
    }
}
