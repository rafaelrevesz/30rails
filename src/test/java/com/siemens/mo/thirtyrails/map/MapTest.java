package com.siemens.mo.thirtyrails.map;

import com.siemens.mo.thirtyrails.map.track.Crossover;
import com.siemens.mo.thirtyrails.map.track.Curve;
import com.siemens.mo.thirtyrails.map.track.DoubleCurve;
import com.siemens.mo.thirtyrails.map.track.LeftJunction;
import com.siemens.mo.thirtyrails.map.track.RightJunction;
import com.siemens.mo.thirtyrails.map.track.Straight;
import com.siemens.mo.thirtyrails.map.track.YJunction;
import com.siemens.mo.thirtyrails.position.Position;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;

import static com.siemens.mo.thirtyrails.map.Rotation.DEG180;
import static com.siemens.mo.thirtyrails.map.Rotation.DEG270;
import static com.siemens.mo.thirtyrails.map.Rotation.DEG90;
import static com.siemens.mo.thirtyrails.map.Rotation.NONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@Slf4j
public class MapTest {

    @Test
    void shouldFindWay() {
        Map map = new Map();
        map.addMountain(new Position(3, 1));
        map.addMountain(new Position(6, 3));
        map.addMountain(new Position(2, 4));
        map.addMountain(new Position(2, 5));
        map.addMountain(new Position(4, 6));
        map.setMine(new Position(6, 2));
        map.setStation(new Position(2, 7), 1);
        map.setStation(new Position(0, 1), 2);
        map.setStation(new Position(5, 0), 3);
        map.setStation(new Position(7, 5), 4);
        map.setBonus(new Position(4, 3));

        map.addTrack(new Mine(new Position(6, 2)));

        Station station1 = new Station(new Position(2, 7), 1);
        map.addTrack(station1);
        Station station2 = new Station(new Position(0, 1), 2);
        map.addTrack(station2);
        Station station3 = new Station(new Position(5, 0), 3);
        map.addTrack(station3);
        Station station4 = new Station(new Position(7, 5), 4);
        map.addTrack(station4);

        map.addTrack(new Straight(new Position(1, 1), DEG90));
        map.addTrack(new RightJunction(new Position(2, 1), DEG90));
        map.addTrack(new YJunction(new Position(2, 2), DEG270));
        map.addTrack(new Crossover(new Position(3, 2), NONE));
        map.addTrack(new YJunction(new Position(4, 2), NONE));
        map.addTrack(new LeftJunction(new Position(5, 2), DEG90));
        map.addTrack(new RightJunction(new Position(5, 1), NONE));
        map.addTrack(new Straight(new Position(4, 3), NONE));
        map.addTrack(new Straight(new Position(4, 4), NONE));
        map.addTrack(new RightJunction(new Position(3, 5), NONE));
        map.addTrack(new YJunction(new Position(4, 5), DEG180));
        map.addTrack(new Straight(new Position(5, 5), DEG90));
        map.addTrack(new Crossover(new Position(6, 5), NONE));
        map.addTrack(new Curve(new Position(2, 6), NONE));
        map.addTrack(new DoubleCurve(new Position(3, 6), NONE));

        // a way to go back
        map.addTrack(new Curve(new Position(2, 3), DEG270));
        map.addTrack(new Curve(new Position(3, 3), DEG90));
        map.addTrack(new Straight(new Position(3, 4), NONE));


        TrackWalker trackWalker = new TrackWalker(map);

        assertThat(trackWalker.walk(station1), containsInAnyOrder(
                "27-26-36-35-45-44-43-42-32-22-21-11-01",
                "27-26-36-35-45-44-43-42-52-51-50",
                "27-26-36-35-45-44-43-42-52-62",
                "27-26-36-35-34-33-23-22-32-42-43-44-45-55-65-75"));

        assertThat(trackWalker.walk(station2), containsInAnyOrder(
                "01-11-21-22-32-42-43-44-45-35-36-26-27",
                "01-11-21-22-32-42-43-44-45-55-65-75"));

        assertThat(trackWalker.walk(station3), containsInAnyOrder(
                "50-51-52-42-43-44-45-35-36-26-27",
                "50-51-52-42-43-44-45-55-65-75"));

        assertThat(trackWalker.walk(station4), containsInAnyOrder(
                "75-65-55-45-44-43-42-32-22-21-11-01",
                "75-65-55-45-44-43-42-32-22-23-33-34-35-36-26-27",
                "75-65-55-45-44-43-42-52-51-50",
                "75-65-55-45-44-43-42-52-62"));
    }

    @Test
    void shouldUseCrossoverTwice() {
        Map map = new Map();
        map.addMountain(new Position(3, 1));
        map.addMountain(new Position(6, 3));
        map.addMountain(new Position(2, 4));
        map.addMountain(new Position(2, 5));
        map.addMountain(new Position(4, 6));
        map.setMine(new Position(6, 2));
        map.setStation(new Position(2, 7), 1);
        map.setStation(new Position(0, 1), 2);
        map.setStation(new Position(5, 0), 3);
        map.setStation(new Position(7, 5), 4);
        map.setBonus(new Position(4, 3));

        map.addTrack(new Mine(new Position(6, 2)));

        Station station1 = new Station(new Position(2, 7), 1);
        map.addTrack(station1);
        Station station2 = new Station(new Position(0, 1), 2);
        map.addTrack(station2);
        Station station3 = new Station(new Position(5, 0), 3);
        map.addTrack(station3);
        Station station4 = new Station(new Position(7, 5), 4);
        map.addTrack(station4);

        map.addTrack(new Straight(new Position(1, 1), DEG90));
        map.addTrack(new Curve(new Position(2, 1), DEG90));
        map.addTrack(new Straight(new Position(2, 2), NONE));
        map.addTrack(new Curve(new Position(2, 3), DEG270));
        map.addTrack(new Straight(new Position(3, 3), DEG90));
        map.addTrack(new Crossover(new Position(4, 3), DEG90));
        map.addTrack(new Curve(new Position(5, 3), DEG90));
        map.addTrack(new Curve(new Position(5, 4), DEG180));
        map.addTrack(new Curve(new Position(4, 4), DEG270));
        map.addTrack(new Curve(new Position(4, 2), NONE));
        map.addTrack(new Straight(new Position(5, 2), DEG90));


        TrackWalker trackWalker = new TrackWalker(map);

        assertThat(trackWalker.walk(station2), containsInAnyOrder(
                "01-11-21-22-23-33-43LR-53-54-44-43UD-42-52-62"));

    }

    @Test
    void shouldUseDoubleCurveTwice() {
        Map map = new Map();
        map.addMountain(new Position(3, 1));
        map.addMountain(new Position(6, 3));
        map.addMountain(new Position(2, 4));
        map.addMountain(new Position(2, 5));
        map.addMountain(new Position(4, 6));
        map.setMine(new Position(6, 2));
        map.setStation(new Position(2, 7), 1);
        map.setStation(new Position(0, 1), 2);
        map.setStation(new Position(5, 0), 3);
        map.setStation(new Position(7, 5), 4);
        map.setBonus(new Position(4, 3));

        map.addTrack(new Mine(new Position(6, 2)));

        Station station1 = new Station(new Position(2, 7), 1);
        map.addTrack(station1);
        Station station2 = new Station(new Position(0, 1), 2);
        map.addTrack(station2);
        Station station3 = new Station(new Position(5, 0), 3);
        map.addTrack(station3);
        Station station4 = new Station(new Position(7, 5), 4);
        map.addTrack(station4);

        map.addTrack(new Straight(new Position(1, 1), DEG90));
        map.addTrack(new Curve(new Position(2, 1), DEG90));
        map.addTrack(new Curve(new Position(2, 2), DEG270));
        map.addTrack(new Straight(new Position(3, 2), DEG90));
        map.addTrack(new Curve(new Position(4, 2), DEG90));
        map.addTrack(new DoubleCurve(new Position(4, 3), NONE));
        map.addTrack(new Curve(new Position(4, 4), DEG180));
        map.addTrack(new Curve(new Position(3, 4), DEG270));
        map.addTrack(new Curve(new Position(3, 3), NONE));
        map.addTrack(new Curve(new Position(5, 3), DEG180));
        map.addTrack(new Curve(new Position(5, 2), NONE));


        TrackWalker trackWalker = new TrackWalker(map);

        assertThat(trackWalker.walk(station2), containsInAnyOrder(
                "01-11-21-22-32-42-43UL-33-34-44-43DR-53-52-62"));
    }

    @Test
    void shouldDetectCircle() {
        Map map = new Map();
        map.addMountain(new Position(3, 1));
        map.addMountain(new Position(6, 3));
        map.addMountain(new Position(2, 4));
        map.addMountain(new Position(2, 5));
        map.addMountain(new Position(4, 6));
        map.setMine(new Position(6, 2));
        map.setStation(new Position(2, 7), 1);
        map.setStation(new Position(0, 1), 2);
        map.setStation(new Position(5, 0), 3);
        map.setStation(new Position(7, 5), 4);
        map.setBonus(new Position(4, 3));

        map.addTrack(new Mine(new Position(6, 2)));

        Station station1 = new Station(new Position(2, 7), 1);
        map.addTrack(station1);
        Station station2 = new Station(new Position(0, 1), 2);
        map.addTrack(station2);
        Station station3 = new Station(new Position(5, 0), 3);
        map.addTrack(station3);
        Station station4 = new Station(new Position(7, 5), 4);
        map.addTrack(station4);

        map.addTrack(new Straight(new Position(1, 1), DEG90));
        map.addTrack(new Curve(new Position(2, 1), DEG90));
        map.addTrack(new Curve(new Position(2, 2), DEG270));
        map.addTrack(new Straight(new Position(3, 2), DEG90));
        map.addTrack(new YJunction(new Position(4, 2), NONE));
        map.addTrack(new LeftJunction(new Position(5, 2), DEG90));
        map.addTrack(new RightJunction(new Position(4, 3), DEG180));
        map.addTrack(new Curve(new Position(3, 3), NONE));
        map.addTrack(new Curve(new Position(3, 4), DEG270));
        map.addTrack(new RightJunction(new Position(4, 4), DEG180));


        TrackWalker trackWalker = new TrackWalker(map);

        assertThat(trackWalker.walk(station2), IsCollectionWithSize.hasSize(0));
    }

    @Test
    void shouldDiscardCircleAndFindWay() {
        Map map = new Map();
        map.addMountain(new Position(3, 1));
        map.addMountain(new Position(6, 3));
        map.addMountain(new Position(2, 4));
        map.addMountain(new Position(2, 5));
        map.addMountain(new Position(4, 6));
        map.setMine(new Position(6, 2));
        map.setStation(new Position(2, 7), 1);
        map.setStation(new Position(0, 1), 2);
        map.setStation(new Position(5, 0), 3);
        map.setStation(new Position(7, 5), 4);
        map.setBonus(new Position(4, 3));

        map.addTrack(new Mine(new Position(6, 2)));

        Station station1 = new Station(new Position(2, 7), 1);
        map.addTrack(station1);
        Station station2 = new Station(new Position(0, 1), 2);
        map.addTrack(station2);
        Station station3 = new Station(new Position(5, 0), 3);
        map.addTrack(station3);
        Station station4 = new Station(new Position(7, 5), 4);
        map.addTrack(station4);

        map.addTrack(new Straight(new Position(1, 1), DEG90));
        map.addTrack(new Curve(new Position(2, 1), DEG90));
        map.addTrack(new Curve(new Position(2, 2), DEG270));
        map.addTrack(new Straight(new Position(3, 2), DEG90));
        map.addTrack(new YJunction(new Position(4, 2), NONE));
        map.addTrack(new LeftJunction(new Position(5, 2), DEG270));
        map.addTrack(new RightJunction(new Position(4, 3), DEG180));
        map.addTrack(new Curve(new Position(3, 3), NONE));
        map.addTrack(new Curve(new Position(3, 4), DEG270));
        map.addTrack(new RightJunction(new Position(4, 4), DEG180));

        map.addTrack(new Curve(new Position(4, 5), DEG270));
        map.addTrack(new Curve(new Position(5, 5), DEG180));
        map.addTrack(new Straight(new Position(5, 4), NONE));
        map.addTrack(new Straight(new Position(5, 3), NONE));

        TrackWalker trackWalker = new TrackWalker(map);

        assertThat(trackWalker.walk(station2), containsInAnyOrder(
                "01-11-21-22-32-42-43-44-45-55-54-53-52-62"));
    }

}
