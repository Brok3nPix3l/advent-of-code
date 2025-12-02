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
                    if (isDoublet(i)) {
                        invalidIdSum += i;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return invalidIdSum;
    }

    /**
     * A number with the same sequence of digits repeated twice is sometimes called a doublet. Checks if the provided number is exactly a series of numbers repeated exactly once. e.g. 1212 or 123123
     * @param number number to be evaluated
     * @return true if is a doublet, false otherwise
     */
    private boolean isDoublet(long number) {
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
        long invalidIdSum = 0;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String idRange = scanner.next();
                String[] ids = idRange.split("-");
                for (long i = Long.parseLong(ids[0]); i <= Long.parseLong(ids[1]); i++) {
                    if (isRepeatedDigitSequence(i)) {
                        if (debug) {
                            System.out.println(i + " isRepeatedDigitSequence");
                        }
                        invalidIdSum += i;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return invalidIdSum;
    }

    private boolean isRepeatedDigitSequence(long number) {
        String str = String.valueOf(number);
        outer:
        for (int size = 1; size <= str.length() / 2; size++) {
            if (str.length() % size != 0) {
                continue;
            }
            for (int i = 0; i < size; i++) {
                for (int j = i + size; j < str.length(); j += size) {
                    if (str.charAt(i) != str.charAt(j)) {
                        continue outer;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
