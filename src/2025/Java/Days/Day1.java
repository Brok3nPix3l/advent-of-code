package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1 implements DailyChallenge {
    File inputFile;

    public Day1(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public int Part1(boolean debug) {
        int dialPosition = 50;
        int restAtZeroCount = 0;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.charAt(0) == 'L') {
                    int distance = Integer.parseInt(line.substring(1));
                    dialPosition = (dialPosition - distance) % 100;
                } else if (line.charAt(0) == 'R') {
                    int distance = Integer.parseInt(line.substring(1));
                    dialPosition = (dialPosition + distance) % 100;
                } else {
                    throw new RuntimeException("Unexpected leading character: " + line);
                }
                if (dialPosition == 0) {
                    restAtZeroCount++;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return restAtZeroCount;
    }

    public int Part2(boolean debug) {
        int dialPosition = 50;
        int passZeroCount = 0;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (debug) {
                    System.out.println(line);
                }
                if (line.charAt(0) == 'L') {
                    int distance = Integer.parseInt(line.substring(1));
                    // if 100 - dialPosition < distance, we're passing zero at least once
                    // if 100 - dialPosition + distance >= 200, we're passing zero at least twice
                    // if we're at 0, then 100 - 0 always counts as passing zero once; so handle this edge case specially
                    int addToPassZeroCount = (int) Math.floor((double) ((dialPosition == 0 ? 0 : 100 - dialPosition) + distance) / 100);
                    passZeroCount += addToPassZeroCount;
                    dialPosition = Math.floorMod(dialPosition - distance, 100);
                    if (debug) {
                        System.out.println("moving left " + distance + ". passed zero " + addToPassZeroCount + " times. dialPosition is now " + dialPosition);
                    }
                } else if (line.charAt(0) == 'R') {
                    int distance = Integer.parseInt(line.substring(1));
                    // if dialPosition + distance > 100, we're passing zero at least once
                    // if dialPosition + distance > 200, we're passing zero at least twice
                    int addToPassZeroCount = (int) Math.floor((double) (dialPosition + distance) / 100);
                    passZeroCount += addToPassZeroCount;
                    dialPosition = Math.floorMod(dialPosition + distance, 100);
                    if (debug) {
                        System.out.println("moving right " + distance + ". passed zero " + addToPassZeroCount + " times. dialPosition is now " + dialPosition);
                    }
                } else {
                    throw new RuntimeException("Unexpected leading character: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return passZeroCount;
    }
}
