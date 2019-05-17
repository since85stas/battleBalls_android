package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import stas.lines2019.game.Entities.GameFieldClass;
import stas.lines2019.game.Entities.GameFieldsFactory;
import stas.lines2019.game.LinesGame;
import stas.lines2019.game.Widgets.SelectLevelDialog;
import stas.lines2019.game.gameFields.GameFieldConstrPuzzle;

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
//        isRenderGamefield = false;
    }

    @Override
    public void setGameField() {
        isRenderGamefield = true;
//        SelectLevelDialog dialog = new SelectLevelDialog("select",mySkin,3,this);
//        dialog.show(stage);
        setGameFieldConstr();
    }

    public void setGameFieldConstr() {
        gameFieldsBase = new ArrayList<int[][]>();
//        deleteFieldsBase();
        loadFieldsBase();
//        loadCurrentField();
        boolean def = true;
        if (def) {
            gameField = new GameFieldConstrPuzzle(this, 8);
        } else {
            gameField = new GameFieldConstrPuzzle(this, initGameField.dimX);
        }
        isRenderGamefield = true;
    }

    @Override
    public void saveCurrentField() {
        gameFieldsBase = new ArrayList<int[][]>();
        Preferences prefs = Gdx.app.getPreferences(CONSTR_PUZZL);
        Hashtable<String, String> hashTable = new Hashtable<String, String>();
        Json json = new Json();
        gameFieldsBase.add(gameField.getCurrentFieldInt());
        hashTable.put(CONSTR_SAVE_BASE, json.toJson(gameFieldsBase)); //here you are serializing the array
        prefs.put(hashTable);
        prefs.flush();
        Gdx.app.log(TAG,"json save");
    }

    public void loadCurrentField() {
        Json json = new Json();
        if (gameFieldsBase.size() != 0 && gameFieldsBase != null) {
            gameField.setGameField(gameFieldsBase.get(0));
        }
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

    public void deleteFieldsBase() {
        gameFieldsBase = new ArrayList<int[][]>();
        Preferences prefs = Gdx.app.getPreferences(CONSTR_PUZZL);
        Json json = new Json();
        Hashtable<String, String> hashTable = new Hashtable<String, String>();
        hashTable.put(CONSTR_SAVE_BASE, json.toJson(gameFieldsBase)); //here you are serializing the array
        prefs.put(hashTable);
        prefs.flush();
//        gameFieldsBase = json.fromJson(List.class, serializedInts);
        Gdx.app.log(TAG,"base load");
    }




}
