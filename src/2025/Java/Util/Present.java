package Util;

public class Present {
    private int presentNumber;
    private int rotationCount;
    private boolean flipped;
    private char[][] grid;
    private int horizontalOffset;
    private int verticalOffset;
    private int occupiedCellCount;
    private int vacantCellCount;

    public Present(int presentNumber, int rotationCount, boolean flipped, char[][] grid, int horizontalOffset, int verticalOffset) {
        this.presentNumber = presentNumber;
        this.rotationCount = rotationCount;
        this.flipped = flipped;
        this.grid = grid;
        for (char[] row : this.grid) {
            for (char c : row) {
                if (c == '.') {
                    this.vacantCellCount++;
                } else if (c == '#') {
                    this.occupiedCellCount++;
                }
            }
        }
        this.horizontalOffset = horizontalOffset;
        this.verticalOffset = verticalOffset;
    }

    public Present(Present present) {
        Present newPresent = new Present(present.presentNumber, present.rotationCount, present.flipped, present.grid,  present.horizontalOffset, present.verticalOffset);
        this.presentNumber = newPresent.presentNumber;
        this.rotationCount = newPresent.rotationCount;
        this.flipped = newPresent.flipped;
        this.grid = newPresent.grid;
        this.horizontalOffset = newPresent.horizontalOffset;
        this.verticalOffset = newPresent.verticalOffset;
    }

    public int getOccupiedCellCount() {
        return occupiedCellCount;
    }

    public void setOccupiedCellCount(int occupiedCellCount) {
        this.occupiedCellCount = occupiedCellCount;
    }

    public int getVacantCellCount() {
        return vacantCellCount;
    }

    public void setVacantCellCount(int vacantCellCount) {
        this.vacantCellCount = vacantCellCount;
    }

    public int getHorizontalOffset() {
        return horizontalOffset;
    }

    public void setHorizontalOffset(int horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
    }

    public int getVerticalOffset() {
        return verticalOffset;
    }

    public void setVerticalOffset(int verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void setGrid(char[][] grid) {
        this.grid = grid;
    }

    public int getPresentNumber() {
        return presentNumber;
    }

    public void setPresentNumber(int presentNumber) {
        this.presentNumber = presentNumber;
    }

    public int getRotationCount() {
        return rotationCount;
    }

    public void setRotationCount(int rotationCount) {
        this.rotationCount = rotationCount;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }
}
