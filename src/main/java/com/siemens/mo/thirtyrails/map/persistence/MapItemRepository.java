package com.siemens.mo.thirtyrails.map.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MapItemRepository extends JpaRepository<MapItemEntity, Integer> {
    List<MapItemEntity> findByMap(MapEntity map);

    Optional<MapItemEntity> findByMapAndXAndY(MapEntity map, int x, int y);
}
