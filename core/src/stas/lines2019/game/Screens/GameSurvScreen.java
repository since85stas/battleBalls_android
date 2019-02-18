package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import stas.lines2019.game.GameFieldSurv;
import stas.lines2019.game.LinesGame;
import stas.lines2019.game.util.Constants;

/**
 * Created by seeyo on 13.02.2019.
 */

public class GameSurvScreen extends GameScreen {

    public static final String RULES_DIALOG_STR = "You need to survive 5 min. You have only 10 seconds on turn." +
            " Each line add bonus time to your time bank." +" When the board is getting full you are not "+
            "loosing, but getting some time penalty.  This mode may be difficult." +
            "It's better to play classical mode first. Clock is ticking. Good luck."
            ;

    private int surviveTime  = 5*60;
    private int initTimeBank = 1*40;
    private float ballTimeAdd  = 2;
    private int treshholdPenalty = 20;
    public int clearGameFieldNumber = 25;
    private int gameDifficult;
    private float turnTimeLimit;
    private float turnTime;

    boolean dialogIsOpen;

    public GameSurvScreen(LinesGame lineGame, SpriteBatch batch) {

        super(lineGame, batch);
    }

    public GameSurvScreen(LinesGame lineGame, SpriteBatch batch, int difficultType ) {
        super(lineGame, batch);
        switch(difficultType) {
            case Constants.DIFFICULT_EASY:
                surviveTime  = 6*60;
                initTimeBank = 1*45;
                ballTimeAdd  = 2.3f;
                treshholdPenalty = 30;
                clearGameFieldNumber = 35;
                gameDifficult = Constants.DIFFICULT_EASY;
                turnTimeLimit  = 10;
                break;
            case Constants.DIFFICULT_NORMAL:
                surviveTime  = 7*60;
                initTimeBank = 1*55;
                ballTimeAdd  = 2;
                treshholdPenalty = 20;
                clearGameFieldNumber = 25;
                gameDifficult = Constants.DIFFICULT_NORMAL;
                turnTimeLimit  = 10;
                break;
            case Constants.DIFFICULT_HARD:
                surviveTime  = 7*60;
                initTimeBank = 1*45;
                ballTimeAdd  = 1.3f;
                treshholdPenalty = 20;
                clearGameFieldNumber = 22;
                gameDifficult = Constants.DIFFICULT_HARD;
                turnTimeLimit  = 10;
                break;
            case Constants.DIFFICULT_NIGHTMARE:
                surviveTime  = 5*60;
                initTimeBank = 1*25;
                ballTimeAdd  = 1;
                treshholdPenalty = 20;
                clearGameFieldNumber = 21;
                gameDifficult = Constants.DIFFICULT_NIGHTMARE;
                turnTimeLimit  = 10;
                break;
            case Constants.DIFFICULT_ENDLESS:

                break;
        }

        dialogIsOpen = false;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
//        turnTimeLimit += d
        if(initTimeBank - gameField.getGameTime() < 0 && !dialogIsOpen) {
            dialogIsOpen = true;
            gameOverDialog(false,false);

        }

        if ( (surviveTime- gameField.getGameTime()) < 0 && !dialogIsOpen)  {
            boolean isWin = true;
            dialogIsOpen = true;
            if(isWin) {
                lineGame.saveSurvPref(gameDifficult);
            }
            gameOverDialog(false,isWin);

        }

        if (gameField.turnTime > turnTimeLimit) {
            gameField.aiTurn();
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
    public Label setLeftTitleLable() {
        return new Label("survive", mySkin, "small");
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
        gameField.setInputProccActive(false);
        initTimeBank -= treshholdPenalty;
    }

    @Override
    public String setRulesLable() {
        return RULES_DIALOG_STR;
    }
}
