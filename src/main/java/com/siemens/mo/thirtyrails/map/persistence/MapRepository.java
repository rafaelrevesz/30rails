package com.siemens.mo.thirtyrails.map.persistence;

import com.siemens.mo.thirtyrails.player.persistence.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapRepository extends JpaRepository<MapEntity, Integer> {

    Optional<MapEntity> findByPlayer(PlayerEntity player);
}
