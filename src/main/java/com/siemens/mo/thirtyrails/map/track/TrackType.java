package com.siemens.mo.thirtyrails.map.track;

import com.siemens.mo.thirtyrails.map.Connection;
import com.siemens.mo.thirtyrails.map.Rotate;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
public abstract class TrackType implements Svg {
    protected final Position position;
    protected final Rotate rotate;
    protected List<Connection> connections = new ArrayList<>();
}
