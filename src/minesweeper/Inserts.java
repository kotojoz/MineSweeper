package minesweeper;

import static minesweeper.Properties.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Inserts {
    private static final String WRONG_MINE_COUNT = "Mines count must be > "
            + MIN_MINES_COUNT.getProperty() + " and < "
            + MAX_MINES_COUNT.getProperty();

    private static final String ONLY_DIGITS = "You can use only digits";

    private static final String UNKNOWN_COMMAND = "Unknown command";

    private static final String WRONG_COORDINATES = "Coordinates must be > 0 and <= "
            + FIELD_SIZE.getProperty();

    private static final Scanner scanner = new Scanner(System.in);

    public static int setMinesCount() {
        int minesCount;
        while (true) {
            try {
                minesCount = scanner.nextInt();
                if (minesCount > MIN_MINES_COUNT.getProperty() && minesCount < MAX_MINES_COUNT.getProperty()) {
                    scanner.nextLine();
                    return minesCount;
                } else {
                    throw new RuntimeException(WRONG_MINE_COUNT);
                }
            } catch (InputMismatchException e) {
                System.out.println(ONLY_DIGITS);
                scanner.nextLine();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String[] enterCommand() {
        while (true) {
            System.out.print("Set/unset mine marks or claim a cell as free: ");
            String[] command = scanner.nextLine().split(" ");
            try {
                if (command.length != 3) {
                    throw new RuntimeException(UNKNOWN_COMMAND);
                }
                int x = Integer.parseInt(command[0]);
                int y = Integer.parseInt(command[1]);
                if (x > FIELD_SIZE.getProperty() || x < 0 ||
                        y > FIELD_SIZE.getProperty() || y < 0) {
                    throw new RuntimeException(WRONG_COORDINATES);
                }
                String comm = command[2];
                if (comm.equals("mine") || comm.equals("free")) {
                    return command;
                } else {
                    throw new RuntimeException(UNKNOWN_COMMAND);
                }
            } catch (NumberFormatException e) {
                System.out.println(UNKNOWN_COMMAND);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
