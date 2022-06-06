package minesweeper;

public enum FieldSigns {
    MINE('X'),
    EMPTY_CELL('.'),
    EXPLORED_CELL('/'),
    PLAYER_MARK('*');

    final char sign;

    FieldSigns(char sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "" + sign;
    }
}

