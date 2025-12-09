package Days;

import Util.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
            int[][] preSumRows = new int[maxRow + 2][maxColumn + 2];
            int[][] preSumColumns = new int[maxColumn + 2][maxRow + 2];
            for (int i = 0; i < points.size(); i++) {
                Point2D pointA = points.get(i);
                Point2D pointB = points.get((i + 1) % points.size());
                if (pointA.getRow() == pointB.getRow()) {
                    preSumRows[pointA.getRow()][Math.min(pointA.getColumn(), pointB.getColumn())]++;
                    preSumRows[pointA.getRow()][Math.max(pointA.getColumn(), pointB.getColumn()) + 1]--;
//                    preSumColumns[Math.min(pointA.getColumn(), pointB.getColumn())][pointA.getRow()]++;
//                    preSumColumns[Math.max(pointA.getColumn(), pointB.getColumn()) + 1][pointA.getRow()]--;
                } else {
//                    preSumRows[Math.min(pointA.getRow(), pointB.getRow())][pointA.getColumn()]++;
//                    preSumRows[Math.max(pointA.getRow(), pointB.getRow()) + 1][pointA.getColumn()]--;
                    preSumColumns[pointA.getColumn()][Math.min(pointA.getRow(), pointB.getRow())]++;
                    preSumColumns[pointA.getColumn()][Math.min(pointA.getRow(), pointB.getRow()) + 1]--;
                }
            }
            if (debug) {
                System.out.println("preSumRows: " + prettyPrint2dArray(preSumRows));
                System.out.println("preSumColumns: " + prettyPrint2dArray(preSumColumns));
            }
            if (debug) {
                StringBuilder sb = new StringBuilder();
                for (int[] preSumRow : preSumRows) {
                    int cur = 0;
                    for (int j = 0; j < preSumRows[0].length; j++) {
                        cur += preSumRow[j];
                        sb.append(cur);
                    }
                    sb.append("\n");
                }
                System.out.println(sb);
                System.out.println("\n");
                sb = new StringBuilder();
                for (int[] preSumColumn : preSumColumns) {
                    int cur = 0;
                    for (int j = 0; j < preSumColumns[0].length; j++) {
                        cur += preSumColumn[j];
                        sb.append(cur);
                    }
                    sb.append("\n");
                }
                System.out.println(sb);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return maxArea;
    }

    private String prettyPrint2dArray(int[][] preSumColumns) {
        StringBuilder sb = new StringBuilder("\n");
        for (int[] preSumColumn : preSumColumns) {
            for (int i : preSumColumn) {
                sb.append(String.format("%3d", i));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
