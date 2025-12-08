package Util;

import java.util.Objects;

public class Position2D {
    private int row;
    private int column;

    public Position2D(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Position2D fromArray(int[] startingPosition) {
        return new Position2D(startingPosition[0], startingPosition[1]);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Position2D position = (Position2D) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Position{" + "row=" + row + ", column=" + column + '}';
    }
}
