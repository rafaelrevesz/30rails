package com.siemens.mo.thirtyrails.position;

public record Position(int col, int row) {

    public boolean isNextTo(Position otherPosition) {
        if (row == otherPosition.row) {
            return 1 == Math.abs(col - otherPosition.col);
        }
        if (col == otherPosition.col) {
            return 1 == Math.abs(row - otherPosition.row);
        }
        return false;
    }
}
