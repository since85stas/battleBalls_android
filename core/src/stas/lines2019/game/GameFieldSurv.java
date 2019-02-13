package stas.lines2019.game;

import stas.lines2019.game.Screens.GameScreen;
import stas.lines2019.game.Screens.GameSurvScreen;

/**
 * Created by seeyo on 13.02.2019.
 */

public class GameFieldSurv extends GameField {

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
}
