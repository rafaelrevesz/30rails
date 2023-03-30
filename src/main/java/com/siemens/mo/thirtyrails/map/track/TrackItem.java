package com.siemens.mo.thirtyrails.map.track;

import com.siemens.mo.thirtyrails.map.Connection;
import com.siemens.mo.thirtyrails.map.Rotation;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
public abstract class TrackItem implements Svg {
    @Getter
    protected final Position position;
    @Getter
    protected final Rotation rotation;
    protected List<Connection> connections = new ArrayList<>();
}
