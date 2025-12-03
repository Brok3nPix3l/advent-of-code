package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day3 implements DailyChallenge {
    File inputFile;

    public Day3(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        long totalJoltage = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (debug) {
                    System.out.println("\n" + line);
                }
                char[] joltage = new char[2];
                int largestDigit = 0, secondLargestDigit = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (debug) {
                        if (line.charAt(i) - '0' > largestDigit) {
                            secondLargestDigit = largestDigit;
                            largestDigit = line.charAt(i) - '0';
                        } else if (line.charAt(i) - '0' > secondLargestDigit) {
                            secondLargestDigit = line.charAt(i) - '0';
                        }
                    }
                    if (line.charAt(i) < joltage[0] && line.charAt(i) < joltage[1]) {
                        continue;
                    }
                    if (line.charAt(i) > joltage[0] && i < line.length() - 1) {
                        joltage[0] = line.charAt(i);
                        joltage[1] = line.charAt(i + 1);
                    } else {
                        joltage[1] = line.charAt(i);
                    }
                    if (joltage[0] == '9' && joltage[1] == '9') {
                        break;
                    }
                }
                if (debug) {
                    System.out.println("largest digit=" + largestDigit + " secondLargestDigit=" + secondLargestDigit);
                    System.out.println(line.replaceAll("[^" + largestDigit + secondLargestDigit + "]", ""));
                    System.out.println(line.substring(line.indexOf(largestDigit + '0')));
                    System.out.println(Integer.parseInt(new String(joltage)) + " joltage");
                }
                totalJoltage += Integer.parseInt(new String(joltage));
                if (debug) {
                    System.out.println(totalJoltage + " total joltage");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return totalJoltage;
    }

    public long Part2(boolean debug) {
        throw new RuntimeException("Not implemented yet");
    }
}
