package ee.wihler.backend.entities;

public class Seat {

    private int row;
    private int column;
    private boolean occupied;

    public Seat(int row, int column, boolean occupied) {
        this.row = row;
        this.column = column;
        this.occupied = occupied;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
