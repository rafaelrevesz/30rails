package com.siemens.mo.thirtyrails.player.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerEntity, String> {

    Optional<PlayerEntity> findByGameIdAndName(int gameId, String name);
}
