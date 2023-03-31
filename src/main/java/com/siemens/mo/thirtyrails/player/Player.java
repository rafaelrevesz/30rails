package com.siemens.mo.thirtyrails.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Player {
    private final int id;
    private final String name;
    private final int turn;
}
