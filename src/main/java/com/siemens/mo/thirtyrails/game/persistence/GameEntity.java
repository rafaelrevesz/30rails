package com.siemens.mo.thirtyrails.game.persistence;

import com.siemens.mo.thirtyrails.diceroll.persistence.DiceRollEntity;
import com.siemens.mo.thirtyrails.game.Game;
import com.siemens.mo.thirtyrails.game.GameState;
import com.siemens.mo.thirtyrails.player.persistence.PlayerEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@Data
@Entity(name = "game")
@NoArgsConstructor
@RepositoryRestResource
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private GameState state = GameState.REGISTRATION;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<PlayerEntity> players;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<DiceRollEntity> diceRolls;

    public Game asGame() {
        return new Game(id, state);
    }
}
