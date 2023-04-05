package com.siemens.mo.thirtyrails.diceroll.persistence;

import com.siemens.mo.thirtyrails.diceroll.Dice;
import com.siemens.mo.thirtyrails.diceroll.DicePair;
import com.siemens.mo.thirtyrails.game.persistence.GameEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import static com.siemens.mo.thirtyrails.diceroll.DiceType.WHITE;
import static com.siemens.mo.thirtyrails.diceroll.DiceType.RED;

@Data
@Entity(name = "diceroll")
@NoArgsConstructor
@RepositoryRestResource
public class DiceRollEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private int numberOfTurn;
    private int whiteValue;

    private int redValue;

    @ManyToOne
    private GameEntity game;

    public static DiceRollEntity of(int numberOfTurn, int whiteValue, int redValue) {
        var entity = new DiceRollEntity();
        entity.setNumberOfTurn(numberOfTurn);
        entity.setWhiteValue(whiteValue);
        entity.setRedValue(redValue);
        return entity;
    }
    public DicePair asDicePair() {
        return new DicePair(new Dice(whiteValue, WHITE), new Dice(redValue, RED));
    }
}
