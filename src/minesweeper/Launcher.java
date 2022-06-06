package minesweeper;


public class Launcher {
    public static void main(String[] args) {
        System.out.print("How many mines do you want on the field? ");
        MineField mineField = new MineField(Inserts.setMinesCount());
        mineField.playGame();
    }
}