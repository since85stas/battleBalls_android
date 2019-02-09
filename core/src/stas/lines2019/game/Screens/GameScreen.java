package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import stas.lines2019.game.GameField;
import stas.lines2019.game.LinesGame;
import stas.lines2019.game.Widgets.ExitDialog;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

import java.util.Date;

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
    //
    private float accumulator = 0;
    private float gametime;
    private Stage stage;
    private SpriteBatch batch;
    private int width;
    private int height;

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

        VerticalGroup scoreLable = scoreHudLable("score", 0);
        scoreLable.setPosition(2 * Constants.HUD_OFFSET * width,
                height - lableItemHeight);
        scoreLable.setSize(Constants.HUD_ITEM_HOR_SIZE * width, lableItemHeight);

        VerticalGroup timeLable = timeHudLable("time", 100);
        timeLable.setPosition(width - 2 * Constants.HUD_OFFSET * width - Constants.HUD_ITEM_HOR_SIZE * width,
                height - lableItemHeight);
        timeLable.setSize(Constants.HUD_ITEM_HOR_SIZE * width, lableItemHeight);

        stage.addActor(scoreLable);
        stage.addActor(timeLable);

        gameField = new GameField(this);
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

        // Set the SpriteBatch's projection matrix
//        batch.setProjectionMatrix(hudViewport.getCamera().combined);

//        batch.disableBlending();
        batch.begin();

        //gameField.update(delta);
        gameField.render(batch, delta);

        // Draw the number of player deaths in the top left
        gametime = gameField.getGameTime();

        if (frameTime % 0.5 == 0) {
            float fps = 1 / delta;
            Gdx.app.log(TAG, "fps =" + fps);
        }

        int time = (int) gameField.getGameTime();
        String timeString = "";
        if (time < 60) {
            timeString = Integer.toString(time);
        } else if (time > 60 && time < 3600) {
            int min = (int) (time / 60);
            int sec = time % 60;
            if (sec > 9) {
                timeString = "0" + Integer.toString(min) + ":" + Integer.toString(sec);
            } else {
                timeString = "0" + Integer.toString(min) + ":0" + Integer.toString(sec);
            }
        }
        timeLable.setText(timeString);
        int score = gameField.getGameScore();
        String scoreString = "";

        if (score < 10) {
            scoreString = "000" + Integer.toString(score);
        } else if (score >= 10 && score < 100) {
            scoreString = "00" + Integer.toString(score);
        } else if (score >= 100 && score < 1000) {
            scoreString = "0" + Integer.toString(score);
        } else if (score >= 1000 && score < 10000) {
            scoreString = "" + Integer.toString(score);
        } else if (score >= 10000 && score < 100000) {
            scoreString = "10k" + Integer.toString(score - 10000);
        }
        scoreLable.setText(scoreString);

//        addRulesButton();
        batch.end();

        stage.draw();
        stage.act();
    }

    public void showExitDialog() {
        ExitDialog dialog = new ExitDialog("", Assets.instance.skinAssets.skin,lineGame);
        dialog.setTransform(true);
        dialog.getBackground();

        dialog.text("exit to main menu",
                Assets.instance.skinAssets.skin.get("dialog", Label.LabelStyle.class))
                .align(Align.center);
        dialog.button("Yes",
                          true,
                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
        );
        dialog.button("No",
                false,
                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
        ); //sends "false" as the result

        Gdx.input.setInputProcessor(stage);
        dialog.show(stage);
    }

    public void gameOverDialog() {
        ExitDialog gameOverDialog = new ExitDialog("", Assets.instance.skinAssets.skin,lineGame);
        gameOverDialog.setTransform(true);
        gameOverDialog.getBackground();

//        Table table = new Table();
        Label gameLable = new Label("Game Over",
                Assets.instance.skinAssets.skin,
                "dialog");
        gameLable.setAlignment(Align.center);
//        table.add(gameLable);
        gameOverDialog.getContentTable().add(gameLable).padTop(40);
        gameOverDialog.getContentTable().row();

        Table table = new Table();
        Label resultLable = new Label("Game Score " + gameField.getGameScore(),
                Assets.instance.skinAssets.skin,
                "dialog");
        resultLable.setAlignment(Align.center);
        table.add(resultLable);
        table.row();
        Label highLable = new Label("High scores " + gameField.getHighScores(),
                Assets.instance.skinAssets.skin,
                "dialog");
        highLable.setAlignment(Align.center);
        table.add(highLable);
        gameOverDialog.getContentTable().add(table);
//        Button button = new TextButton("Ok",
//                true,
//                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class));
//        gameOverDialog.text("Game Over",
//                Assets.instance.skinAssets.skin.get("dialog", Label.LabelStyle.class))
//                .align(Align.center);
        gameOverDialog.button("Ok",
                true,
                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
        );

//        gameOverDialog.button("No",
//                false,
//                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
//        ); //sends "false" as the result

        Gdx.input.setInputProcessor(stage);
        gameOverDialog.show(stage);
    }


    private VerticalGroup timeHudLable(String title, int digit) {

        Skin skin = Assets.instance.skinAssets.skin;
        Label titleLable = new Label(title, skin, "small");
        timeLable = new Label(Integer.toString(digit), skin, "game");
        float size1 = titleLable.getHeight();
        float size2 = timeLable.getHeight();
        lableItemHeight = size1 + size2;
        VerticalGroup group = new VerticalGroup();
        group.addActor(titleLable);
        group.addActor(timeLable);
        return group;
    }

    private VerticalGroup scoreHudLable(String title, int digit) {

        Skin skin = Assets.instance.skinAssets.skin;
        Label titleLable = new Label(title, skin, "small");
        scoreLable = new Label(Integer.toString(digit), skin, "game");
        float size1 = titleLable.getHeight();
        float size2 = scoreLable.getHeight();
        lableItemHeight = size1 + size2;
        VerticalGroup group = new VerticalGroup();
        group.addActor(titleLable);
        group.addActor(scoreLable);
        return group;
    }



    public void rulesDialog() {
        ExitDialog gameOverDialog = new ExitDialog("", Assets.instance.skinAssets.skin, lineGame);
        gameOverDialog.setTransform(true);
        gameOverDialog.getBackground();

//        Table table = new Table();
        Label gameLable = new Label("Move balls to consist horizontal, vertical or diagonal lines of " +
                "minimum 5 balls.  ",
                Assets.instance.skinAssets.skin,
                "dialog");
        gameLable.setAlignment(Align.center);
//        table.add(gameLable);
        gameOverDialog.getContentTable().add(gameLable).padTop(40);
        gameOverDialog.getContentTable().row();

//        Table table = new Table();
//        Label resultLable = new Label("Game Score " + gameField.getGameScore(),
//                Assets.instance.skinAssets.skin,
//                "dialog");
//        resultLable.setAlignment(Align.center);
//        table.add(resultLable);
//        table.row();
//        Label highLable = new Label("High scores " + gameField.getHighScores(),
//                Assets.instance.skinAssets.skin,
//                "dialog");
//        highLable.setAlignment(Align.center);
//        table.add(highLable);
//        gameOverDialog.getContentTable().add(table);
//        Button button = new TextButton("Ok",
//                true,
//                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class));
//        gameOverDialog.text("Game Over",
//                Assets.instance.skinAssets.skin.get("dialog", Label.LabelStyle.class))
//                .align(Align.center);
        gameOverDialog.button("Ok",
                true,
                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
        );
        gameOverDialog.show(stage);
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
//        batch.dispose();
//        gameField.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {

        // Update HUD viewport
        hudViewport.update(width, height, true);

        // Set font scale to min(width, height) / reference screen size
//        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
    }
}
