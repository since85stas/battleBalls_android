package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import stas.lines2019.game.Widgets.HelpDialog;
import stas.lines2019.game.gameFields.GameField;
import stas.lines2019.game.LinesGame;
import stas.lines2019.game.MenuBall;
import stas.lines2019.game.Widgets.ExitDialog;
import stas.lines2019.game.Widgets.RulesDialog;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

import static stas.lines2019.game.util.Constants.HUD_ITEM_HOR_SIZE;
import static stas.lines2019.game.util.Constants.HUD_OFFSET;

public class GameScreen implements Screen {

    //
    private static final String TAG = GameScreen.class.getName();
    //
    public LinesGame lineGame;
    public GameField gameField;
    public float lableItemHeight;

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;
    Label timeLable;
    Label scoreLable;
    public Label energyLable;

    //
    private float accumulator = 0;
    private float gametime;
    public Stage stage;
    private SpriteBatch batch;
    Skin mySkin;
    public int width;
    public int height;

    public boolean isRenderGamefield;

    public boolean isExpansionPlayed;

    DelayedRemovalArray<MenuBall> menuBalls;

    public GameScreen(LinesGame lineGame, SpriteBatch batch) {
        this.lineGame = lineGame;
        this.batch = batch;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
    }

    @Override
    public void show() {
        stage = new Stage();

        // Initialize the HUD viewport
        hudViewport = new ScreenViewport();
        mySkin = Assets.instance.skinAssets.skin;
        scoreLable = new Label("0",mySkin , "game");
        timeLable = new Label("0",mySkin , "game");

        VerticalGroup timeLableGroup = new VerticalGroup();
        timeLableGroup.setPosition(width-2*Constants.HUD_OFFSET*width-Constants.HUD_ITEM_HOR_SIZE*width,
                height - lableItemHeight);
        timeLableGroup.setSize(Constants.HUD_ITEM_HOR_SIZE * width, lableItemHeight);
        Label titleLable = new Label(Assets.instance.bundle.get("timeLable"), mySkin, "small");
//        timeLable = new Label(Integer.toString(digit), mySkin, "game");
        float size1 = titleLable.getHeight();
        float size2 = timeLable.getHeight();
        lableItemHeight = size1 + size2;
        timeLableGroup.addActor(titleLable);
        timeLableGroup.addActor(timeLable);

        VerticalGroup scoreLableGroup = new VerticalGroup();
        scoreLableGroup.setPosition(2 * Constants.HUD_OFFSET * width,
                height - lableItemHeight);
        scoreLableGroup.setSize(Constants.HUD_ITEM_HOR_SIZE * width, lableItemHeight);
        titleLable = setLeftTitleLable();
        scoreLableGroup.addActor(titleLable);
        scoreLableGroup.addActor(scoreLable);

        stage.addActor(scoreLableGroup);
        stage.addActor(timeLableGroup);

        setExpansionPlay();
        if(isExpansionPlayed) {
            drawEnergy();
        }
        setGameField();
        isRenderGamefield = true;

    }

    public void setExpansionPlay() {
        isExpansionPlayed = false;
    }

    private void drawEnergy() {
        energyLable = new Label("0",mySkin , "game");

        VerticalGroup timeLableGroup = new VerticalGroup();
        timeLableGroup.setPosition(width/2 - width*0.13f,
                height - lableItemHeight);
        timeLableGroup.setSize(HUD_ITEM_HOR_SIZE * width, lableItemHeight);
        Label titleLable = new Label("energy", mySkin, "small-energy");
//        timeLable = new Label(Integer.toString(digit), mySkin, "game");
        float size1 = titleLable.getHeight();
        float size2 = energyLable.getHeight();
        lableItemHeight = size1 + size2;
        timeLableGroup.addActor(titleLable);
        timeLableGroup.addActor(energyLable);
        stage.addActor(timeLableGroup);
        Gdx.app.log(TAG,"energ");
    }

    public void setGameField() {
        gameField = new GameField(this);
    }

    public Label setLeftTitleLable() {
        return new Label(Assets.instance.bundle.get("scoreLable"), mySkin, "small");
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.3f, 0.47f, 0.6f, 0.8f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Constants.TIME_STEP) {
            update(Constants.TIME_STEP);
            accumulator -= Constants.TIME_STEP;
        }

        hudViewport.apply();

        batch.begin();

        gameField.update(delta);
        gameField.render(batch, delta);

        if (isExpansionPlayed) {
            setEnergyLable(gameField.energy);
        }
        // Draw the number of player deaths in the top left

        if (frameTime % 0.5 == 0) {
            float fps = 1 / delta;
            Gdx.app.log(TAG, "fps =" + fps);
        }

        int time = (int)gameField.getGameTime();
        setTimeLable(time);

        int score =(int) gameField.getGameScore();
        setScoreLable(score);


//        addRulesButton();
        batch.end();

        stage.draw();
        stage.act();
    }

    public void setTimeLable(int time) {

        timeLable.setText(timeFormat(time));
    }

    public String timeFormat(int time) {
        String timeString = "";
        if ( time > 0 && time < 60) {
            timeString = Integer.toString(time);
        } else if (time > 60 && time < 3600) {
            int min = (int) (time / 60);
            int sec = time % 60;
            if (sec > 9) {
                timeString = "0" + Integer.toString(min) + ":" + Integer.toString(sec);
            } else {
                timeString = "0" + Integer.toString(min) + ":0" + Integer.toString(sec);
            }
        } else if (time < 0) {
            timeString = "0";
        }
        return timeString;
    }

    public void setScoreLable(int score) {

        scoreLable.setText(scoreFormat(score));
    }

    public void setEnergyLable(int enwrgy) {
        energyLable.setText(Integer.toString(enwrgy));
    }

    public String scoreFormat(int score) {
        String scoreString = "";
        if (  0 < score && score < 10) {
            scoreString = "000" + Integer.toString(score);
        } else if (score >= 10 && score < 100) {
            scoreString = "00" + Integer.toString(score);
        } else if (score >= 100 && score < 1000) {
            scoreString = "0" + Integer.toString(score);
        } else if (score >= 1000 && score < 10000) {
            scoreString = "" + Integer.toString(score);
        } else if (score >= 10000 && score < 100000) {
            scoreString = "10k" + Integer.toString(score - 10000);
        }  else if (score < 0 ) {
            scoreString = "0";
        }
        return scoreString;
    }


    public void showExitDialog() {
        ExitDialog dialog = new ExitDialog("", Assets.instance.skinAssets.skin,lineGame);
        dialog.setTransform(true);
        dialog.getBackground();

        dialog.text(Assets.instance.bundle.get("exitDi"),
                Assets.instance.skinAssets.skin.get("dialog", Label.LabelStyle.class))
                .align(Align.center);
        dialog.button(Assets.instance.bundle.get("exitYes"),
                          true,
                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
        );
        dialog.button(Assets.instance.bundle.get("exitNo"),
                false,
                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
        ); //sends "false" as the result

        Gdx.input.setInputProcessor(stage);
        dialog.show(stage);
    }

    public void gameOverDialog(boolean classicMode, boolean isWin ) {
        ExitDialog gameOverDialog = new ExitDialog("", Assets.instance.skinAssets.skin,lineGame);
        gameOverDialog.setTransform(true);
        gameOverDialog.getBackground();

//        Table table = new Table();
        Label gameLable = new Label(Assets.instance.bundle.get("gameOver"),
                Assets.instance.skinAssets.skin,
                "dialog");
        gameLable.setAlignment(Align.center);
//        table.add(gameLable);
        gameOverDialog.getContentTable().add(gameLable).padTop(40);
        gameOverDialog.getContentTable().row();

        Table table = new Table();
        if (classicMode) {
            Label resultLable = new Label(Assets.instance.bundle.get("yourScore") + gameField.getGameScore(),
                    Assets.instance.skinAssets.skin,
                    "dialog");
            resultLable.setAlignment(Align.center);
            table.add(resultLable);
            table.row();
            Label highLable = new Label(Assets.instance.bundle.get("highScore") + gameField.getHighScores(),
                    Assets.instance.skinAssets.skin,
                    "dialog");
            highLable.setAlignment(Align.center);
            table.add(highLable);

        } else {

            String result = new String();
            if (isWin) {
                result = Assets.instance.bundle.get("youWin");
                result += "\n" + Assets.instance.bundle.get("newDiff");
            } else  {
                result = Assets.instance.bundle.get("youLoose");
            }
            Label resultLable = new Label(result ,
                    Assets.instance.skinAssets.skin,
                    "dialog");
            resultLable.setAlignment(Align.center);
            table.add(resultLable);
            table.row();
        }
        gameOverDialog.getContentTable().add(table);
        gameOverDialog.button(Assets.instance.bundle.get("Ok"),
                true,
                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class));

        Gdx.input.setInputProcessor(stage);

        gameOverDialog.show(stage);
    }

    public void rulesDialog() {
        RulesDialog rulesDialog = new RulesDialog("",
                Assets.instance.skinAssets.skin,
                lineGame);
        rulesDialog.setTransform(true);
        rulesDialog.getBackground();
        Gdx.input.setInputProcessor(stage);
//        Table table = new Table();
        Label gameLable = new Label(setRulesLable(),
                Assets.instance.skinAssets.skin,
                "dialog");
        gameLable.setWrap(true);
        gameLable.setAlignment(Align.left);
//        table.add(gameLable);
        rulesDialog.getContentTable().add(gameLable).padTop(40);
        rulesDialog.getContentTable().row();

        rulesDialog.button("Ok",
                false,
                Assets.instance.skinAssets.skin.get("small",
                        TextButton.TextButtonStyle.class)
        );
        rulesDialog.show(stage);
    }

    public void helpDialog() {
        HelpDialog helpDialog = new HelpDialog ("",
                Assets.instance.skinAssets.skin,
                lineGame);

        Gdx.input.setInputProcessor(stage);
        helpDialog.show(stage);
        helpDialog.setFillParent(true);
        Table table = helpDialog.getContentTable();
        float w = stage.getWidth();
        float h = stage.getHeight();

        Gdx.app.log(TAG,"help dialog");
    }

    public String setRulesLable() {
        return Assets.instance.bundle.get("rulesText");
    }

        public void update(float dt) {

    }

    @Override
    public void dispose() {
        batch.dispose();
//		gameField.dispose();
        //img.dispose();
    }

    @Override
    public void hide() {
//        lineGame.isScreenRendering = false;
//        batch.dispose();
//        gameField.dispose();
    }

    @Override
    public void pause() {
        gameField.savePrefTime();
        lineGame.isScreenRendering = false;
    }

    @Override
    public void resume() {
        gameField.initStartTime();
        lineGame.isScreenRendering = true;
    }

    @Override
    public void resize(int width, int height) {

        // Update HUD viewport
        hudViewport.update(width, height, true);

        // Set font scale to min(width, height) / reference screen size
//        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
    }

    public void generateBalls() {
        menuBalls = new DelayedRemovalArray();
        for (int i = 0; i < Constants.GAME_BALLS_INIT_NUMBERS; i++) {
            final MenuBall ball = MenuBall.generateOneBall();
            ball.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Assets.instance.soundsBase.bubbleSound.play();
                    for (int i = 0; i < menuBalls.size; i++) {
                        if (menuBalls.get(i).equals(ball)) {
                            menuBalls.removeIndex(i);
                            menuBalls.add(MenuBall.generateOneBall());
                        }
                    }
                    return true;
                }
            });
            menuBalls.add(ball);
            stage.addActor(ball);

        }
    }
}
