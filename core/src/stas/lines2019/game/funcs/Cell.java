package stas.lines2019.game.funcs;
class Cell {
    int i;
    int j;

    boolean directionLeft;
    boolean directionRight;
    boolean directionUp;
    boolean directionDown;

    //
    boolean hasBall;
    boolean validCell;

    public void setCrossed(boolean crossed) {
        isCrossed = crossed;
    }

    boolean isCrossed = false;

    public boolean isHasDirections() {
        boolean hasDirections;
        hasDirections = directionDown || directionUp || directionLeft || directionRight;
        return hasDirections;
    }

    public boolean isHasBall() {
        return hasBall;
    }

    public boolean isValidCell() {
        return validCell;
    }

    Cell(int i, int j, boolean directionLeft, boolean directionRight,
         boolean directionDown, boolean directionUp, boolean hasBall) {
        this.i = i;
        this.j = j;
        this.directionLeft = directionLeft;
        this.directionRight = directionRight;
        this.directionDown = directionDown;
        this.directionUp = directionUp;
        this.hasBall = hasBall;

        validCell = !hasBall && isHasDirections();

    }

}
