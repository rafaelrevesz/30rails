package com.siemens.mo.thirtyrails.map;

import com.siemens.mo.thirtyrails.map.track.Crossover;
import com.siemens.mo.thirtyrails.map.track.DoubleCurve;
import com.siemens.mo.thirtyrails.map.track.TrackItem;
import com.siemens.mo.thirtyrails.position.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.siemens.mo.thirtyrails.map.Direction.DOWN;
import static com.siemens.mo.thirtyrails.map.Direction.LEFT;
import static com.siemens.mo.thirtyrails.map.Direction.RIGHT;
import static com.siemens.mo.thirtyrails.map.Direction.STOP;
import static com.siemens.mo.thirtyrails.map.Direction.UP;

@Slf4j
public class TrackWalker {
    private final Map map;
    private List<String> lines = new ArrayList<>();
    private final java.util.Map<String, String> stationIndexMapping;

    public TrackWalker(Map map) {
        this.map = map;
        stationIndexMapping = new HashMap<>();
        for (Station station : map.getStations()) {
            stationIndexMapping.put(station.getPosition().index(), "" + station.getId());
        }
        if (map.getMinePosition() != null) {
            stationIndexMapping.put(map.getMinePosition().index(), "M");
        }
    }

    public java.util.Map<Integer, List<String>> walk() {
        java.util.Map<Integer, List<String>> result = new HashMap<>();
        for (Station station : map.getStations()) {
            result.put(station.getId(), walk(station));
        }
        return result;
    }

    public List<String> walk(Station station) {
        lines.clear();
        log.info("Start from station {}", station.getId());
        walk(station, station.getStartDirection(), "" + station.getId());
        return getShorterLines();
    }

    private void walk(TrackItem trackItem, Direction direction, String line) {
        log.info("Walk {} from {}", direction, trackItem);
        TrackItem nextTrack = map.getByPosition(getPositionFromDirection(trackItem.getPosition(), direction));
        if (nextTrack != null && nextTrack.canConnectedFrom(direction.opposite())) {
            for (Direction goDirection : nextTrack.getConnectionsFrom(direction.opposite())) {
                String nextPositionIndex = positionIndex(nextTrack, goDirection);
                if (line.contains("-" + nextPositionIndex)) {
                    log.error("Circle detected at {}", nextTrack.getPosition());
                    continue;
                }
                if (goDirection == STOP) {
                    log.info("######## DESTINATION FOUND {} ", nextTrack);
                    lines.add(line + "-" + stationIndexMapping.get(nextPositionIndex));
                } else {
                    walk(nextTrack, goDirection, line + "-" + nextPositionIndex);
                }
            }
        } else {
            log.info("Stop walking from {}", trackItem);
        }
    }

    private List<String> getShorterLines() {

        java.util.Map<String, String> lineMap = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split("-");
            String endStationKey = parts[parts.length - 1];
            if (lineMap.containsKey(endStationKey)) {
                if ((line.length() < lineMap.get(endStationKey).length()) || (line.length() == lineMap.get(endStationKey).length() && line.contains(map.getBonusPosition().index()))) {
                    lineMap.put(endStationKey, line);
                }
            } else {
                lineMap.put(endStationKey, line);
            }
        }
        return lineMap.values().stream().toList();
    }

    private Position getPositionFromDirection(Position position, Direction direction) {
        if (direction == UP) {
            return new Position(position.col(), position.row() - 1);
        } else if (direction == DOWN) {
            return new Position(position.col(), position.row() + 1);
        } else if (direction == LEFT) {
            return new Position(position.col() - 1, position.row());
        } else if (direction == RIGHT) {
            return new Position(position.col() + 1, position.row());
        } else {
            return new Position(position.col(), position.row());
        }
    }

    public void reset() {
        lines.clear();
    }

    private String positionIndex(TrackItem trackItem, Direction direction) {
        String postfix = "";
        if (trackItem instanceof Crossover) {
            if (direction == UP || direction == DOWN) {
                postfix = "UD";
            } else {
                postfix = "LR";
            }
        } else if (trackItem instanceof DoubleCurve) {
            if (direction == UP || trackItem.getConnectionsFrom(direction).contains(UP)) {
                postfix = "UL";
            } else {
                postfix = "DR";
            }
        }
        return trackItem.getPosition().index() + postfix;
    }
}
