package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import stas.lines2019.game.Entities.GameFieldClass;
import stas.lines2019.game.Entities.GameFieldsFactory;
import stas.lines2019.game.LinesGame;
import stas.lines2019.game.gameFields.GameFieldConstrPuzzle;
import stas.lines2019.game.gameFields.GameFieldExpans;
import stas.lines2019.game.util.Constants;
import static stas.lines2019.game.util.ConstantsPuzzle.*;

/**
 * Created by seeyo on 01.04.2019.
 */

public class PuzzleConstrScreen extends GameScreen {

    private List<int[][]> gameFieldsBase;


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
        gameFieldsBase = new ArrayList<int[][]>();

        saveCurrentfield();
        loadFieldsBase();
        saveCurrentfield();
        loadFieldsBase();
        boolean def = true;
        if (def) {
            gameField = new GameFieldConstrPuzzle(this, 9);
        } else {
            gameField = new GameFieldConstrPuzzle(this, initGameField.dimX);
        }

    }

    public void saveCurrentfield() {
//        GameFieldClass gameFieldClass = new GameFieldClass("field03",5,5,"1,1,1,1,1,1,2,3");
        Preferences prefs = Gdx.app.getPreferences(CONSTR_PUZZL);
        Hashtable<String, String> hashTable = new Hashtable<String, String>();
        Json json = new Json();
        int[][] field  =  new  int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                field[i][j] = i+j;
            }
        }
        gameFieldsBase.add(field);
        hashTable.put(CONSTR_SAVE_BASE, json.toJson(gameFieldsBase)); //here you are serializing the array
        prefs.put(hashTable);
        prefs.flush();
        Gdx.app.log(TAG,"json save");
    }


    public void loadCurrentField() {
        Json json = new Json();
        GameFieldsFactory f = json.fromJson(GameFieldsFactory.class,
                Gdx.files.internal("gameFieldsSave/gameFieldsSave.json"));
        initGameField = f.GameFieldClass.get(0);
        Gdx.app.log(TAG,"json load");
    }

    public void  loadFieldsBase() {
        gameFieldsBase = new ArrayList<int[][]>();
        Preferences prefs = Gdx.app.getPreferences(CONSTR_PUZZL);
        Json json = new Json();
        StringBuilder defaultAcieveStr = new StringBuilder();
        String serializedInts = prefs.getString(CONSTR_SAVE_BASE,"");
        gameFieldsBase = json.fromJson(List.class, serializedInts);
        Gdx.app.log(TAG,"base load");

    }


}
