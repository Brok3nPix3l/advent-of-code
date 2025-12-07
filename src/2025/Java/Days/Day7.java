package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 implements DailyChallenge {
    File inputFile;
    private static final char SPLITTER = '^';
    private static final char EMPTY_SPACE = '.';

    public Day7(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    public long Part1(boolean debug) {
        long splitCount = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            Queue<int[]> queue = new LinkedList<>();
            List<String> lines = new ArrayList<>();
            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
                int startingColumn = line.indexOf('S');
                if (startingColumn != -1) {
                    queue.add(new int[]{row, startingColumn});
                }
                row++;
            }
            if (debug) {
                System.out.println(queue.stream().map(Arrays::toString).collect(Collectors.toList()));
                System.out.println(lines);
            }
            Map<Integer,Map<Integer,Boolean>> visited = new HashMap<>();
            while (!queue.isEmpty()) {
                int[] pos = queue.poll();
                if (pos[0] >= lines.size() - 1 || (visited.containsKey(pos[0]) && visited.get(pos[0]).containsKey(pos[1]) && visited.get(pos[0]).get(pos[1]))) {
                    continue;
                }
                visited.computeIfAbsent(pos[0], a -> new HashMap<>()).put(pos[1], true);
                switch (lines.get(pos[0] + 1).charAt(pos[1])) {
                    case SPLITTER:
                        splitCount++;
                        // add left split
                        if (pos[1] > 0) {
                            queue.add(new int[]{pos[0] + 1, pos[1] - 1});
                        }
                        // add right split
                        if (pos[1] < lines.get(pos[0] + 1).length() - 1) {
                            queue.add(new int[]{pos[0] + 1, pos[1] + 1});
                        }
                        break;
                    case EMPTY_SPACE:
                        queue.add(new int[]{pos[0] + 1, pos[1]});
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return splitCount;
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
