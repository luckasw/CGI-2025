package ee.wihler.backend.entities;

public class Seat {

    private int row;
    private int column;
    private boolean occupied;
    private boolean suggested;

    public Seat(int row, int column, boolean occupied, boolean suggested) {
        this.row = row;
        this.column = column;
        this.occupied = occupied;
        this.suggested = suggested;
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

    public boolean isSuggested() {
        return suggested;
    }

    public void setSuggested(boolean suggested) {
        this.suggested = suggested;
    }
}
