package com.siemens.mo.thirtyrails.map.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapRepository extends JpaRepository<MapEntity, Integer> {

    Optional<MapEntity> findByGameIdAndPlayerName(int gameId, String playerName);
}
