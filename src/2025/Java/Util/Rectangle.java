package Util;

import java.util.List;

public record Rectangle(int column1, int row1, int column2, int row2) {

    public static Rectangle fromPoints(Point2D pointA, Point2D pointB) {
        return new Rectangle(pointA.getColumn(), pointA.getRow(), pointB.getColumn(), pointB.getRow());
    }

    public boolean collides(List<Edge> edges) {
        int minColumn = Math.min(this.column1, this.column2);
        int maxColumn = Math.max(this.column1, this.column2);
        int minRow = Math.min(this.row1, this.row2);
        int maxRow = Math.max(this.row1, this.row2);
        for (Edge edge : edges) {
            if (minColumn < edge.getMaxColumn() && maxColumn > edge.getMinColumn() &&
                    minRow < edge.getMaxRow() && maxRow > edge.getMinRow()) {
                return true;
            }
        }
        return false;
    }

    public long getArea() {
        return (long) (Math.abs(this.column2 - this.column1) + 1) * (Math.abs(this.row2 - this.row1) + 1);
    }
}
