import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        System.out.print("How many mines do you want on the field? ");
        Scanner scanner = new Scanner(System.in);
        int minesCount = scanner.nextInt();

        MineField mineField = new MineField(minesCount);
        mineField.playGame();
    }
}