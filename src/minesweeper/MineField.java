package minesweeper;

import static minesweeper.FieldSigns.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MineField {

    private static final int FIELD_SIZE = 9;

    private boolean steppedOnMine;

    private boolean noExploredCell;

    private List<Cell> firstEmptyCells;

    private final int MINES_ON_THE_FIELD;

    private final List<Cell> field;

    public MineField(int minesCount) {
        this.field = new ArrayList<>();
        this.MINES_ON_THE_FIELD = minesCount;
        this.noExploredCell = true;
        createField();
    }

    private void createField() {
        for (int y = 1; y <= FIELD_SIZE; y++) {
            for (int x = 1; x <= FIELD_SIZE; x++) {
                field.add(new Cell(x, y));
            }
        }
    }

    public void playGame() {
        while (noExploredCell) {
            printField();
            setOrDeletePlayerMark();
        }

        while (!win()) {
            if (!steppedOnMine) {
                printField();
                setOrDeletePlayerMark();
            } else {
                break;
            }
        }

        if (steppedOnMine) {
            System.out.println("You stepped on a mine and failed!");
            printField();
        } else {
            System.out.println("Congratulations! You found all the mines!");
            printField();
        }
    }

    public void setOrDeletePlayerMark() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Set/delete mines marks (x and y coordinates): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            String command = scanner.next();
            int cellIndex = field.indexOf(new Cell(x, y));
            if (command.equals("mine")) {
                field.get(cellIndex).setPlayerMark(!field.get(cellIndex).isPlayerMark());
                break;
            } else if (command.equals("free")) {
                explore(x, y);
                break;
            }
        }
    }

    private void explore(int x, int y) {
        int cellIndex = field.indexOf(new Cell(x, y));
        if (noExploredCell) {
            firstMove(field.get(cellIndex));
        } else {
            if (field.get(cellIndex).isMine()) {
                field.get(cellIndex).setExploredCell(true);
                steppedOnMine = true;
            } else {
                field.get(cellIndex).setExploredCell(true);
                if (field.get(cellIndex).getMinesAround() == 0) {
                    autoExploringAroundThisCell(x, y);
                }
            }
        }
    }

    private void firstMove(Cell firstCell) {
        firstEmptyCells = new ArrayList<>();
        noExploredCell = false;
        firstCell.setExploredCell(true);
        for (int x = firstCell.getX() - 1; x <= firstCell.getX() + 1; x++) {
            for (int y = firstCell.getY() - 1; y <= firstCell.getY() + 1; y++) {
                try {
                    int index = field.indexOf(new Cell(x, y));
                    firstEmptyCells.add(field.get(index));
                } catch (IndexOutOfBoundsException ignored) {
                }
            }

        }
        setMinesOnTheField(MINES_ON_THE_FIELD);
        setNumbersAroundMines();
        autoExploringAroundThisCell(firstCell.getX(), firstCell.getY());
    }

    private void setMinesOnTheField(int minesTotalCount) {
        Random random = new Random();
        boolean haveMines = minesTotalCount > 0;

        while (haveMines) {
            for (Cell cell : field) {
                if (minesTotalCount == 0) {
                    haveMines = false;
                } else {
                    if (!cell.isMine() && random.nextInt(10) == 1 && !firstEmptyCells.contains(cell)) {
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
                    for (int i = y - 1; i <= y + 1; i++) {
                        for (int j = x - 1; j <= x + 1; j++) {
                            setNumber(j, i);
                        }
                    }
                }
            }
        }
    }

    private void setNumber(int x, int y) {
        try {
            int cellIndex = field.indexOf(new Cell(x, y));
            if (!field.get(cellIndex).isMine()) {
                int minesAround = field.get(cellIndex).getMinesAround();
                field.get(cellIndex).setMinesAround(++minesAround);
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
    }


    private void autoExploringAroundThisCell(int x, int y) {
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (cellIsEmpty(j, i)) {
                    autoExploringAroundThisCell(j, i);
                }
            }
        }
    }

    private boolean cellIsEmpty(int x, int y) {
        try {
            int cellIndex = field.indexOf(new Cell(x, y));
            if (field.get(cellIndex).isExploredCell()) {
                return false;
            } else {
                if (field.get(cellIndex).getMinesAround() > 0) {
                    field.get(cellIndex).setExploredCell(true);
                    return false;
                }
                field.get(cellIndex).setExploredCell(true);
                field.get(cellIndex).setPlayerMark(false);
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean win() {
        List<Cell> markedMines = field.stream().
                filter(cell -> cell.isMine() && cell.isPlayerMark()).collect(Collectors.toList());

        List<Cell> markedCells = field.stream().
                filter(Cell::isPlayerMark).collect(Collectors.toList());

        List<Cell> exploredCells = field.stream().filter(Cell::isExploredCell).collect(Collectors.toList());

        return markedMines.size() == MINES_ON_THE_FIELD && markedCells.size() == MINES_ON_THE_FIELD
                || field.size() - MINES_ON_THE_FIELD == exploredCells.size();
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
                if (!field.get(cellIndex).isExploredCell() && !field.get(cellIndex).isPlayerMark()) {
                    System.out.print(EMPTY_CELL);
                } else if (field.get(cellIndex).isExploredCell() && field.get(cellIndex).getMinesAround() > 0) {
                    System.out.print(field.get(cellIndex).getMinesAround());
                } else if (field.get(cellIndex).isPlayerMark()) {
                    System.out.print(PLAYER_MARK);
                } else if (field.get(cellIndex).isExploredCell() && field.get(cellIndex).isMine()) {
                    System.out.print(MINE);
                } else {
                    System.out.print(EXPLORED_CELL);
                }
            }
            System.out.println("|");
        }

        //Print last line
        System.out.println("-|---------|");
    }

}
