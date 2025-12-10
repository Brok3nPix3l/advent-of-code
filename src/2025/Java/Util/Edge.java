package Util;

public record Edge(Point2D pointA, Point2D pointB) {
    public int getMinRow() {
        return Math.min(pointA.getRow(), pointB.getRow());
    }
    public int getMaxRow() {
        return Math.max(pointA.getRow(), pointB.getRow());
    }
    public int getMinColumn() {
        return Math.min(pointA.getColumn(), pointB.getColumn());
    }
    public int getMaxColumn() {
        return Math.max(pointA.getColumn(), pointB.getColumn());
    }
}
