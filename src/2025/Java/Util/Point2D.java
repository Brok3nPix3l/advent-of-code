package Util;

import java.util.Objects;

public class Point2D {
    private int row;
    private int column;

    public Point2D(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Point2D fromArray(int[] coords) {
        return new Point2D(coords[0], coords[1]);
    }

    public static Point2D fromArray(String[] coords) {
        int column = Integer.parseInt(coords[0]);
        int row = Integer.parseInt(coords[1]);
        return new Point2D(row, column);
    }

    /**
     * Computes the area between two points, treating them as opposite corners
     * @param pointA
     * @param pointB
     * @return
     */
    public static long areaBetweenCorners(Point2D pointA, Point2D pointB) {
        long raw = (long) (pointA.getRow() - pointB.getRow() + 1) * (pointA.getColumn() - pointB.getColumn() + 1);
        return Math.abs(raw);
    }

    public static Point2D applyVector(Point2D point2D, int[] direction) {
        return new Point2D(point2D.getRow() + direction[0], point2D.getColumn() + direction[1]);
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
        Point2D position = (Point2D) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Position{" + "column=" + column + ", row=" + row + '}';
    }
}
