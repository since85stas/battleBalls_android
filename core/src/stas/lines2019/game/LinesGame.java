package stas.lines2019.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.pay.Information;
import com.badlogic.gdx.pay.Offer;
import com.badlogic.gdx.pay.OfferType;
import com.badlogic.gdx.pay.PurchaseManager;
import com.badlogic.gdx.pay.PurchaseManagerConfig;
import com.badlogic.gdx.pay.PurchaseObserver;
import com.badlogic.gdx.pay.Transaction;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.Viewport;

import stas.lines2019.game.Screens.*;
import stas.lines2019.game.results.AchivementsList;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;
import static stas.lines2019.game.util.Constants.*;
import stas.lines2019.game.util.ConstantsAchiveEng;

import java.util.Hashtable;

public class LinesGame extends Game {
    LinesGame app;
    private SpriteBatch batch; //область отрисовки
    private GameScreen gameScreen;
    private Viewport viewport ;
    public AchivementsList achivementsList;
    public boolean findSaveGame;

    public boolean friendGameIsBought;
    public boolean expansGameIsBought;
    public boolean hadrModeisBought;
    public boolean nightmareModeisBought;

    public boolean isScreenRendering;

    public int numberOfMainMenuOpens;
    public int numberOfStars;

    public boolean survLevelIsComp[];
    private boolean gameIsRated = false;
    private static final int OPENS_NUMB = 30;

    public PurchaseManager purchaseManager;

    public static final String TAG = LinesGame.class.toString();


    @Override
    public void create () {
        this.app = this;
        batch = new SpriteBatch();
        isScreenRendering = true;
        setScreen(new LoadingScreen(this));
    }

    public void prepareMainRes() {
        //        // создаем текстуры
        AssetManager am = new AssetManager();

        Assets.instance.init(am);

        survLevelIsComp = new boolean[Constants.NUM_DIFFICULTIES];



        Gdx.input.setCatchBackKey(true);

        PurchaseManagerConfig pmc = new PurchaseManagerConfig();
        pmc.addOffer(new Offer().setType(OfferType.ENTITLEMENT).setIdentifier(Constants.FRIEND_VERSION));

//        purchaseManager.install(new MyPurchaseObserver() , pmc, true);

//        updateFromManager();
    }

    public void updateFromManager() {
        Information skuInfo = app.purchaseManager.getInformation(Constants.FRIEND_VERSION );

        if (skuInfo == null || skuInfo.equals(Information.UNAVAILABLE)) {
//            setDisabled(true);
            Gdx.app.log(TAG,"not avaible");

        } else {
            Gdx.app.log(TAG," avaible");
        }
    }

    public void setMainMenuScreen() {

        achivementsList = new AchivementsList(this);
        achivementsList.generateAchivemnets();
        isScreenRendering = true;

        loadAchieve();

        numberOfMainMenuOpens++;

        if (numberOfMainMenuOpens%OPENS_NUMB == 0) {
            openRateWindow();
        }

        setScreen(new MainMenuScreen(this));
    }

    private void openRateWindow() {

    }

    public void setGameScreen() {
        gameScreen = new GameScreen(this,batch);
        setScreen(gameScreen);
    }

    public void setGameSurvivScreen(int diffType) {
        gameScreen = new GameSurvScreen(this,batch,diffType);
        setScreen(gameScreen);
    }

    public void setGameScreenExpans() {
        gameScreen = new GameScreenExpans(this,batch);
        setScreen(gameScreen);
    }

    public void setPuzzleConstrScreen() {
        gameScreen = new PuzzleConstrScreen(this,batch);
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
        if (isScreenRendering) {
            getScreen().render(dt);
        }
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

        saveStarsNumber();
    }

    public void saveStarsNumber() {
        Preferences gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gamePref.putInteger(Constants.STARS_NUMBER,numberOfStars);
        gamePref.flush();
    }

    public void dropKeys() {
        Preferences gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gamePref.putBoolean(Constants.PREF_GAME_IS_PLAY,false);
        gamePref.putBoolean(Constants.SURV_GAME_IS_BOUGHT,false);
        gamePref.putBoolean(Constants.EXPANS_GAME_IS_BOUGHT,false);
        gamePref.putBoolean(DIFFICULT_HARD_BOUGHT,false);
        gamePref.putBoolean(DIFFICULT_NIGHTMARE_BOUGHT,false);
        numberOfMainMenuOpens = gamePref.getInteger(Constants.GAME_OPENS,0);
        gamePref.putInteger(Constants.STARS_NUMBER,15);
        gamePref.flush();
    }

    public void loadAchieve() {
//        dropKeys();
        Preferences gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        findSaveGame = gamePref.getBoolean(Constants.PREF_GAME_IS_PLAY,false);
        friendGameIsBought = gamePref.getBoolean(Constants.SURV_GAME_IS_BOUGHT,false);
        expansGameIsBought = gamePref.getBoolean(Constants.EXPANS_GAME_IS_BOUGHT,false);
        hadrModeisBought = gamePref.getBoolean(DIFFICULT_HARD_BOUGHT,false);
        nightmareModeisBought = gamePref.getBoolean(DIFFICULT_NIGHTMARE_BOUGHT,false);
        numberOfMainMenuOpens = gamePref.getInteger(Constants.GAME_OPENS,0);
        numberOfStars         = gamePref.getInteger(Constants.STARS_NUMBER,0);
        Preferences prefs = Gdx.app.getPreferences(Constants.PREF_ACHIEV);

        Json json = new Json();
        StringBuilder defaultAcieveStr = new StringBuilder();

        String defaultStr = defaultAcieveStr.toString();
        String serializedInts = prefs.getString(Constants.PREF_ACHIEV_MASSIVE);
        int[] deserializedInts = json.fromJson(int[].class, serializedInts);
        if ( deserializedInts != null) {
            for (int i = 0; i < deserializedInts.length; i++) {
                if (deserializedInts[i] == 1) {
                    achivementsList.getAchivements()[i].initCompleteAchievs(1);
                }
            }
        }

        Preferences survPref = Gdx.app.getPreferences(Constants.PREF_SURVIVE);
        try {
            survLevelIsComp[0] = survPref.getBoolean(Constants.PREF_DIFFICULT_EASY,false);
            survLevelIsComp[1] = survPref.getBoolean(Constants.PREF_DIFFICULT_NORMAL,false);
            survLevelIsComp[2] = survPref.getBoolean(Constants.PREF_DIFFICULT_HARD,false);
            survLevelIsComp[3] = survPref.getBoolean(Constants.PREF_DIFFICULT_NIGHTMARE,false);
            survLevelIsComp[4] = survPref.getBoolean(Constants.PREF_DIFFICULT_ENDLESS,false);
        } catch (Exception e) {
            Gdx.app.log("lineGame","catch e");
        }
    }

    public void setSurvGameIsBought()  {
        Preferences gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gamePref.putBoolean(Constants.SURV_GAME_IS_BOUGHT,true);
        gamePref.flush();
        friendGameIsBought = true;
    }

    public void setExpansGameIsBought() {
        Preferences gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gamePref.putBoolean(Constants.EXPANS_GAME_IS_BOUGHT,true);
        gamePref.flush();
        numberOfStars -= EXPANSION_STAR_COST;
        expansGameIsBought = true;
        saveStarsNumber();
    }

    public void setHardGameIsBought() {
        Preferences gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gamePref.putBoolean(DIFFICULT_HARD_BOUGHT,true);
        gamePref.flush();
        numberOfStars -= SURV_MODE_COST;
        hadrModeisBought = true;
        saveStarsNumber();
    }

    public void setNightGameIsBought() {
        Preferences gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        gamePref.putBoolean(DIFFICULT_NIGHTMARE_BOUGHT,true);
        gamePref.flush();
        numberOfStars -= SURV_MODE_COST;
        nightmareModeisBought = true;
        saveStarsNumber();
    }

    public void saveSurvPref(int diffType) {
        Preferences survPref = Gdx.app.getPreferences(Constants.PREF_SURVIVE);
        switch(diffType) {
            case(Constants.DIFFICULT_EASY):
                survPref.putBoolean(Constants.PREF_DIFFICULT_EASY,true);
                break;
            case(Constants.DIFFICULT_NORMAL):
                survPref.putBoolean(Constants.PREF_DIFFICULT_NORMAL,true);
                break;
            case(Constants.DIFFICULT_HARD):
                survPref.putBoolean(Constants.PREF_DIFFICULT_HARD,true);
                break;
            case(Constants.DIFFICULT_NIGHTMARE):
                survPref.putBoolean(Constants.PREF_DIFFICULT_NIGHTMARE,true);
                break;
            case(Constants.DIFFICULT_ENDLESS):
                survPref.putBoolean(Constants.PREF_DIFFICULT_ENDLESS,true);
                break;

        }
        survPref.flush();

        loadAchieve();
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

    private class MyPurchaseObserver implements PurchaseObserver {


        @Override
        public void handleInstall() {
            Gdx.app.log("IAP", "Installed");

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
//                updateGuiWhenPurchaseManInstalled(null);
                }
            });
        }

        @Override
        public void handleInstallError(final Throwable e) {
            Gdx.app.error("IAP", "Error when trying to install PurchaseManager", e);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
//                updateGuiWhenPurchaseManInstalled(e.getMessage());
                }
            });
        }

        @Override
        public void handleRestore(final Transaction[] transactions) {
            if (transactions != null && transactions.length > 0)
                for (Transaction t : transactions) {
                    handlePurchase(t, true);
                }
            else if (false)
                showErrorOnMainThread("Nothing to restore");
        }

        @Override
        public void handleRestoreError(Throwable e) {
            if (false)
                showErrorOnMainThread("Error restoring purchases: " + e.getMessage());
        }

        @Override
        public void handlePurchase(final Transaction transaction) {
            handlePurchase(transaction, false);
        }

        protected void handlePurchase(final Transaction transaction, final boolean fromRestore) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    if (transaction.isPurchased()) {
                        if (transaction.getIdentifier().equals(Constants.FRIEND_VERSION)) {
                            setSurvGameIsBought();
                        }
                    }
                }
            });
        }

        @Override
        public void handlePurchaseError(Throwable e) {
            showErrorOnMainThread("Error on buying:\n" + e.getMessage());
        }

        @Override
        public void handlePurchaseCanceled() {

        }

        private void showErrorOnMainThread(final String message) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    // show a dialog here...
                }
            });
        }
    }
}
