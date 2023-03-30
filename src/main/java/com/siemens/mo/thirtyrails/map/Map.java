package com.siemens.mo.thirtyrails.map;

import com.siemens.mo.thirtyrails.diceroll.Dice;
import com.siemens.mo.thirtyrails.map.track.TrackType;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.siemens.mo.thirtyrails.diceroll.DiceType.BW;

@Slf4j
public class Map implements Svg {

    private final Set<Position> mountainPositions = new HashSet<>();
    private Position minePosition;
    private Position bonusPosition;
    private java.util.Map<Position, Integer> stations = new HashMap<>();
    private boolean mountainSkipped = false;
    private Set<TrackType> tracks = new HashSet<>();

    public void addMountain(Position position) {
        /*
        if (isMountainSetupReady()) {
            throw new IllegalArgumentException("Mountains are already set");
        }
        if (mountainPositions.contains(position)) {
            throw new IllegalArgumentException("Position is already set");
        }*/
        mountainPositions.add(position);
    }

    public void setMine(Position position) {
        /*
        if (isMineSetupReady()) {
            throw new IllegalArgumentException("Mine is already set");
        }
        if (!isMountainSetupReady()) {
            throw new IllegalStateException("Mountains are not set yet");
        }
        if (mountainPositions.contains(position)) {
            throw new IllegalArgumentException("Position is already set");
        }
        if (!mountainPositions.stream().anyMatch(p -> p.isNextTo(position))) {
            throw new IllegalArgumentException("Mine position is invalid");
        }*/
        minePosition = position;
    }

    public void setBonus(Position position) {
        /*
        if (!isMountainSetupReady()) {
            throw new IllegalStateException("Mountains are not set yet");
        }
        if (!isMineSetupReady()) {
            throw new IllegalStateException("Mine is not set yet");
        }
        if (isBonusSetupReady()) {
            throw new IllegalArgumentException("Bonus is already set");
        }
        if (mountainPositions.contains(position) || minePosition.equals(position)) {
            throw new IllegalArgumentException("Position is already set");
        }*/
        bonusPosition = position;
    }

    public void setStation(Position position, int stationId) {
        stations.put(position, stationId);
    }

    public boolean isMountainSetupReady() {
        return mountainPositions.size() == 5;
    }

    public boolean isMineSetupReady() {
        return minePosition != null;
    }

    public boolean isBonusSetupReady() {
        return bonusPosition != null;
    }

    public boolean isStationSetupReady() {
        return stations.size() == 4;
    }

    public void skip() {
        if (mountainSkipped) {
            throw new IllegalStateException("One mountain was already skipped");
        }
        mountainSkipped = true;
    }

    public void setTrack(TrackType trackType) {
        tracks.add(trackType);
    }

    @Override
    public String toSvg(int x, int y) {
        StringBuilder svg = new StringBuilder();
        Dice[] dices = {new Dice(1, BW), new Dice(2, BW), new Dice(3, BW), new Dice(4, BW), new Dice(5, BW), new Dice(6, BW)};

        svg.append("<rect x=\"0\" y=\"0\" width=\"1000\" height=\"1000\" fill=\"white\"/>\n");
        for (int i = 0; i < 7; i++) {
            svg.append("<line x1=\"").append(200 + i * 100).append("\" y1=\"0\" x2=\"").append(200 + i * 100).append("\" y2=\"1000\" stroke=\"black\"/>\n");
            svg.append("<line x1=\"0\" y1=\"").append(200 + i * 100).append("\" x2=\"1000\" y2=\"").append(200 + i * 100).append("\" stroke=\"black\"/>\n");
        }
        for (int i = 0; i < 6; i++) {
            svg.append(dices[i].toSvg(205 + i * 100, 5));
            svg.append(dices[i].toSvg(205 + i * 100, 905));
            svg.append(dices[i].toSvg(5, 205 + i * 100));
            svg.append(dices[i].toSvg(905, 205 + i * 100));
            svg.append(drawGrayField(201 + i * 100, 101));
            svg.append(drawGrayField(201 + i * 100, 801));
            svg.append(drawGrayField(101, 201 + i * 100));
            svg.append(drawGrayField(801, 201 + i * 100));
        }
        for (Position position : mountainPositions) {
            log.info("Mountain x={}, y={}", position.col(), position.row());
            svg.append("<line x1=\"").append(120 + position.col() * 100).append("\" y1=\"").append(180 + position.row() * 100).append("\" x2=\"").append(150 + position.col() * 100).append("\" y2=\"").append(120 + position.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
            svg.append("<line x1=\"").append(180 + position.col() * 100).append("\" y1=\"").append(180 + position.row() * 100).append("\" x2=\"").append(150 + position.col() * 100).append("\" y2=\"").append(120 + position.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
        }
        if (isMineSetupReady()) {
            svg.append("<line x1=\"").append(120 + minePosition.col() * 100).append("\" y1=\"").append(180 + minePosition.row() * 100).append("\" x2=\"").append(140 + minePosition.col() * 100).append("\" y2=\"").append(120 + minePosition.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
            svg.append("<line x1=\"").append(180 + minePosition.col() * 100).append("\" y1=\"").append(180 + minePosition.row() * 100).append("\" x2=\"").append(160 + minePosition.col() * 100).append("\" y2=\"").append(120 + minePosition.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
            svg.append("<line x1=\"").append(150 + minePosition.col() * 100).append("\" y1=\"").append(140 + minePosition.row() * 100).append("\" x2=\"").append(140 + minePosition.col() * 100).append("\" y2=\"").append(120 + minePosition.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
            svg.append("<line x1=\"").append(150 + minePosition.col() * 100).append("\" y1=\"").append(140 + minePosition.row() * 100).append("\" x2=\"").append(160 + minePosition.col() * 100).append("\" y2=\"").append(120 + minePosition.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
        }

        for (java.util.Map.Entry<Position, Integer> station : stations.entrySet()) {
            svg.append("<text x=\"").append(130 + station.getKey().col() * 100).append("\" y=\"").append(180 + station.getKey().row() * 100).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"blue\">");
            svg.append(station.getValue()).append("</text>\n");
        }

        if (isBonusSetupReady()) {
            svg.append("<rect x=\"").append(101 + bonusPosition.col() * 100).append("\" y=\"").append(101 + bonusPosition.row() * 100).append("\" width=\"98\" height=\"98\" fill=\"lightblue\"/>\n");
        }

        for (TrackType trackType : tracks) {
            svg.append(trackType.toSvg(200, 200));
        }

        return svg.toString();
    }

    private String drawGrayField(int x, int y) {
        return "<rect x=\"" + x + "\" y=\"" + y + "\" width=\"98\" height=\"98\" fill=\"#AAAAAA\"/>\n";
    }
}
