package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DayTemplate implements DailyChallenge {
    File inputFile;

    public DayTemplate(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        throw new RuntimeException("Not implemented yet");
//        try (Scanner scanner = new Scanner(this.inputFile)) {
//            while (scanner.hasNextLine()) {
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
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
