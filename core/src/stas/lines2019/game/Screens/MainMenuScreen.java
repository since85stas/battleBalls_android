package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import stas.lines2019.game.LinesGame;
import stas.lines2019.game.MenuBall;
import stas.lines2019.game.Widgets.BuyDialog;
import stas.lines2019.game.Widgets.DifficultDialog;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;
import stas.lines2019.game.util.ConstantsAchiveEng;

/**
 * Created by seeyo on 04.12.2018.
 */

public class MainMenuScreen extends InputAdapter implements Screen{

    private static final String TAG = MainMenuScreen.class.getName().toString();

    private static final int numButtons = 8;

    private DelayedRemovalArray<MenuBall> menuBalls;

    private final String[] buttonNames = {
            Assets.instance.bundle.get("BtnContinue"),
            Assets.instance.bundle.get("BtnClass"),
            Assets.instance.bundle.get("BtnSurv"),
            Assets.instance.bundle.get("BtnExtens"),
            Assets.instance.bundle.get("BtnPuzz"),
            Assets.instance.bundle.get("BtnAchiev"),
            Assets.instance.bundle.get("BtnSupp"),
            Assets.instance.bundle.get("BtnExt")
    };

    SpriteBatch batch;
//    BitmapFont hudFont;
    LinesGame mGame;

    private Stage stage;
    public Skin mySkin;
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

        batch.begin();

        batch.draw(Assets.instance.backAssets.texture,
                0,
                0,
                widtht,
                height
        );
        for (int i = 0; i < menuBalls.size; i++) {
            menuBalls.get(i).render(batch,delta);
            menuBalls.get(i).update(delta);
            if (menuBalls.get(i).getPath() > height ) {
                menuBalls.removeIndex(i);
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
        batch.end();

        stage.act();
        stage.draw();
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

        Label titleLable = new Label(Assets.instance.bundle.get("MenuTitle"),mySkin,"menu");
        titleLable.layout();
        GlyphLayout lay = new GlyphLayout();
        lay.setText(mySkin.getFont("menu-font"),Assets.instance.bundle.get("MenuTitle"));
        int len = (int)lay.width;
        titleLable.setPosition((widtht - len)/2,height - 2*Constants.TITLE_UPPER_OFFSET*height);
        stage.addActor(titleLable);

        TextButton[] buttons = new TextButton[numButtons];

        float buttonX = (widtht - widtht*Constants.BUTTONS_WIDTH)/2;
        float buttonY = height - height*Constants.BUTTONS_UPPER_OFFSET;

        String achiveText  =buttonNames[4] +  " " +
        Integer.toString(mGame.achivementsList.getCompleteAchievsNumber()) + "/" +
                Integer.toString(ConstantsAchiveEng.NUM_ACHIVEMENTS);

        for (int i = 0; i < buttons.length; i++) {
            buttonY -= height*Constants.BUTTONS_HEIGHT + height*Constants.BUTTONS_BETWEEN_SPACE;

            buttons[i] = new TextButton(buttonNames[i],mySkin,"menu");
            if (i == 4) {
                buttons[i].setDisabled(true);
            }

            if(!mGame.findSaveGame) {
                buttons[0].setDisabled(true);
            }
            buttons[i].setSize(widtht*Constants.BUTTONS_WIDTH,
                    height*Constants.BUTTONS_HEIGHT);

            buttons[i].setPosition(buttonX,buttonY);

            final int finalI = i;
            buttons[i].addListener(new InputListener(){
                @Override
                public boolean keyDown(InputEvent event, int keycode) {
                    if(keycode == Input.Keys.BACK){

                        // Respond to the back button click here
//                    dispose();
                        Gdx.app.log(TAG,"ssss");
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("PreScreen","Pressed");
                    switch (finalI){
                        case 0:
                            if (mGame.findSaveGame) {
                                mGame.setGameScreen();
                            }
                            break;
                        case 1:
                            mGame.findSaveGame = false;
                            mGame.setGameScreen();
                            break;
                        case 2:
                            mGame.findSaveGame = false;
                            selectDifficDialog();
                            break;
                        case 3:
                            mGame.findSaveGame = false;

                            if (mGame.expansGameIsBought || mGame.friendGameIsBought) {
                                mGame.setGameScreenExpans();
                            } else {
                                showStarBuyDialog(Constants.EXPANSION_STAR_COST,
                                        Constants.EXPANS_GAME_IS_BOUGHT );
                            }
                            break;
                        case 5:
                            mGame.setAchieveScreen();
                            break;
                        case 6:
                            mGame.purchaseManager.purchase(Constants.FRIEND_VERSION);
                            break;
                        case 7:
                            Gdx.app.exit();
                            break;
                    }

                    return true;
                }
            });
            stage.addActor(buttons[i]);
        }
//        buttons[4].setText(achiveText);

        // change achievement text
        buttons[5].add(new Image(Assets.instance.starAssets.texture));
        buttons[5].add(Integer.toString(mGame.numberOfStars),
                "dialog");


        Table table = new Table();
        table.row();
        table.add(new Label(Assets.instance.bundle.get("Soon"), mySkin,"small"));

        buttons[4].add(table);

        Table table2 = new Table();
        table2.row();
        table2.add(new Label(Assets.instance.bundle.get("new"), mySkin,"xp"));
        buttons[3].add(table2);
    }


    private void selectDifficDialog() {
        DifficultDialog dialog = new DifficultDialog(Assets.instance.bundle.get("diffSurvSelect"),
                Assets.instance.skinAssets.skin,mGame,this);
        dialog.setTransform(true);
        dialog.getBackground();

        Gdx.input.setInputProcessor(stage);
        dialog.show(stage);
    }

//    public void showBuyDialog() {
//
//        BuyDialog dialog = new BuyDialog ("", Assets.instance.skinAssets.skin,mGame);
//        dialog.setTransform(true);
//        dialog.getBackground();
//
//        Label gameLable = new Label(Assets.instance.bundle.get("buyDialog"),
//                Assets.instance.skinAssets.skin,
//                "dialog");
//        gameLable.setWrap(true);
//        gameLable.setAlignment(Align.left);
////        table.add(gameLable);
//        dialog.getContentTable().add(gameLable).padTop(40);
//        dialog.getContentTable().row();
//
//        dialog.button(Assets.instance.bundle.get("buy"),
//                true,
//                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
//        );
//        dialog.button(Assets.instance.bundle.get("no"),
//                false,
//                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
//        ); //sends "false" as the result
//
//        Gdx.input.setInputProcessor(stage);
//        dialog.show(stage);
//    }

    public void showStarBuyDialog(int cost, String key) {

        BuyDialog dialog = new BuyDialog ("",
                key,
                cost ,
                Assets.instance.skinAssets.skin,
                mGame);

        Gdx.input.setInputProcessor(stage);
        dialog.show(stage);
    }


//    }

    public void generateBalls() {
        menuBalls = new DelayedRemovalArray();
        for (int i = 0; i < Constants.MENU_BALLS_INIT_NUMBERS; i++) {
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
