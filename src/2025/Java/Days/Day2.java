package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day2 implements DailyChallenge {
    File inputFile;

    public Day2(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public int Part1(boolean debug) {
        int invalidIdSum = 0;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int[] ids = Arrays.stream(line.split("-")).mapToInt(Integer::parseInt).toArray();
                for (int i = ids[0]; i <= ids[1]; i++) {
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

    private boolean isInvalid(int number) {
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

    public int Part2(boolean debug) {
        throw new RuntimeException("Not implemented yet");
    }
}
