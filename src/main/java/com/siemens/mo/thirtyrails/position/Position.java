package com.siemens.mo.thirtyrails.position;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record Position(@Min(1) @Max(6) int col, @Min(1) @Max(6) int row) {

    public boolean isNextTo(Position otherPosition) {
        if (row == otherPosition.row) {
            return 1 == Math.abs(col - otherPosition.col);
        }
        if (col == otherPosition.col) {
            return 1 == Math.abs(row - otherPosition.row);
        }
        return false;
    }

    public String index() {
        return col + "" + row;
    }

    public static Position of(String index) {
        return new Position(Integer.parseInt(index.substring(0, 1)), Integer.parseInt(index.substring(1)));
    }
}
