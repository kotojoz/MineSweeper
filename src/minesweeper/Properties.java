package minesweeper;

public enum Properties {
    FIELD_SIZE(9),
    MIN_MINES_COUNT(1),
    MAX_MINES_COUNT(60);

    final int property;

    Properties(int property) {
        this.property = property;
    }

    public int getProperty() {
        return property;
    }
}
