package stas.lines2019.game.funcs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import stas.lines2019.game.balls.SquareItem;
import stas.lines2019.game.util.Constants;

import java.util.ArrayList;

public class FindBallPath {
    private static final String TAG = FindBallPath.class.getName();

    private Vector2 from;
    private Vector2 to;

    // в каком направлении мы в последний раз сходили
    private int lastMovement;

    private SquareItem[][] squares;
    private boolean[][] field;
    private Cell[][] cells;

    private static final int VERTICAL = 22;
    private static final int HORIZONTAL = 33;

    // путь до требуемой точки
    private ArrayList<Vector2> path;

    // params
    private int sizeX;
    private int sizeY;

    // iteration params
    private int currentCellI;
    private int currentCellJ;

    //TODO: убрать SquareItem из конструктора
    public FindBallPath(SquareItem[][] squares, Vector2 from, Vector2 to) {
        this.squares = squares;
        this.from = from;
        this.to = to;
        sizeX = squares.length;
        sizeY = squares.length;
        field = new boolean[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (squares[i][j].isHasBall()) {
                    field[i][j] = false;
                } else {
                    field[i][j] = true;
                }
            }
        }

        this.cells = generateEmptyCells();
        Gdx.app.log(TAG, "field" + field);
    }

    public boolean findPath() {
        boolean result = false;
        for (int i = 0; i < 4; i++) {
            if (!result) {
                currentCellI = (int) from.x;
                currentCellJ = (int) from.y;
                result = checkCells();
            }
        }
        return result;
    }

    private boolean checkCells() {
        boolean hasPath = false;
        path = new ArrayList<Vector2>();
        path.add(new Vector2(currentCellI, currentCellJ));

        do  {
            hasPath = ballMovement();
            // проверка на предыдущую ячейку с открытой границей
            int size = path.size();
            if (hasPath) {
                break;
            }

            for (int iter = size - 1; iter > -1; iter--) {
                if (iter == 0) {
                    return hasPath;
                }
                int i = (int) path.get(iter).x;
                int j = (int) path.get(iter).y;
                if (checkLeftCell(i, j) || checkRightCell(i, j) || checkDownCell(i, j) || checkUpCell(i, j)) {
                    currentCellI = (int) path.get(iter).x;
                    currentCellJ = (int) path.get(iter).y;
                    break;
                } else {
                    path.remove(iter);
                }
            }
        } while (!hasPath);
        return hasPath;
    }


    private boolean ballMovement() {
        boolean hasPath = false;

        while (checkLeftCell(currentCellI, currentCellJ) || checkRightCell(currentCellI, currentCellJ) ||
                checkDownCell(currentCellI, currentCellJ) || checkUpCell(currentCellI, currentCellJ)) {

            // находим приоритетное направление
            int prior = findPrioriativeDirection();
            switch (prior) {
                case Constants.LEFT:
                    // проходим по всем направлениям
                    if (checkLeftCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.LEFT));
                    } else if (checkDownCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.DOWN));
                    } else if (checkUpCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.UP));
                    } else if (checkRightCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.RIGHT));
                    }
                    break;
                case Constants.RIGHT:
                    // проходим по всем направлениям
                    if (checkRightCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.RIGHT));
                    } else if (checkDownCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.DOWN));
                    } else if (checkUpCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.UP));
                    } else if (checkLeftCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.LEFT));
                    }
                    break;
                case Constants.DOWN:
                    // проходим по всем направлениям
                    if (checkDownCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.DOWN));
                    } else if (checkRightCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.RIGHT));
                    } else if (checkLeftCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.LEFT));
                    } else if (checkUpCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.UP));
                    }
                    break;
                case Constants.UP:
                    // проходим по всем направлениям
                    if (checkUpCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.UP));
                    } else if (checkRightCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.RIGHT));
                    } else if (checkLeftCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.LEFT));
                    } else if (checkDownCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.DOWN));
                    }
            }

            // проверка на попадание в Нужную точку
            if (currentCellI == to.x && currentCellJ == to.y) {
                Gdx.app.log(TAG, "Get to final point pathSize " + path.size());
                hasPath = true;
                break;
            }
        }
        return hasPath;
    }

    private int findPrioriativeDirection() {
        int direction = -1;
        int directionCase ;
        int dx = (int) to.x - currentCellI;
        int dy = (int) to.y - currentCellJ;

        if (Math.abs(dx) <= Math.abs(dy)) {
            directionCase = HORIZONTAL;
        } else {
            directionCase = VERTICAL;
        }

        if (dx == 0) {
            directionCase = VERTICAL;
        }

        if (dy == 0) {
            directionCase = HORIZONTAL;
        }

        switch (directionCase) {
            case HORIZONTAL:
                if (dx > 0) {
                    direction = Constants.RIGHT;
                } else if (dx < 0) {
                    direction = Constants.LEFT;
                } else if (dx == 0) {

                }
                break;
            case VERTICAL:
                if (dy > 0) {
                    direction = Constants.UP;
                } else if (dy < 0) {
                    direction = Constants.DOWN;
                }
                break;
        }

//        if (isOpposite(direction,lastMovement)) {
//            direction = getOppositeTo(direction);
//        }
        return direction;
    }

    private boolean isOpposite(int direction1, int dirextion2) {
        if (direction1 == Constants.LEFT && dirextion2 == Constants.RIGHT) return true;
        if (direction1 == Constants.RIGHT && dirextion2 == Constants.LEFT) return true;
        if (direction1 == Constants.UP && dirextion2 == Constants.DOWN) return true;
        if (direction1 == Constants.DOWN && dirextion2 == Constants.UP) return true;
        return false;
    }

    private int getOppositeTo(int direction) {
        if (direction == Constants.LEFT) return Constants.RIGHT;
        if (direction == Constants.RIGHT) return Constants.LEFT;
        if (direction == Constants.UP) return Constants.DOWN;
        if (direction == Constants.DOWN) return Constants.UP;
        return -77;
    }


    private boolean checkLeftCell(int i, int j) {
        boolean result = false;
        if (i != 0) {
            Cell cell = cells[i][j];
            Cell cellChecked = cells[i - 1][j];
            result = cells[i][j].directionLeft && !cells[i - 1][j].isCrossed;
        }
        Gdx.app.log(TAG, "checkLeftCell");
        return result;
    }

    private boolean checkRightCell(int i, int j) {
        boolean result = false;
        if (i != sizeX - 1) {
            Cell cell = cells[i][j];
            Cell cellChecked = cells[i + 1][j];
            result = cells[i][j].directionRight && !cells[i + 1][j].isCrossed;
        }
        Gdx.app.log(TAG, "checkRightCell");
        return result;
    }

    private boolean checkDownCell(int i, int j) {
        boolean result = false;
        if (j != 0) {
            Cell cell = cells[i][j];
            Cell cellChecked = cells[i][j - 1];
            result = cells[i][j].directionDown && !cells[i][j - 1].isCrossed;
        }
        Gdx.app.log(TAG, "checkDownCell");
        return result;
    }

    private boolean checkUpCell(int i, int j) {
        boolean result = false;
        if (j != sizeY - 1) {
            Cell cell = cells[i][j];
            Cell cellChecked = cells[i][j + 1];
            result = cells[i][j].directionUp && !cells[i][j + 1].isCrossed;
        }
        Gdx.app.log(TAG, "checkUpCell");
        return result;
    }


    private Vector2 goToCellStep(int direction) {
        int previousCellI = -1;
        int previousCellJ = -1;

        switch (direction) {
            case Constants.LEFT:
                previousCellI = currentCellI;
                previousCellJ = currentCellJ;
                currentCellI--;
                cells[previousCellI][previousCellJ].directionLeft = false;
                cells[previousCellI][previousCellJ].setCrossed(true);
                cells[currentCellI][currentCellJ].directionRight = false;
                break;
            case Constants.RIGHT:
                previousCellI = currentCellI;
                previousCellJ = currentCellJ;
                currentCellI++;
                cells[previousCellI][previousCellJ].directionRight = false;
                cells[previousCellI][previousCellJ].setCrossed(true);
                cells[currentCellI][currentCellJ].directionLeft = false;
                break;
            case Constants.DOWN:
                previousCellI = currentCellI;
                previousCellJ = currentCellJ;
                currentCellJ--;
                cells[previousCellI][previousCellJ].directionDown = false;
                cells[previousCellI][previousCellJ].setCrossed(true);
                cells[currentCellI][currentCellJ].directionUp = false;
                break;
            case Constants.UP:
                previousCellI = currentCellI;
                previousCellJ = currentCellJ;
                currentCellJ++;
                cells[previousCellI][previousCellJ].directionUp = false;
                cells[previousCellI][previousCellJ].setCrossed(true);
                cells[currentCellI][currentCellJ].directionDown = false;
                break;
        }
        return (new Vector2(currentCellI, currentCellJ));
    }

    private Cell[][] generateEmptyCells() {
        Cell[][] cells = new Cell[sizeX][sizeY];

        boolean directionLeft;
        boolean directionRight;
        boolean directionUp;
        boolean directionDown;

        boolean cellHasBall = false;

        showCells(field);

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                directionLeft = false;
                directionRight = false;
                directionUp = false;
                directionDown = false;
                //if (field[i][j] ) {

                // проверяем соседние клетки по X
                if (i != 0) {
                    if (field[i - 1][j]) {
                        directionLeft = true;
                    }
                }
                if (i != sizeX - 1) {
                    if (field[i + 1][j]) {
                        directionRight = true;
                    }
                }

                // проверяем соседние клетки по Y
                if (j != 0) {
                    if (field[i][j - 1]) {
                        directionDown = true;
                    }
                }
                if (j != sizeY - 1) {
                    if (field[i][j + 1]) {
                        directionUp = true;
                    }
                }
                if (field[i][j]) {
                    cellHasBall = false;
                } else {
                    cellHasBall = true;
                }

                cells[i][j] = new Cell(i, j, directionLeft, directionRight, directionDown, directionUp, cellHasBall);
            }
        }

        showCells(cells);
        Gdx.app.log(TAG, "test");
        return cells;
    }

    private void showCells(Cell[][] cells) {
        for (int j = sizeY - 1; j > -1; j--) {
            Gdx.app.log (TAG, "j= " + j + " " +
                    cells[0][j].isValidCell() + " " +
                    cells[1][j].isValidCell() + " " +
                    cells[2][j].isValidCell() + " " +
                    cells[3][j].isValidCell() + " " +
                    cells[4][j].isValidCell() + " " +
                    cells[5][j].isValidCell() + " " +
                    cells[6][j].isValidCell() + " " +
                    cells[7][j].isValidCell() + " " +
                    cells[8][j].isValidCell()
            );
        }
        Gdx.app.log(TAG, "\n");
    }

    private void showCells(boolean[][] cells) {
        for (int j = sizeY - 1; j > -1; j--) {
            Gdx.app.log(TAG, "j= " + j + " " +
                    cells[0][j] + " " +
                    cells[1][j] + " " +
                    cells[2][j] + " " +
                    cells[3][j] + " " +
                    cells[4][j] + " " +
                    cells[5][j] + " " +
                    cells[6][j] + " " +
                    cells[7][j] + " " +
                    cells[8][j]
            );
        }
        Gdx.app.log(TAG, "\n");
    }

    public Vector2[] getPath() {
        if (path.size() == 1) {
            Gdx.app.log(TAG, "wrong path");
        }
        Vector2[] pathArray = path.toArray(new Vector2[path.size()]);
        return pathArray;
    }
}
