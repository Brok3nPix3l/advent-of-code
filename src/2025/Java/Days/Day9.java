package Days;

import Util.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day9 implements DailyChallenge {
    File inputFile;

    public Day9(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        long maxArea = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            List<Point2D> points = new ArrayList<>();
            long maxPointCoordinate = 0L;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Point2D point = Point2D.fromArray(line.split(","));
                points.add(point);
                if (point.getRow() > maxPointCoordinate) {
                    maxPointCoordinate = point.getRow();
                }
                if (point.getColumn() > maxPointCoordinate) {
                    maxPointCoordinate = point.getColumn();
                }
            }
            if (debug) {
                System.out.println("Max point coordinate: " + maxPointCoordinate);
            }
            for (int i = 0; i < points.size(); i++) {
                Point2D pointA = points.get(i);
                for (int j = i + 1; j < points.size(); j++) {
                    Point2D pointB = points.get(j);
                    long curArea = Point2D.areaBetweenCorners(pointA, pointB);
                    if (curArea > maxArea) {
                        maxArea = curArea;
                        if (debug) {
                            System.out.println("Max area: " + maxArea + " between points: " + pointA + " and " + pointB);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return maxArea;
    }

    public long Part2(boolean debug) {
        long maxArea = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            List<Point2D> points = new ArrayList<>();
            int maxRow = 0, maxColumn = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Point2D point = Point2D.fromArray(line.split(","));
                points.add(point);
                maxRow = Math.max(maxRow, point.getRow());
                maxColumn = Math.max(maxColumn, point.getColumn());
            }
            if (debug) {
                System.out.println("Max row: " + maxRow + " max column: " + maxColumn);
            }
            char[][] grid = new char[maxRow + 1][maxColumn + 1];
            for (char[] row : grid) {
                Arrays.fill(row, '.');
            }
            for (int i = 0; i < points.size(); i++) {
                Point2D pointA = points.get(i);
                Point2D pointB = points.get((i + 1) % points.size());
                if (pointA.getRow() == pointB.getRow()) {
                    for (int column = Math.min(pointA.getColumn(), pointB.getColumn()); column <= Math.max(pointA.getColumn(), pointB.getColumn()); column++) {
                        grid[pointA.getRow()][column] = '#';
                    }
                } else {
                    for (int row = Math.min(pointA.getRow(), pointB.getRow()); row <= Math.max(pointA.getRow(), pointB.getRow()); row++) {
                        grid[row][pointA.getColumn()] = '#';
                    }
                }
            }
            Point2D floodFillEntry = null;
            for (int i = 0; i < points.size(); i++) {
                Point2D prevPoint = points.get(i);
                Point2D curPoint = points.get((i + 1) % points.size());
                Point2D nextPoint = points.get((i + 2) % points.size());
                if (prevPoint.getRow() == curPoint.getRow()) {
                    if (curPoint.getRow() == nextPoint.getRow()) {
                        continue;
                    }
                    // curPoint and nextPoint share a column
                    int rowDelta;
                    int columnDelta;
                    if (prevPoint.getColumn() < curPoint.getColumn()) {
                        columnDelta = -1;
                    } else {
                        columnDelta = 1;
                    }
                    if (curPoint.getRow() < nextPoint.getRow()) {
                        rowDelta = 1;
                    } else {
                        rowDelta = -1;
                    }
                    floodFillEntry = new Point2D(curPoint.getRow() + rowDelta, curPoint.getColumn() + columnDelta);
                    break;
                }
                // prevPoint and curPoint are on different rows
                if (prevPoint.getColumn() == curPoint.getColumn()) {
                    continue;
                }
                int rowDelta;
                int columnDelta;
                if (prevPoint.getRow() < curPoint.getRow()) {
                    rowDelta = -1;
                } else {
                    rowDelta = 1;
                }
                if (curPoint.getColumn() < nextPoint.getColumn()) {
                    columnDelta = 1;
                } else {
                    columnDelta = -1;
                }
                floodFillEntry = new Point2D(curPoint.getRow() + rowDelta, curPoint.getColumn() + columnDelta);
                break;
            }
            if (debug) {
                System.out.println("grid: " + prettyPrint2dArray(grid));
            }
            char fillWith = '#';
            floodFill(grid, floodFillEntry, fillWith);
            if (debug) {
                System.out.println("grid: " + prettyPrint2dArray(grid));
            }

            for (int i = 0; i < points.size(); i++) {
                Point2D pointA = points.get(i);
                for (int j = i + 1; j < points.size(); j++) {
                    Point2D pointB = points.get(j);
                    boolean canUseArea = true;
                    outer:
                    for (int r = Math.min(pointA.getRow(), pointB.getRow()); r <= Math.max(pointA.getRow(), pointB.getRow()); r++) {
                        for (int c = Math.min(pointA.getColumn(), pointB.getColumn()); c <= Math.max(pointA.getColumn(), pointB.getColumn()); c++) {
                            if (grid[r][c] != '#') {
                                canUseArea = false;
                                break outer;
                            }
                        }
                    }
                    if (!canUseArea) {
                        continue;
                    }
                    long curArea = Point2D.areaBetweenCorners(pointA, pointB);
                    if (curArea > maxArea) {
                        maxArea = curArea;
                        if (debug) {
                            System.out.println("Max area: " + maxArea + " between points: " + pointA + " and " + pointB);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return maxArea;
    }

    private void floodFill(char[][] grid, Point2D point, char fillWith) {
        Queue<Point2D> queue = new LinkedList<>();
        queue.add(point);
        while (!queue.isEmpty()) {
            Point2D curPoint = queue.poll();
            if (isOutOfBounds(grid, curPoint)) {
                continue;
            }
            if (grid[curPoint.getRow()][curPoint.getColumn()] != '.') {
                continue;
            }
            grid[curPoint.getRow()][curPoint.getColumn()] = fillWith;
            int[][] directions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
            for (int[] direction : directions) {
                Point2D newPoint = new Point2D(curPoint.getRow() + direction[0], curPoint.getColumn() + direction[1]);
                if (isOutOfBounds(grid, newPoint)) {
                    continue;
                }
                queue.add(newPoint);
            }
        }
    }

    private static boolean isOutOfBounds(char[][] grid, Point2D newPoint) {
        return newPoint.getRow() < 0 || newPoint.getRow() >= grid.length || newPoint.getColumn() < 0 || newPoint.getColumn() >= grid[0].length;
    }

    private String prettyPrint2dArray(char[][] preSumColumns) {
        StringBuilder sb = new StringBuilder("\n");
        for (char[] preSumColumn : preSumColumns) {
            for (char i : preSumColumn) {
                sb.append(String.format("%3s", i));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
