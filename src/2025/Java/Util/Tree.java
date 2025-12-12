package Util;

import java.util.*;

public class Tree {
    private static final char PRESENT = '#';
    private static final char EMPTY = '.';
    char[][] grid;
    private int width;
    private int height;
    private Map<Integer, Integer> requiredPresents;
    private final Stack<Present> placedPresents;

    public Tree(int width, int height, Map<Integer, Integer> requiredPresents) {
        this.width = width;
        this.height = height;
        this.grid = new char[width][height];
        for (char[] row : grid) {
            Arrays.fill(row, EMPTY);
        }
        this.requiredPresents = requiredPresents;
        this.placedPresents = new Stack<>();
    }

    public Tree(Tree tree) {
        this.width = tree.width;
        this.height = tree.height;
        this.grid = new char[width][height];
        for (int i = 0; i < width; i++) {
            System.arraycopy(tree.grid[i], 0, this.grid[i], 0, height);
        }
        this.requiredPresents = new HashMap<>(tree.requiredPresents);
        this.placedPresents = new Stack<>();
        this.placedPresents.addAll(tree.placedPresents);
    }

    public static Tree fromLine(String line) {
        String[] parts = line.split(": ");
        String[] dimensions = parts[0].split("x");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        Map<Integer, Integer> requiredPresents = new HashMap<>();
        String[] requiredPresentCounts = parts[1].split(" ");
        for (int i = 0; i < requiredPresentCounts.length; i++) {
            requiredPresents.put(i, Integer.parseInt(requiredPresentCounts[i]));
        }
        return new Tree(width, height, requiredPresents);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void setGrid(char[][] grid) {
        this.grid = grid;
    }

    public Map<Integer, Integer> getRequiredPresents() {
        return requiredPresents;
    }

    public void setRequiredPresents(Map<Integer, Integer> requiredPresents) {
        this.requiredPresents = requiredPresents;
    }

    @Override
    public String toString() {
        return "Tree{" + "grid=" + Arrays.deepToString(grid) + ", requiredPresents=" + requiredPresents + '}';
    }

    // if I'm able to place the 1st present but not the second present, then I need to try placing the 1st present
    // in another position/rotation/etc.
    public boolean canPlaceAllPresents(Map<Integer, List<String>> presents, boolean debug) {
        List<Integer> requiredPresentNumbers =
                this.requiredPresents.entrySet().stream().filter(e -> e.getValue() != 0).map(Map.Entry::getKey)
                        .toList();
        if (requiredPresentNumbers.isEmpty()) {
            return true;
        }
        for (int presentNumber : requiredPresentNumbers) {
            List<String> currentPresentList = presents.get(presentNumber);
            char[][] currentPresentArr = to2dCharArray(currentPresentList);
            Present currentPresent = new Present(presentNumber, 0, false, currentPresentArr, 0, 0);
            Tree tree = new Tree(this);
            if (!tree.attemptToPlaceAllPresentRotations(currentPresent)) {
                continue;
            }
//            if (debug) {
//                prettyPrint(tree.getGrid());
//            }
            tree.getRequiredPresents().put(presentNumber, tree.getRequiredPresents().get(presentNumber) - 1);
            if (tree.canPlaceAllPresents(presents, debug)) {
                return true;
            }
        }
        return false;
    }

//    private void prettyPrint(char[][] grid) {
//        for (char[] row : grid) {
//            for (char aChar : row) {
//                System.out.print(aChar);
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }

    // try all possible positions on the grid, in all four rotations, to place the present
    // assumption: all presents are always 3x3
    private boolean attemptToPlaceAllPresentRotations(Present present) {
        // iterate over all rotations of present
        do {
            List<Present> presentRotations = generateRotationsAndFlips(present);
            for (Present presentRotation : presentRotations) {
                // position is the top-left corner of present
                for (int r = 0; r < width - 2; r++) {
                    for (int c = 0; c < height - 2; c++) {
                        if (attemptToPlacePresentAtPosition(presentRotation, r, c)) {
                            presentRotation.setHorizontalOffset(r + 1);
                            presentRotation.setVerticalOffset(c);
                            this.placedPresents.push(presentRotation);
                            return true;
                        }
                    }
                }
            }
            removePresent(this.placedPresents.pop());
        } while (!this.placedPresents.isEmpty());
        return false;
    }

    private void removePresent(Present present) {
        final int n = present.getGrid().length, m = present.getGrid()[0].length;
        for (int r = present.getHorizontalOffset(); r < present.getHorizontalOffset() + n; r++) {
            for (int c = present.getVerticalOffset(); c < present.getVerticalOffset() + m; c++) {
                this.grid[r][c] = EMPTY;
            }
        }
    }

    private boolean attemptToPlacePresentAtPosition(Present present, int positionRow, int positionColumn) {
        final int n = present.getGrid().length, m = present.getGrid()[0].length;
        if (positionRow + n >= this.width || positionColumn + m >= this.height) {
            return false;
        }
        // optimization: can only store the section that's being updated and then patch it in if successful
        // instead of copying and replacing the entire thing
        char[][] newGrid = new char[this.width][this.height];
        for (int r = 0; r < this.width; r++) {
            System.arraycopy(this.grid[r], 0, newGrid[r], 0, this.height);
        }
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (present.getGrid()[r][c] != PRESENT) {
                    continue;
                }
                if (this.grid[positionRow + r][positionColumn + c] == PRESENT) {
                    return false;
                }
                newGrid[positionRow + r][positionColumn + c] = present.getGrid()[r][c];
            }
        }
        this.grid = newGrid;
        return true;
    }

    /**
     * Rotates current present 0, 90, 180, and 270 degrees, as well as flips it over and rotates it at the same
     * degree increments
     *
     * @param present original present
     * @return up to 8 unique present configurations that are equivalent to the provided present
     */
    // optimization: de-dupe list before returning. maybe serialize to a string and compare that way?
    private List<Present> generateRotationsAndFlips(Present present) {
        List<Present> rotations = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rotations.add(rotate(present, i));
        }
        Present flippedPresent = flip(present);
        for (int i = 0; i < 4; i++) {
            rotations.add(rotate(flippedPresent, i));
        }
        return rotations;
    }

    private Present flip(Present present) {
        final int n = present.getGrid().length, m = present.getGrid()[0].length;
        char[][] flippedPresentArr = new char[n][m];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                flippedPresentArr[r][m - 1 - c] = present.getGrid()[r][c];
            }
        }
        Present flippedPresent = new Present(present);
        flippedPresent.setFlipped(!flippedPresent.isFlipped());
        flippedPresent.setGrid(flippedPresentArr);
        return flippedPresent;
    }

    /**
     * Rotates the given input by the given number of times, 90 degrees for each
     *
     * @param present original present
     * @param times          90-degree increments by which to rotate the original present
     * @return a new present, rotated a number of times according to the given input
     */
    private Present rotate(Present present, int times) {
        if (times == 0) {
            return present;
        }
        times %= 4;
        final int n = present.getGrid().length, m = present.getGrid()[0].length;
        char[][] rotatedPresentArr = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotatedPresentArr[j][n - 1 - i] = present.getGrid()[i][j];
            }
        }
        Present rotatedPresent = new Present(present);
        rotatedPresent.setRotationCount(rotatedPresent.getRotationCount() + 1);
        rotatedPresent.setGrid(rotatedPresentArr);
        return rotate(rotatedPresent, times - 1);
    }

    private char[][] to2dCharArray(List<String> stringList) {
        return stringList.stream().map(String::toCharArray).toArray(char[][]::new);
    }
}
