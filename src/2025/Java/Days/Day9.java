package Days;

import Util.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        throw new RuntimeException("Not implemented yet");
//        try (Scanner scanner = new Scanner(this.inputFile)) {
//            while (scanner.hasNextLine()) {
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }
}
