package com.siemens.mo.thirtyrails.map.track;

import com.siemens.mo.thirtyrails.map.Rotation;
import com.siemens.mo.thirtyrails.map.persistence.MapItemEntity;
import com.siemens.mo.thirtyrails.map.web.TrackDto;
import com.siemens.mo.thirtyrails.position.Position;

import java.lang.reflect.Constructor;

public class TrackItemFactory {

    public static <T extends TrackItem> T get(TrackDto trackDto) {
        return (T)switch (trackDto.type()) {
            case STRAIGHT -> Straight.of(trackDto);
            case CROSSOVER -> Crossover.of(trackDto);
            case CURVE -> Curve.of(trackDto);
            case Y_JUNCTION -> YJunction.of(trackDto);
            case DOUBLE_CURVE -> DoubleCurve.of(trackDto);
            case LEFT_JUNCTION -> LeftJunction.of(trackDto);
            case RIGHT_JUNCTION -> RightJunction.of(trackDto);
        };
    }

    public static <T extends TrackItem> T get(MapItemEntity entity) throws Exception {
        Class<?> trackClass = Class.forName(entity.getType());
        Constructor<?> constructor = trackClass.getConstructor(Position.class, Rotation.class);
        return (T)constructor.newInstance(entity.asPosition(), entity.getRotation());
    }
}
