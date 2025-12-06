package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day6 implements DailyChallenge {
    File inputFile;

    public Day6(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    // split each line on whitespace (trim leading and trailing whitespace)
    // check for a "+" or "*" to decide if we're at the end of the file
    // iterate through each index (problem) and perform the current operation to those numbers, then add to the
    // grand total

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

    // 123 328
    //  45 64
    //   6 98
    // *   +
    // [[[1],[2,4],[3,5,6]],[[3,6,9],[2,4,8],[8]]]

    // since the operators are always aligned with the first column (number) of a problem, can use that to
    // determine where the breaks are
    public long Part2(boolean debug) {
        long ans = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            Map<Integer, Integer> digitsByColumnMap = new HashMap<>();
            int maxColumn = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("*") || line.startsWith("+")) {
                    char curOperator = line.charAt(0);
                    long curProblemAns = 0;
                    for (int i = 0; i < maxColumn; i++) {
                        if (i < line.length() && line.charAt(i) != ' ') {
                            ans += curProblemAns;
                            if (debug) {
                                System.out.println("curProblemAns=" + curProblemAns);
                            }
                            curOperator = line.charAt(i);
                            curProblemAns = curOperator == '+' ? 0L : 1L;
                        }
                        if (digitsByColumnMap.containsKey(i)) {
                            switch (curOperator) {
                                case '+':
                                    curProblemAns += digitsByColumnMap.get(i);
                                    if (debug) {
                                        System.out.println("adding " + digitsByColumnMap.get(i));
                                    }
                                    break;
                                case '*':
                                    curProblemAns *= digitsByColumnMap.get(i);
                                    if (debug) {
                                        System.out.println("multiplying " + digitsByColumnMap.get(i));
                                    }
                            }
                        }
                    }
                    ans += curProblemAns;
                    if (debug) {
                        System.out.println("curProblemAns=" + curProblemAns);
                    }
                }
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ' ') {
                        continue;
                    }
                    digitsByColumnMap.put(i, digitsByColumnMap.getOrDefault(i, 0) * 10 + line.charAt(i) - '0');
                }
                maxColumn = Math.max(maxColumn, line.length());
            }
            if (debug) {
                System.out.println(digitsByColumnMap);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }
}
