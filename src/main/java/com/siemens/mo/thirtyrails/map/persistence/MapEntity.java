package com.siemens.mo.thirtyrails.map.persistence;

import com.siemens.mo.thirtyrails.game.persistence.GameEntity;
import com.siemens.mo.thirtyrails.player.Player;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@Data
@Entity(name = "map")
@NoArgsConstructor
@RepositoryRestResource
public class MapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    private GameEntity game;

    private String playerName;
    private int turn;
    private boolean mountainSkipped;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "map")
    private List<MapItemEntity> mapItems;

    public Player asPlayer() {
        return new Player(id, playerName, turn);
    }

}
