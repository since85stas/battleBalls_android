package stas.lines2019.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.Widgets.AchieveItemButton;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

public class AchieveScreen implements Screen {

    public LinesGame lineGame;
    private SpriteBatch batch ;
    private Stage stage;

    private int width;
    private int height;

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport = new ScreenViewport();

    // Add BitmapFont
//    BitmapFont font;

    private Skin mySkin;
    private Table container;

    public  AchieveScreen (LinesGame lineGame,SpriteBatch batch){
        this.lineGame = lineGame;
        this.batch      = batch ;
    }

    @Override
    public void show() {
        mySkin = Assets.instance.skinAssets.skin;

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        hudViewport = new ScreenViewport();

        stage = new Stage();
        stage.setViewport(hudViewport);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        String[] achievsDescr = lineGame.achivementsList.getAchievDescrArray();
        Button[] acievmentsItems = new Button[achievsDescr.length];
        int[]    achievementComp = lineGame.achivementsList.getAchievCompArray();

        Table achievments = new Table().pad(20);

        for (int i = 0; i < achievsDescr.length; i++) {
            acievmentsItems[i] = createAchieveItem(achievsDescr[i],i,achievementComp[i]);
            achievments.add(acievmentsItems[i]);
            achievments.row();
        }

//        scroll.addPage(achievments);
        ScrollPane commonScroll = new ScrollPane(achievments,mySkin);
//        achievments.setSize(200,200);
        commonScroll.setPosition(0,0);
        commonScroll.setFillParent(true);
//        commonScroll.scr

//        achievments.setFillParent(true);
        achievments.align(Align.left);
        achievments.pad(20);

        stage.addActor(commonScroll);
    }

    public Button createAchieveItem(String descr, int number, int isComplete) {
        Button button = new AchieveItemButton(mySkin,"default");

        //
        Label label = new Label(descr, mySkin,"small");
//        label.setSize(width*0.5f,100);
        label.setWrap(true);
        label.setAlignment(Align.left);

        // create image
        Image image;
        if (isComplete == 1) {
            image = new Image(Assets.instance.starAssets.achieveTexture);
        }  else if (isComplete == 0) {
            image = new Image(Assets.instance.lockAssets.texture);
        } else {
            image = new Image(Assets.instance.lockAssets.texture);
        }
        image.setSize(Constants.ACHIEVE_HEIGHT*height/2,Constants.ACHIEVE_HEIGHT*height/2);

        image.setAlign(Align.left);

        Table table = new Table();
//        table.setFillParent(true);

        table.add(image);
        table.add(label);

        button.add(table);

        button.setName("item " + number);
        button.align(Align.left);
        if (isComplete == 0) {
            button.setDisabled(true);
        }
//        button.setWidth(400);
//        button.setFillParent(true);
//        button.setDisabled(true);
//        button.setSize(width*1f,100);
//        button.addListener(levelClickListener);
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.47f, 0.65f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            lineGame.setMainMenuScreen();
        }
//        Table(stage);
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        // Update HUD viewport
        hudViewport.update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
