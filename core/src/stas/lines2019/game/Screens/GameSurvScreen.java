package stas.lines2019.game.Screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import stas.lines2019.game.GameFieldSurv;
import stas.lines2019.game.LinesGame;
import stas.lines2019.game.util.Constants;

/**
 * Created by seeyo on 13.02.2019.
 */

public class GameSurvScreen extends GameScreen {

    public static final String RULES_DIALOG_STR = "You have 5 minutes in the beginning" +
            " them into the lines of the same color." +" To avoid filling up the board you "+
            "should gather the balls into horizontal, vertical or diagonal lines of 5 or more "+
            "balls. ";

    private int surviveTime  = 5*60;
    private int initTimeBank = 1*40;
    private int ballTimeAdd  = 2;
    private int treshholdPenalty = 20;
    public int clearGameFieldNumber = 25;

    public GameSurvScreen(LinesGame lineGame, SpriteBatch batch) {

        super(lineGame, batch);
    }

    public GameSurvScreen(LinesGame lineGame, SpriteBatch batch, int difficultType ) {
        super(lineGame, batch);
        switch(difficultType) {
            case Constants.DIFFICULT_EASY:
                surviveTime  = 5*60;
                initTimeBank = 1*40;
                ballTimeAdd  = 2;
                treshholdPenalty = 20;
                break;
            case Constants.DIFFICULT_NORMAL:

                break;
            case Constants.DIFFICULT_HARD:

                break;
            case Constants.DIFFICULT_NIGHTMARE:

                break;
            case Constants.DIFFICULT_ENDLESS:

                break;
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(initTimeBank - gameField.getGameTime() < 0) {
            gameOverDialog(false,false);
        }

        if (surviveTime < 0) {
            gameOverDialog(false,true);
        }

    }

    @Override
    public void setGameField() {
        gameField = new GameFieldSurv(this);
    }

    @Override
    public void setScoreLable(int score) {
        super.setScoreLable( (int) (surviveTime - gameField.getGameTime()));
    }

    @Override
    public void setTimeLable(int time) {
        int timeNew = (int) (initTimeBank - gameField.getGameTime());
        super.setTimeLable(timeNew);
    }

    @Override
    public String scoreFormat(int score) {
        return super.timeFormat(score);
    }

    public void lineIsSet (int lenght ) {
        initTimeBank += ballTimeAdd*lenght;
    }

    public void endFreeSpace() {
        initTimeBank -= treshholdPenalty;
    }

    @Override
    public String setRulesLable() {
        return RULES_DIALOG_STR;
    }
}
