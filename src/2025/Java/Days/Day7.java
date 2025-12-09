package Days;

import Util.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 implements DailyChallenge {
    private static final char SPLITTER = '^';
    private static final char EMPTY_SPACE = '.';
    File inputFile;

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
            Map<Integer, Map<Integer, Boolean>> visited = new HashMap<>();
            while (!queue.isEmpty()) {
                int[] pos = queue.poll();
                if (pos[0] >= lines.size() - 1 ||
                        (visited.containsKey(pos[0]) && visited.get(pos[0]).containsKey(pos[1]) &&
                                visited.get(pos[0]).get(pos[1]))) {
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
        long timelineCount;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            int[] startingPosition = new int[2];
            List<String> lines = new ArrayList<>();
            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
                int startingColumn = line.indexOf('S');
                if (startingColumn != -1) {
                    startingPosition = new int[]{row, startingColumn};
                }
                row++;
            }
            if (debug) {
                System.out.println(Arrays.toString(startingPosition));
                System.out.println(lines);
            }
            Map<Point2D, Long> memo = new HashMap<>();
            timelineCount = traverse(Point2D.fromArray(startingPosition), memo, lines);
            // dfs
            // call recursive function on starting node
            // each time it reaches a fork, it'll call the recursive function for each new node
            // if the node has already been visited, and has an entry in the memo map, return that value instead
            // store the found value in the memo map
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return timelineCount;
    }

    private long traverse(Point2D pos, Map<Point2D, Long> memo, List<String> lines) {
        if (memo.containsKey(pos)) {
            return memo.get(pos);
        }
        long timelineCount = 0L;
        if (pos.getRow() >= lines.size() - 1) {
            return 1L;
        }
        switch (lines.get(pos.getRow() + 1).charAt(pos.getColumn())) {
            case SPLITTER:
                // add left split
                if (pos.getColumn() > 0) {
                    timelineCount += traverse(new Point2D(pos.getRow() + 1, pos.getColumn() - 1), memo, lines);
                }
                // add right split
                if (pos.getColumn() < lines.get(pos.getRow() + 1).length() - 1) {
                    timelineCount += traverse(new Point2D(pos.getRow() + 1, pos.getColumn() + 1), memo, lines);
                }
                break;
            case EMPTY_SPACE:
                timelineCount += traverse(new Point2D(pos.getRow() + 1, pos.getColumn()), memo, lines);
        }
        memo.put(pos, timelineCount);
        return timelineCount;
    }
}
