package com.siemens.mo.thirtyrails.player;

import com.siemens.mo.thirtyrails.diceroll.DicePair;

public record PlayerState(String name, int turn, boolean mountainsSet, boolean mineSet, boolean stationsSet, DicePair currentDicePair) {

}
