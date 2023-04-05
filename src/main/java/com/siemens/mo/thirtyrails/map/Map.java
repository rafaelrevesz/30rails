package com.siemens.mo.thirtyrails.map;

import com.siemens.mo.thirtyrails.diceroll.Dice;
import com.siemens.mo.thirtyrails.map.track.TrackItem;
import com.siemens.mo.thirtyrails.position.Position;
import com.siemens.mo.thirtyrails.svg.Svg;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.siemens.mo.thirtyrails.diceroll.DiceType.WHITE;

@Slf4j
public class Map implements Svg {
    private final Set<Position> mountainPositions = new HashSet<>();
    @Getter
    private Position minePosition;
    @Getter
    private Position bonusPosition;
    private final java.util.Map<Position, Integer> stations = new HashMap<>();
    private final java.util.Map<Position, TrackItem> tracks = new HashMap<>();

    public void addMountain(Position position) {
        mountainPositions.add(position);
        tracks.put(position, new Mountain(position));
    }

    public void setMine(Position position) {
        minePosition = position;
        tracks.put(position, new Mine(position));
    }

    public void setBonus(Position position) {
        bonusPosition = position;
    }

    public void setStation(Position position, int stationId) {
        stations.put(position, stationId);
    }

    public boolean isMineSetupReady() {
        return minePosition != null;
    }

    public boolean isBonusSetupReady() {
        return bonusPosition != null;
    }

    public void addTrack(TrackItem track) {
        tracks.put(track.getPosition(), track);
    }

    @Override
    public String toSvg(int x, int y) {
        StringBuilder svg = new StringBuilder();
        Dice[] dices = {new Dice(1, WHITE), new Dice(2, WHITE), new Dice(3, WHITE), new Dice(4, WHITE), new Dice(5, WHITE), new Dice(6, WHITE)};

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
            svg.append("<line x1=\"").append(120 + position.col() * 100).append("\" y1=\"").append(180 + position.row() * 100).append("\" x2=\"").append(150 + position.col() * 100).append("\" y2=\"").append(120 + position.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
            svg.append("<line x1=\"").append(180 + position.col() * 100).append("\" y1=\"").append(180 + position.row() * 100).append("\" x2=\"").append(150 + position.col() * 100).append("\" y2=\"").append(120 + position.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
        }
        if (isMineSetupReady()) {
            svg.append("<line x1=\"").append(120 + minePosition.col() * 100).append("\" y1=\"").append(180 + minePosition.row() * 100).append("\" x2=\"").append(140 + minePosition.col() * 100).append("\" y2=\"").append(120 + minePosition.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
            svg.append("<line x1=\"").append(180 + minePosition.col() * 100).append("\" y1=\"").append(180 + minePosition.row() * 100).append("\" x2=\"").append(160 + minePosition.col() * 100).append("\" y2=\"").append(120 + minePosition.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
            svg.append("<line x1=\"").append(150 + minePosition.col() * 100).append("\" y1=\"").append(140 + minePosition.row() * 100).append("\" x2=\"").append(140 + minePosition.col() * 100).append("\" y2=\"").append(120 + minePosition.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
            svg.append("<line x1=\"").append(150 + minePosition.col() * 100).append("\" y1=\"").append(140 + minePosition.row() * 100).append("\" x2=\"").append(160 + minePosition.col() * 100).append("\" y2=\"").append(120 + minePosition.row() * 100).append("\" stroke=\"blue\" stroke-width=\"5\"/>\n");
        }
        if (isBonusSetupReady()) {
            svg.append("<rect x=\"").append(101 + bonusPosition.col() * 100).append("\" y=\"").append(101 + bonusPosition.row() * 100).append("\" width=\"98\" height=\"98\" fill=\"lightblue\"/>\n");
        }

        for (TrackItem trackItem : tracks.values()) {
            svg.append(trackItem.toSvg(200, 200));
        }

        return svg.toString();
    }

    private String drawGrayField(int x, int y) {
        return "<rect x=\"" + x + "\" y=\"" + y + "\" width=\"98\" height=\"98\" fill=\"#AAAAAA\"/>\n";
    }

    public <T extends TrackItem> T getByPosition(Position position) {
        return (T)tracks.get(position);
    }

    public List<Station> getStations() {
        return tracks.values().stream()
                .filter(Station.class::isInstance)
                .map(Station.class::cast)
                .toList();

    }

}
