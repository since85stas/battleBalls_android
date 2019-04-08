package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import stas.lines2019.game.Entities.GameFieldClass;
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

    public GameFieldClass initGameField;

    @Override
    public void setPuzzleConstrPlayed() {
        isPuzzleConstrPlayed = true;
    }

    @Override
    public void setGameField() {
        loadCurrentField();

        gameField = new GameFieldConstrPuzzle(this, initGameField.dimX);

        saveCurrentfield();
    }

    public void saveCurrentfield() {
        GameFieldClass gameFieldClass = new GameFieldClass("field03",5,5,"1,1,1,1,1,1,2,3");

        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
//        json.addClassTag("Mission", GameFieldClass.class);
        FileHandle missionFile = Gdx.files.local("gameFieldsSave/gameFieldsSave.json");
        missionFile.writeString(json.prettyPrint(gameFieldClass), false);
        Gdx.app.log(TAG,"json save");
    }

    public void loadCurrentField() {
        Json json = new Json();
        GameFieldsFactory f = json.fromJson(GameFieldsFactory.class,
                Gdx.files.internal("gameFieldsSave/gameFieldsSave.json"));
        initGameField = f.GameFieldClass.get(0);
        Gdx.app.log(TAG,"json load");
    }


}
