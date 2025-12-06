package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day6 implements DailyChallenge {
    File inputFile;

    public Day6(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    // split each line on whitespace (trim leading and trailing whitespace)
    // check for a "+" or "*" to decide if we're at the end of the file
    // iterate through each index (problem) and perform the current operation to those numbers, then add to the grand total

    // 1 2 3
    // 4 5 6
    // * + +
    // [[1,4],[2,5],[3,6]]
    public long Part1(boolean debug) {
        long ans = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            List<List<Long>> numbers = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                // perform operations
                if (line.startsWith("*") || line.startsWith("+")) {
                    String[] operations = line.split("\s+");
                    for (int problemNumber = 0; problemNumber < operations.length; problemNumber++) {
                        switch (operations[problemNumber]) {
                            case "+":
                                ans += numbers.get(problemNumber).stream().reduce(0L, Long::sum);
                                break;
                            case "*":
                                ans += numbers.get(problemNumber).stream().reduce(1L, (a, b) -> a * b);
                        }
                    }
                    break;
                }
                // add each number from the current row to its respective list
                List<Long> numberList = Arrays.stream(line.split("\s+")).map(Long::parseLong).toList();
                int problemNumber = 0;
                for (long number : numberList) {
                    while (numbers.size() < problemNumber + 1) {
                        numbers.add(new ArrayList<>());
                    }
                    numbers.get(problemNumber).add(number);
                    problemNumber++;
                }
            }
            if (debug) {
                System.out.println(numbers);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
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
