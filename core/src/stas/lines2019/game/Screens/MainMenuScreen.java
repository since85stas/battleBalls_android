package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.DelayedRemovalArray;
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

    private DelayedRemovalArray<MenuBall> menuBalls;

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
        for (int i = 0; i < menuBalls.size; i++) {
            menuBalls.get(i).render(batch,delta);
            menuBalls.get(i).update(delta);
            if (menuBalls.get(i).getPath() > height - 100) {
                menuBalls.removeIndex(i);
                MenuBall ball = generateOneBall();
                menuBalls.add(ball);
                stage.addActor(ball);
            }
        }
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
        menuBalls = new DelayedRemovalArray();
        for (int i = 0; i < Constants.MENU_BALLS_INIT_NUMBERS; i++) {
            MenuBall ball = generateOneBall();
            menuBalls.add(ball);
            stage.addActor(ball);

        }

//        ball = new MenuBall(new Vector2(widtht/2,height/2),100,100);
//        ball.initTexture(Assets.instance.blueBallAssets.texture);
//        ball.setVelocity(new Vector2(20,20));
//        ball.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                Gdx.app.log(TAG,"ball pressed");
//                return super.touchDown(event, x, y, pointer, button);
//
//            }
//        });
//        stage.addActor(ball);
    }

    private MenuBall generateOneBall() {
        int random = MathUtils.random(0,1);
        Vector2 initPos = new Vector2();
        Vector2 initVel = new Vector2();
        if(random == 0) {
            int x = generateXinit();
            int y = MathUtils.random(0,height);
            initPos = new Vector2(x,y);
            initVel = generateBallVelocity(initPos,0);
        } else if (random == 1) {
            int x = MathUtils.random(0,widtht );
            int y = generateYinit();
            initPos = new Vector2(x,y);
            initVel = generateBallVelocity(initPos,1);
        }
        int randomSize = MathUtils.random(-10,10);
        final MenuBall ball = new MenuBall(initPos,
                50  +randomSize ,
                50  + randomSize);
        ball.setVelocity(initVel);
        ball.initTexture(getBallColorText(MathUtils.random(0,3)));
        ball.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (int i = 0; i < menuBalls.size; i++) {
                    if (menuBalls.get(i).equals(ball)) {
                        menuBalls.removeIndex(i);
                        menuBalls.add(generateOneBall());
                    }
                }
                return true;
            }
        });
        return ball;
    }

    private int generateXinit () {
        int xRandom =  100;
        int x = 0;
        int random = MathUtils.random(0,1);
        if(random == 0) {
            x = 0 - MathUtils.random(5,xRandom);
        } else if (random == 1) {
            x = MathUtils.random(widtht,widtht + xRandom );
        }
        return x;
    }

    private int generateYinit () {
        int yRandom =  200;
        int x = 0;
        int random = MathUtils.random(0,1);
        if(random == 0) {
            x = 0 - MathUtils.random(5,yRandom);
        } else if (random == 1) {
            x = MathUtils.random(height,height + yRandom );
        }
        return x;
    }

    private Vector2 generateBallVelocity(Vector2 initPos, int axisType) {
        Vector2 vel = new Vector2();
        if(axisType == 0) {
            int dx = (int)initPos.x - widtht/2;
            int dy = (int)initPos.y - height/2;
            if (dx <= 0) {
                float vX = MathUtils.random(Constants.MENU_BALLS_VEL_MIN,Constants.MENU_BALLS_VEL_MAX);
                float vY = MathUtils.random(-Constants.MENU_BALLS_VEL_RANGE,Constants.MENU_BALLS_VEL_RANGE);
                vel = new Vector2(vX,vY);
            } else {
                float vX = -MathUtils.random(Constants.MENU_BALLS_VEL_MIN,Constants.MENU_BALLS_VEL_MAX);
                float vY = MathUtils.random(-Constants.MENU_BALLS_VEL_RANGE,Constants.MENU_BALLS_VEL_RANGE);
                vel = new Vector2(vX,vY);
            }
        }  else if (axisType == 1) {
            int dx = (int)initPos.x - widtht/2;
            int dy = (int)initPos.y - height/2;
            if (dy <= 0) {
                float vX = MathUtils.random(-Constants.MENU_BALLS_VEL_RANGE,Constants.MENU_BALLS_VEL_RANGE);
                float vY = MathUtils.random(Constants.MENU_BALLS_VEL_MIN,Constants.MENU_BALLS_VEL_MAX);
                vel = new Vector2(vX,vY);
            } else {
                float vX = MathUtils.random(-Constants.MENU_BALLS_VEL_RANGE,Constants.MENU_BALLS_VEL_RANGE);
                float vY = -MathUtils.random(Constants.MENU_BALLS_VEL_MIN,Constants.MENU_BALLS_VEL_MAX);
                vel = new Vector2(vX,vY);
            }
        }


        return vel;
    }

    public Texture getBallColorText( int ballColor ) {
        String textureName = null;
        Texture texture = null;
        switch (ballColor) {
            case 0:
                //textureName = "sphere_blue.png";
                texture = Assets.instance.blueBallAssets.texture;
                break;
            case 1:
//                textureName = "sphere_green.png";
                texture = Assets.instance.greenBallAssets.texture;
                break;
            case 2:
//                textureName = "sphere_purle.png";
                texture = Assets.instance.purpleBallAssets.texture;
                break;
            case 3:
//                textureName = "sphere_yellow.png";
                texture = Assets.instance.yellowBallAssets.texture;
                break;
        }
        return texture;
    }

}
