package ee.wihler.backend.entities;

public class Seat {

    private int row;
    private int column;
    private boolean occupied;
    private boolean suggested;
    private boolean selected;

    public Seat(
        int row,
        int column,
        boolean occupied,
        boolean suggested,
        boolean selected
    ) {
        this.row = row;
        this.column = column;
        this.occupied = occupied;
        this.suggested = suggested;
        this.selected = selected;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSuggested(boolean suggested) {
        this.suggested = suggested;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
