package com.siemens.mo.thirtyrails.map.persistence;

import com.siemens.mo.thirtyrails.map.Rotation;
import com.siemens.mo.thirtyrails.map.track.TrackItem;
import com.siemens.mo.thirtyrails.position.Position;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Data
@Entity(name = "mapitem")
@NoArgsConstructor
@RepositoryRestResource
public class MapItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    private MapEntity map;

    private int x;
    private int y;
    private String type;
    private Rotation rotation;

    public Position asPosition() {
        return new Position(x, y);
    }

    public static <T extends TrackItem> MapItemEntity of(MapEntity map, T track) {
        MapItemEntity entity = new MapItemEntity();
        entity.setMap(map);
        entity.setX(track.getPosition().col());
        entity.setY(track.getPosition().row());
        entity.setType(track.getClass().getName());
        entity.setRotation(track.getRotation());
        return entity;
    }
}
