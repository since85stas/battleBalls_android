package stas.lines2019.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import stas.lines2019.game.Screens.GameScreen;
import stas.lines2019.game.Screens.GameSurvScreen;

/**
 * Created by seeyo on 13.02.2019.
 */

public class GameFieldSurv extends GameField {

    private int CLEAR_BALLS_NUMBER = 25;

    public GameSurvScreen mGameSurvScreen;

    public GameFieldSurv(GameScreen gameScreen) {
        super(gameScreen);
    }

    public GameFieldSurv(GameSurvScreen gameSurvScreen) {

        super(gameSurvScreen);
        mGameSurvScreen = gameSurvScreen;
    }

    @Override
    public void lineIsSet() {
        mGameSurvScreen.lineIsSet(getLineLong());
    }

    @Override
    public void noFreeSpace() {
        mGameSurvScreen.endFreeSpace();
        clearGameField();
    }

    private void clearGameField() {
        int num = mGameSurvScreen.clearGameFieldNumber;

        for (int i = 0; i < num; i++) {
            Vector2[] freeSquares = checkSquares(true);
            int random = MathUtils.random(0, freeSquares.length - 1);
            returnSquareInitState(freeSquares[random],true);
            //            squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
//                    .setNextTurnBall(true);
        }

    }
}
