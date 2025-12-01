import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1 implements DailyChallenge {
    File inputFile;

    public Day1(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public int Part1() {
        int dialPosition = 50;
        int restAtZeroCount = 0;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.charAt(0) == 'L') {
                    int distance = Integer.parseInt(line.substring(1));
                    dialPosition = (dialPosition + distance) % 100;
                } else if (line.charAt(0) == 'R') {
                    int distance = Integer.parseInt(line.substring(1));
                    dialPosition = (dialPosition - distance) % 100;
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

    public int Part2() {
        throw new RuntimeException("Not implemented yet");
    }
}
