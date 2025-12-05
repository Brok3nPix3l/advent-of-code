package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day5 implements DailyChallenge {
    File inputFile;

    public Day5(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        List<Long[]> ranges = new ArrayList<>();
        boolean readingRanges = true;
        long freshIngredientIdCount = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (debug) {
                    System.out.println(line);
                }
                if (line.isEmpty()) {
                    readingRanges = false;
                    continue;
                }
                if (readingRanges) {
                    ranges.add(Arrays.stream(line.split("-")).map(Long::parseLong).toArray(Long[]::new));
                    continue;
                }
                long ingredientId = Long.parseLong(line);
                for (Long[] range : ranges) {
                    if (range[0] <= ingredientId && ingredientId <= range[1]) {
                        freshIngredientIdCount++;
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return freshIngredientIdCount;
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
