package minesweeper;

import static minesweeper.Properties.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Inserts {
    private static final String WRONG_MINE_COUNT = "Mines count must be > "
            + MIN_MINES_COUNT.getProperty() + " and < "
            + MAX_MINES_COUNT.getProperty();

    private static final String ONLY_DIGITS = "You can use only digits";

    public static int setMinesCount() {
        Scanner scanner = new Scanner(System.in);
        int minesCount;
        while (true) {
            try {
                minesCount = scanner.nextInt();
                if (minesCount > MIN_MINES_COUNT.getProperty() && minesCount < MAX_MINES_COUNT.getProperty()) {
                    break;
                }
                System.out.println(WRONG_MINE_COUNT);
            } catch (InputMismatchException e) {
                System.out.println(ONLY_DIGITS);
                scanner.nextLine();
            }
        }
        return minesCount;
    }

    public static int setCoordinates() {
        Scanner scanner = new Scanner(System.in);
        int number;
        while (true) {
            try {
                number = Integer.parseInt(scanner.next());
                if (number <= FIELD_SIZE.getProperty() && number > 0) {
                    break;
                }else {
                    System.out.println("Coordinates must be > 0 and < 9");
                }
            } catch (NumberFormatException e) {
                System.out.println(ONLY_DIGITS);
                scanner.nextLine();
            }
        }
        return number;
    }

    public static String setCommand() {
        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            try {
                command = scanner.next().toLowerCase();
                if (command.equals("mine") || command.equals("free")) {
                    break;
                }
                System.out.println("Coordinates must be > 0 and < 9");
            } catch (InputMismatchException e) {
                System.out.println("Only letters");
                scanner.nextLine();
            }
        }
        return command;
    }
}
