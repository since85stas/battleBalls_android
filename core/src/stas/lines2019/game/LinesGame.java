package stas.lines2019.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import stas.lines2019.game.Screens.AchieveScreen;
import stas.lines2019.game.Screens.GameScreen;
import stas.lines2019.game.Screens.MainMenuScreen;
import stas.lines2019.game.results.AchivementsList;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;
import stas.lines2019.game.util.ConstantsAchiveEng;

import java.util.Hashtable;

public class LinesGame extends Game {
    private SpriteBatch batch; //область отрисовки
    private GameScreen gameScreen;
    private Viewport viewport ;
    public AchivementsList achivementsList;
    public boolean findSaveGame;

    @Override
    public void create () {

        batch = new SpriteBatch();
//        viewport   = new FitViewport(1280,720);

        // создаем текстуры
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        setMainMenuScreen();
    }

    public void setMainMenuScreen() {

        // создаем достижения
        achivementsList = new AchivementsList(this);
        achivementsList.generateAchivemnets();
//        dropAcievmComplete();
        loadAchieve();

        setScreen(new MainMenuScreen(this));
    }

    public void setGameScreen() {
        gameScreen = new GameScreen(this,batch);
        setScreen(gameScreen);
    }

    public void setAchieveScreen() {
        AchieveScreen screen = new AchieveScreen(this,batch);
        setScreen(screen);
    }

    @Override
    public void render () {
        float dt = Gdx.graphics.getDeltaTime();
        //update(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getScreen().render(dt);
    }

    @Override
    public void dispose () {
        batch.dispose();
        getScreen().dispose();
    }

    public void saveAchieve() {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREF_ACHIEV);

        Hashtable<String, String> hashTable = new Hashtable<String, String>();
        Json json = new Json();
        hashTable.put(Constants.PREF_ACHIEV_MASSIVE, json.toJson(achivementsList.getAchievCompArray()) ); //here you are serializing the array
        prefs.put(hashTable);

        prefs.flush();
    }

    public void loadAchieve() {
        Preferences gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        findSaveGame = gamePref.getBoolean(Constants.PREF_GAME_IS_PLAY,false);
        Preferences prefs = Gdx.app.getPreferences(Constants.PREF_ACHIEV);

        Json json = new Json();
        StringBuilder defaultAcieveStr = new StringBuilder();
                ;
//        for (int i = 0; i < ConstantsAchiveEng.NUM_ACHIVEMENTS; i++) {
//            if (i != ConstantsAchiveEng.NUM_ACHIVEMENTS -1) {
//                defaultAcieveStr.append("0,");
//            } else {
//                defaultAcieveStr.append("0");
//            }
//        }

        String defaultStr = defaultAcieveStr.toString();
        String serializedInts = prefs.getString(Constants.PREF_ACHIEV_MASSIVE);
        int[] deserializedInts = json.fromJson(int[].class, serializedInts);
        //you need to pass the class type - be aware of it!
        if ( deserializedInts != null) {
            for (int i = 0; i < achivementsList.getAchievCompArray().length; i++) {
                if (deserializedInts[i] == 1) {
                    achivementsList.getAchivements()[i].setComplete(1);
                }
            }
        }
    }

    public void dropAcievmComplete() {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREF_ACHIEV);

        Hashtable<String, String> hashTable = new Hashtable<String, String>();
        Json json = new Json();
        int[] array = new int[achivementsList.getAchievCompArray().length];
        for (int i = 0; i < ConstantsAchiveEng.NUM_ACHIVEMENTS; i++) {
            array[i] = 0;
        }
        hashTable.put(Constants.PREF_ACHIEV_MASSIVE, json.toJson(array) ); //here you are serializing the array
        prefs.put(hashTable);

        prefs.flush();
    }

    public Viewport getViewport() {
        return viewport;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }
}
