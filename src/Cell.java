public class Cell {

    private final int x;

    private final int y;

    private int minesAround;

    private boolean Mine;

    private boolean playerMark;

    private boolean exploredCell;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

    public boolean isMine() {
        return Mine;
    }

    public void setMine(boolean mine) {
        Mine = mine;
    }

    public boolean isPlayerMark() {
        return playerMark;
    }

    public void setPlayerMark(boolean playerMark) {
        this.playerMark = playerMark;
    }

    public boolean isExploredCell() {
        return exploredCell;
    }

    public void setExploredCell(boolean exploredCell) {
        this.exploredCell = exploredCell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (x != cell.x) return false;
        return y == cell.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
