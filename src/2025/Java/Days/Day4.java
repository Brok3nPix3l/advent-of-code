package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day4 implements DailyChallenge {
    File inputFile;
    static final char ROLL_OF_PAPER = '@';
    static final String NOT_ROLL_OF_PAPER = ".";

    public Day4(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        try (Scanner scanner = new Scanner(this.inputFile)) {
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            if (debug) {
                System.out.println(lines);
            }
            long ans = 0L;
            for (int i = 0; i < lines.size(); i++) {
                String currentLine = lines.get(i);
                for (int j = 0, len = currentLine.length(); j < len; j++) {
                    if (currentLine.charAt(j) == ROLL_OF_PAPER && hasFewerThanFourAdjacentRollsOfPaper(i, j, lines)) {
                        if (debug) {
                            System.out.println("(" + i + "," + j + ")");
                        }
                        ans++;
                    }
                }
            }
            return ans;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasFewerThanFourAdjacentRollsOfPaper(int row, int column, List<String> lines) {
        int adjacentRollsOfPaperCount = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            if (r < 0 || r == lines.size()) {
                continue;
            }
            String currentLine = lines.get(r);
            for (int c = column - 1; c <= column + 1; c++) {
                if (c < 0 || r == row && c == column || c == currentLine.length()) {
                    continue;
                }
                if (currentLine.charAt(c) == ROLL_OF_PAPER) {
                    adjacentRollsOfPaperCount++;
                }
            }
        }
        return adjacentRollsOfPaperCount < 4;
    }

    public long Part2(boolean debug) {
        try (Scanner scanner = new Scanner(this.inputFile)) {
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            if (debug) {
                System.out.println(lines);
            }
            long ans = 0L;
            long paperRollsRemovedInCurrentCheck;
            List<String> nextLines;
            do {
                paperRollsRemovedInCurrentCheck = 0L;
                 nextLines = new ArrayList<>();
                for (int i = 0; i < lines.size(); i++) {
                    String currentLine = lines.get(i);
                    StringBuilder nextLine = new StringBuilder(currentLine);
                    for (int j = 0, len = currentLine.length(); j < len; j++) {
                        if (currentLine.charAt(j) == ROLL_OF_PAPER && hasFewerThanFourAdjacentRollsOfPaper(i, j, lines)) {
                            if (debug) {
                                System.out.println("(" + i + "," + j + ")");
                            }
                            paperRollsRemovedInCurrentCheck++;
                            nextLine.replace(j, j + 1, NOT_ROLL_OF_PAPER);
                        }
                    }
                    nextLines.add(nextLine.toString());
                }
                ans += paperRollsRemovedInCurrentCheck;
                lines = nextLines;
            } while (paperRollsRemovedInCurrentCheck > 0);
            return ans;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
