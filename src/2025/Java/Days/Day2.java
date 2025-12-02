package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 implements DailyChallenge {
    File inputFile;

    public Day2(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        long invalidIdSum = 0;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String idRange = scanner.next();
                String[] ids = idRange.split("-");
                for (long i = Long.parseLong(ids[0]); i <= Long.parseLong(ids[1]); i++) {
                    if (isInvalid(i)) {
                        invalidIdSum += i;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return invalidIdSum;
    }

    private boolean isInvalid(long number) {
        String str = String.valueOf(number);
        if (str.length() % 2 != 0) {
            return false;
        }
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(i + str.length() / 2)) {
                return false;
            }
        }
        return true;
    }

    public long Part2(boolean debug) {
        throw new RuntimeException("Not implemented yet");
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < ids[0].length(); i++) {
//            if (ids[0].charAt(i) != ids[1].charAt(i)) {
//                break;
//            }
//            sb.append(ids[0].charAt(i));
//        }
//        String prefix = sb.toString();
//        int minLength = ids[0].length();
//        int maxLength = ids[1].length();
//        int diff = maxLength - minLength;
//        // if there is a shared prefix, then all invalid IDs MUST start with that prefix
//        // else, all prefixes from low to high could have invalid IDs
//            // if low and high are the same length, can iterate from low's first digit to high's first digit
//            // else, low's prefix is its first digit and high's prefix is its first -> 'diff' digit. e.g. 95 -> 115 the prefixes are 9 and 11
//        if (debug) {
//            System.out.println("ids: " + Arrays.toString(ids) + " prefix=" + prefix + " minLength=" + minLength + " maxLength=" + maxLength + " diff=" + diff);
//        }
    }
}
