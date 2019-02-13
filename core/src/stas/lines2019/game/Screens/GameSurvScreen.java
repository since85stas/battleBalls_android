package stas.lines2019.game.Screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import stas.lines2019.game.GameFieldSurv;
import stas.lines2019.game.LinesGame;

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

    public GameSurvScreen(LinesGame lineGame, SpriteBatch batch) {

        super(lineGame, batch);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(initTimeBank - gameField.getGameTime() < 0) {
            gameOverDialog();
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

    @Override
    public String setRulesLable() {
        return RULES_DIALOG_STR;
    }
}
