package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import static stas.lines2019.game.util.Constants.*;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.util.Assets;

import static stas.lines2019.game.util.Constants.SKILL_BUTTON_SIZE;

public class HelpDialog extends Dialog {

    LinesGame game;

    public int WIDTH =(int)( Gdx.graphics.getWidth()*0.95);
    public int HEIGHT =(int)( Gdx.graphics.getHeight()*0.6);

    public HelpDialog(String title, Window.WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public HelpDialog(String title, Skin skin, LinesGame game) {
        super(title, skin);
        this.game = game;
        setTransform(false);
        getBackground();

//        setSize(WIDTH,HEIGHT);

//        setModal(true);
//        setMovable(false);
        setResizable(false);
        padLeft(10);

        getContentTable().setFillParent(true);

//        getContentTable().defaults().width(WIDTH - 0.05f*WIDTH);
//        getContentTable().defaults().maxHeight(60);
//        getContentTable().defaults().maxWidth(WIDTH - 0.05f*WIDTH);
        getContentTable().setPosition(0,0);;
        getContentTable().align(Align.topLeft);

        Table table = getContentTable();

//        Label label = new Label("Buttons:",skin);
//        label.setHeight(60);
//        getContentTable().add(label).padLeft(10);
//        getContentTable().setDebug(true);

//        textButton.setSize(60,60);
        Label tetLable = new Label("Skills",skin,"small-energy");
        getContentTable().add(tetLable).padTop(WIDTH*HELP_OFFSET_Y*5).padLeft(WIDTH*HELP_OFFSET_X);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        SkillButton textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.teleportTexture)));
        textButton.align(Align.left).padTop(WIDTH*HELP_OFFSET_Y*2);
        getContentTable().add(textButton).width(WIDTH*HELP_IMAGE_SIZE).height(WIDTH*HELP_IMAGE_SIZE).
                padLeft(WIDTH*HELP_OFFSET_X);
        Label label2 = new Label(Assets.instance.bundle.get("helpTeleport"),skin,"small");
        label2.setWrap(true);
        getContentTable().add(label2).width(WIDTH-WIDTH*HELP_IMAGE_SIZE*2).
                padLeft(WIDTH*HELP_OFFSET_X).padRight(WIDTH*HELP_OFFSET_X);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.removeTexture)));
        textButton.align(Align.left);
        getContentTable().add(textButton).width(WIDTH*HELP_IMAGE_SIZE).height(WIDTH*HELP_IMAGE_SIZE).
                padLeft(WIDTH*HELP_OFFSET_X);
        label2 = new Label(Assets.instance.bundle.get("helpRemove"),skin,"small");
        label2.setWrap(true);
        getContentTable().add(label2).width(WIDTH-WIDTH*HELP_IMAGE_SIZE*2).
                padLeft(WIDTH*HELP_OFFSET_X).padRight(WIDTH*HELP_OFFSET_X);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.colorelessTexture)));
        textButton.align(Align.left);
        getContentTable().add(textButton).width(WIDTH*HELP_IMAGE_SIZE).height(WIDTH*HELP_IMAGE_SIZE).
                padLeft(WIDTH*HELP_OFFSET_X);
        label2 = new Label(Assets.instance.bundle.get("helpColoreless"),skin,"small");
        label2.setWrap(true);
        getContentTable().add(label2).width(WIDTH-WIDTH*HELP_IMAGE_SIZE*2).
                padLeft(WIDTH*HELP_OFFSET_X).padRight(WIDTH*HELP_OFFSET_X);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.blockTexture)));
        textButton.align(Align.left);
        getContentTable().add(textButton).width(WIDTH*HELP_IMAGE_SIZE).height(WIDTH*HELP_IMAGE_SIZE).
                padLeft(WIDTH*HELP_OFFSET_X);
        label2 = new Label(Assets.instance.bundle.get("helpBlock"),skin,"small");
        label2.setWrap(true);
        getContentTable().add(label2).width(WIDTH-WIDTH*HELP_IMAGE_SIZE*2).
                padLeft(WIDTH*HELP_OFFSET_X).padRight(WIDTH*HELP_OFFSET_X);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.bombTexture)));
        textButton.align(Align.left);
        getContentTable().add(textButton).width(WIDTH*HELP_IMAGE_SIZE).height(WIDTH*HELP_IMAGE_SIZE).
                padLeft(WIDTH*HELP_OFFSET_X);
        label2 = new Label(Assets.instance.bundle.get("helpBomb"),skin,"small");
        label2.setWrap(true);
        getContentTable().add(label2).width(WIDTH-WIDTH*HELP_IMAGE_SIZE*2).
                padLeft(WIDTH*HELP_OFFSET_X).padRight(WIDTH*HELP_OFFSET_X);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        // drawing balls

        tetLable = new Label("Balls",skin,"small-energy");
        getContentTable().add(tetLable).padTop(WIDTH*HELP_OFFSET_Y*3).padLeft(WIDTH*HELP_OFFSET_X).
                padTop(HEIGHT*HELP_ROW_HEIGT*2);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.numberAssets.getNumTexture(2))));
        textButton.align(Align.left);
        getContentTable().add(textButton).width(WIDTH*HELP_IMAGE_SIZE).height(WIDTH*HELP_IMAGE_SIZE).
                padLeft(WIDTH*HELP_OFFSET_X);
        label2 = new Label(Assets.instance.bundle.get("helpBallTough"),skin,"small");
        label2.setWrap(true);
        getContentTable().add(label2).width(WIDTH-WIDTH*HELP_IMAGE_SIZE*2).
                padLeft(WIDTH*HELP_OFFSET_X).padRight(WIDTH*HELP_OFFSET_X);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.lockAssets.texture)));
        textButton.align(Align.left);
        getContentTable().add(textButton).width(WIDTH*HELP_IMAGE_SIZE).height(WIDTH*HELP_IMAGE_SIZE).
                padLeft(WIDTH*HELP_OFFSET_X);
        label2 = new Label(Assets.instance.bundle.get("helpBallLock"),skin,"small");
        label2.setWrap(true);
        getContentTable().add(label2).width(WIDTH-WIDTH*HELP_IMAGE_SIZE*2).
                padLeft(WIDTH*HELP_OFFSET_X).padRight(WIDTH*HELP_OFFSET_X);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.colorlessBallAssets.texture)));
        textButton.align(Align.left);
        getContentTable().add(textButton).width(WIDTH*HELP_IMAGE_SIZE).height(WIDTH*HELP_IMAGE_SIZE).
                padLeft(WIDTH*HELP_OFFSET_X);
        label2 = new Label(Assets.instance.bundle.get("helpBallColorless"),skin,"small");
        label2.setWrap(true);
        getContentTable().add(label2).width(WIDTH-WIDTH*HELP_IMAGE_SIZE*2).
                padLeft(WIDTH*HELP_OFFSET_X).padRight(WIDTH*HELP_OFFSET_X);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.bombAssets.texture)));
        textButton.align(Align.left);
        getContentTable().add(textButton).width(WIDTH*HELP_IMAGE_SIZE).height(WIDTH*HELP_IMAGE_SIZE).
                padLeft(WIDTH*HELP_OFFSET_X);
        label2 = new Label(Assets.instance.bundle.get("helpBallBomb"),skin,"small");
        label2.setWrap(true);
        getContentTable().add(label2).width(WIDTH-WIDTH*HELP_IMAGE_SIZE*2).
                padLeft(WIDTH*HELP_OFFSET_X).padRight(WIDTH*HELP_OFFSET_X);
        getContentTable().row().height(HEIGHT*HELP_ROW_HEIGT);

        button("Ok",
                false,
                Assets.instance.skinAssets.skin.get("small",
                        TextButton.TextButtonStyle.class)
        );
    }



    @Override
    protected void result(Object object) {
        Gdx.app.log("dia", object.toString());
        if (object.equals(true)) {
            game.setMainMenuScreen();
        } else if (object.equals(false)){
            game.getGameScreen().gameField.setInputProccActive(true);
        }
    }
}
