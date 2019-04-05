package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;

import stas.lines2019.game.Entities.GameFieldsFactory;
import stas.lines2019.game.LinesGame;
import stas.lines2019.game.gameFields.GameFieldConstrPuzzle;
import stas.lines2019.game.gameFields.GameFieldExpans;

/**
 * Created by seeyo on 01.04.2019.
 */



public class PuzzleConstrScreen extends GameScreen {

    public static final String TAG = PuzzleConstrScreen.class.getName();

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
        loadCurrentField();
    }

    public void saveCurrentfield() {

    }

    public void loadCurrentField() {
        Json json = new Json();
        GameFieldsFactory f = json.fromJson(GameFieldsFactory.class,
                Gdx.files.internal("gameFieldsSave/gameFieldsSave.json"));
        Gdx.app.log(TAG,"json load");
    }
}
