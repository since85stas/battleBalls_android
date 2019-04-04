package stas.lines2019.game.Screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.gameFields.GameFieldConstrPuzzle;
import stas.lines2019.game.gameFields.GameFieldExpans;

/**
 * Created by seeyo on 01.04.2019.
 */

public class PuzzleConstrScreen extends GameScreen {
    public PuzzleConstrScreen(LinesGame lineGame, SpriteBatch batch) {
        super(lineGame, batch);
    }

    @Override
    public void setPuzzleConstrPlayed() {
        isPuzzleConstrPlayed = true;
    }

    @Override
    public void setGameField() {
        gameField = new GameFieldConstrPuzzle(this);
    }

    public void saveCurrentfield() {

    }
}
