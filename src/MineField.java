import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MineField {

    private static final char EMPTY_CELL = '.';

    private static final char PLAYER_MARK = '*';

    private static final int FIELD_SIZE = 9;

    private final int MINES_ON_THE_FIELD;

    private final List<Cell> field;

    public MineField(int minesCount) {
        this.field = new ArrayList<>();
        this.MINES_ON_THE_FIELD = minesCount;
        createField();
        setMinesOnTheField(minesCount);
        setNumbersAroundMines();
    }

    private void createField() {
        for (int y = 1; y <= FIELD_SIZE; y++) {
            for (int x = 1; x <= FIELD_SIZE; x++) {
                field.add(new Cell(x, y));
            }
        }
    }

    private void setMinesOnTheField(int minesTotalCount) {
        Random random = new Random();
        boolean haveMines = minesTotalCount > 0;

        while (haveMines) {
            for (Cell cell : field) {
                if (minesTotalCount == 0) {
                    haveMines = false;
                } else {
                    if (!cell.isMine() && random.nextInt(10) == 1) {
                        cell.setMine(true);
                        minesTotalCount--;
                    }
                }
            }
        }
    }

    private void setNumbersAroundMines() {
        for (int y = 1; y <= FIELD_SIZE; y++) {
            for (int x = 1; x <= FIELD_SIZE; x++) {
                int cellIndex = field.indexOf(new Cell(x, y));
                if (field.get(cellIndex).isMine()) {
                    setNumber(x - 1, y - 1);
                    setNumber(x, y - 1);
                    setNumber(x + 1, y - 1);
                    setNumber(x - 1, y);
                    setNumber(x + 1, y);
                    setNumber(x - 1, y + 1);
                    setNumber(x, y + 1);
                    setNumber(x + 1, y + 1);
                }
            }
        }
    }

    private void setNumber(int x, int y) {
        try{
            int cellIndex = field.indexOf(new Cell(x, y));
            if (!field.get(cellIndex).isMine()) {
                int minesAround = field.get(cellIndex).getMinesAround();
                field.get(cellIndex).setMinesAround(++minesAround);
            }
        }catch (IndexOutOfBoundsException ignored){
        }
    }

    public void setOrDeletePlayerMark() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Set/delete mines marks (x and y coordinates): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int cellIndex = field.indexOf(new Cell(x, y));
            if (field.get(cellIndex).getMinesAround() > 0) {
                System.out.println("There is a number here!");
            } else if (field.get(cellIndex).isPlayerMark()) {
                field.get(cellIndex).setPlayerMark(false);
                break;
            } else {
                field.get(cellIndex).setPlayerMark(true);
                break;
            }
        }
    }

    public void printField() {

        //Print first line with numbers
        System.out.print(" |");
        for (int i = 1; i <= FIELD_SIZE; i++) {
            System.out.print(i);
        }

        //print field
        System.out.print("|\n");
        System.out.println("-|---------|");
        int linesNumber = 1;
        for (int y = 1; y <= FIELD_SIZE; y++) {
            System.out.print(linesNumber++ + "|");
            for (int x = 1; x <= FIELD_SIZE; x++) {

                //Find cell with coordinates x and y and check condition
                int cellIndex = field.indexOf(new Cell(x, y));
                if (field.get(cellIndex).isMine() && !field.get(cellIndex).isPlayerMark()) {
                    System.out.print(EMPTY_CELL);
                } else if (field.get(cellIndex).isPlayerMark()) {
                    System.out.print(PLAYER_MARK);
                } else if (field.get(cellIndex).getMinesAround() > 0) {
                    System.out.print(field.get(cellIndex).getMinesAround());
                } else {
                    System.out.print(EMPTY_CELL);
                }
            }
            System.out.println("|");
        }

        //Print last line
        System.out.println("-|---------|");
    }

    public void playGame() {
        while (!win()) {
            printField();
            setOrDeletePlayerMark();
        }
        System.out.println("Congratulations! You found all the mines!");
    }

    public boolean win() {
        List<Cell> markedMines = field.stream().
                filter(cell -> cell.isMine() && cell.isPlayerMark()).collect(Collectors.toList());

        List<Cell> markedCells = field.stream().
                filter(Cell::isPlayerMark).collect(Collectors.toList());

        return markedMines.size() == MINES_ON_THE_FIELD && markedCells.size() == MINES_ON_THE_FIELD;
    }
}
