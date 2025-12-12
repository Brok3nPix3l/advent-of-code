package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day12 implements DailyChallenge {
    File inputFile;

    public Day12(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        long ans = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            boolean readingPresents = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
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
