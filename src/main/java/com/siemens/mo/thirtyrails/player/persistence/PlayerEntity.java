package com.siemens.mo.thirtyrails.player.persistence;

import com.siemens.mo.thirtyrails.game.persistence.GameEntity;
import com.siemens.mo.thirtyrails.map.persistence.MapEntity;
import com.siemens.mo.thirtyrails.player.Player;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity(name = "player")
@NoArgsConstructor
@RepositoryRestResource
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private int turn;
    private boolean mountainSkipped;
    @ManyToOne
    private GameEntity game;

    @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
    @JoinColumn(name = "map_id", referencedColumnName = "id")
    private MapEntity map;

    public Player asPlayer() {
        return new Player(id, name, turn);
    }
}
