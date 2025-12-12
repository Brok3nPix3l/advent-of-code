package Days;

import Util.Tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day12 implements DailyChallenge {
    File inputFile;

    public Day12(File inputFile) throws FileNotFoundException {
        new Scanner(inputFile);
        this.inputFile = inputFile;
    }

    // dfs / bfs?
    // grids seem pretty small, so try simulating and brute forcing solutions?
    // if valid solution found, increment answer and continue
    // if all solutions tried and no valid solution found, continue

    // need to handle piece rotations as well
    // can try brute forcing all positions and rotations
    // if a piece overlaps with an existing piece, continue
    // if a piece is placed successfully, decrement the required count
    // if no more required pieces, then it's a valid solution
    public long Part1(boolean debug) {
        long ans = 0L;
        try (Scanner scanner = new Scanner(this.inputFile)) {
            boolean readingPresents = true;
            Map<Integer, List<String>> presents = new HashMap<>();
            int currentPresentNumber = -1;
            List<Tree> trees = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    continue;
                }
                if (readingPresents && line.contains("x")) {
                    readingPresents = false;
                }
                if (readingPresents) {
                    if (line.contains(":")) {
                        int presentNumber = Integer.parseInt(line.split(":")[0]);
                        currentPresentNumber = presentNumber;
                        presents.put(presentNumber, new ArrayList<>());
                    } else {
                        presents.get(currentPresentNumber).add(line);
                    }
                } else {
                    trees.add(Tree.fromLine(line));
                }
            }
            if (debug) {
                System.out.println("presents: " + presents);
                System.out.println("trees: " + trees);
            }
            for (int i = 0; i < trees.size(); i++) {
                Tree tree = trees.get(i);
                if (!tree.isFeasibleToPlacePresents(presents, debug, i)) {
                    continue;
                }
                if (tree.canPlaceAllPresents(presents, debug)) {
                    if (debug) {
                        System.out.println("can place all presents under tree " + i);
                    }
                    ans++;
                } else {
                    if (debug) {
                        System.out.println("can not place all presents under tree " + i);
                    }
                }
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
