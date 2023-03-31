package com.siemens.mo.thirtyrails.map;

public enum Direction {
    LEFT, UP, RIGHT, DOWN, STOP;

    public Direction opposite() {
        return switch (this) {
            case LEFT -> RIGHT;
            case UP -> DOWN;
            case DOWN -> UP;
            case RIGHT -> LEFT;
            case STOP -> STOP;
        };
    }
}
