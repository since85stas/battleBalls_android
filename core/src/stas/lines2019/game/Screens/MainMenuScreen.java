package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import stas.lines2019.game.LinesGame;
import stas.lines2019.game.MenuBall;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

/**
 * Created by seeyo on 04.12.2018.
 */

public class MainMenuScreen extends InputAdapter implements Screen{

    private static final String TAG = MainMenuScreen.class.getName().toString();

    private static final int numButtons = 4;

    private static final String[] buttonNames = {
            "continue",
            "new game",
            "achievements",
            "exit"
    };

    SpriteBatch batch;
//    BitmapFont hudFont;
    LinesGame mGame;

    private Stage stage;
    private Skin mySkin;
    int widtht;
    int height;

    MenuBall ball;

    public MainMenuScreen(LinesGame game) {
        mGame = game;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        widtht = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
//        mySkin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
        mySkin = Assets.instance.skinAssets.skin;
//        mySkin.
        generateButtons();
        Gdx.input.setInputProcessor(stage);

        generateBalls();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.47f, 0.65f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fps = 1 / delta;
//        Gdx.app.log("preLevel","fps =" + fps);

        stage.act();
        stage.draw();

        batch.begin();
        ball.render(batch,delta);
//        ball.update(delta);
        batch.end();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
//        hudFont.dispose();
    }

    private void generateButtons() {

        Label titleLable = new Label("LINES 2019",mySkin,"menu");
        titleLable.layout();
        GlyphLayout lay = new GlyphLayout();
        lay.setText(mySkin.getFont("menu-font"),"LINES 2019");
        int len = (int)lay.width;
//        titleLable.getGlyphLayout().setText(mySkin.getFont("menu-font"),"mew");
//        titleLable
//        int len = (int)titleLable.getGlyphLayout();
        titleLable.setPosition((widtht - len)/2,height - 2*Constants.TITLE_UPPER_OFFSET*height);
        stage.addActor(titleLable);

        TextButton[] buttons = new TextButton[numButtons];

        float buttonX = (widtht - widtht*Constants.BUTTONS_WIDTH)/2;
        float buttonY = height - height*Constants.BUTTONS_UPPER_OFFSET;

//        TextButton.TextButtonStyle style = mySkin.get

        for (int i = 0; i < buttons.length; i++) {
            buttonY -= height*Constants.BUTTONS_HEIGHT + height*Constants.BUTTONS_BETWEEN_SPACE;

            buttons[i] = new TextButton(buttonNames[i],mySkin,"default");

            if(!mGame.findSaveGame) {
                buttons[0].setDisabled(true);
            }
            buttons[i].setSize(widtht*Constants.BUTTONS_WIDTH,
                    height*Constants.BUTTONS_HEIGHT);

            buttons[i].setPosition(buttonX,buttonY);

            final int finalI = i;
            buttons[i].addListener(new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("PreScreen","Pressed");
                    switch (finalI){
                        case 0:
                            mGame.setGameScreen();
                            break;
                        case 1:
                            mGame.findSaveGame = false;
                            mGame.setGameScreen();
                            break;
                        case 2:
                            mGame.setAchieveScreen();
                            break;
                        case 3:
                            break;
                    }

                    return true;
                }
            });
            stage.addActor(buttons[i]);
        }
    }
//    }

    public void generateBalls() {
        ball = new MenuBall(new Vector2(widtht/2,height/2),100,100);
        ball.initTexture(Assets.instance.blueBallAssets.texture);
        ball.setVelocity(new Vector2(20,20));
        ball.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG,"ball pressed");
                return super.touchDown(event, x, y, pointer, button);

            }
        });
        stage.addActor(ball);
    }
    private void buttonPressed(int button) {

    }

}
