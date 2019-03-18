package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

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
        getContentTable().row().height(100);
        getContentTable().setDebug(true);

        SkillButton textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.teleportTexture)));
        textButton.align(Align.left);
//        textButton.setSize(WIDTH*SKILL_BUTTON_SIZE,WIDTH*SKILL_BUTTON_SIZE/1.5f );
//        Image icon = new Image(Assets.instance.skillAssets.teleportTexture);
//        icon.setSize(60,60);
//        group.addActor(textButton);
//        textButton.setSize(60,60);
        Label tetLable = new Label("2232",skin,"small");
//        getContentTable().add(textButton).width(100);

        getContentTable().add(textButton).width(60).height(60);

        Label label2 = new Label("teleport ball in any position at game field1111111111111111111",skin,"small");
//        label2.
//        label2.setAlignment(Align.left);
        label2.setWrap(true);
//        group.addActor(label);
        getContentTable().add(label2).padLeft(10).width(WIDTH-60);
//        getContentTable().row().height(60);

//        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
//                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.removeTexture)));
//        getContentTable().add(textButton);
//        label = new Label("teloport:2",skin,"small");
//        getContentTable().add(label);
//        getContentTable().row().height(60);
//
//        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
//                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.blockTexture)));
//        getContentTable().add(textButton);
//        label = new Label("teloport:3",skin,"small");
//        getContentTable().add(label);
//        getContentTable().row().height(60);
//
//        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
//                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.colorelessTexture)));
//        getContentTable().add(textButton);
//        label = new Label("teloport:4",skin,"small");
//        getContentTable().add(label);
//        getContentTable().row().height(60);
//
//        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
//                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.bombTexture)));
//        getContentTable().add(textButton);
//        label = new Label("teloport:5",skin,"small");
//        getContentTable().add(label);
//        getContentTable().row().height(60);
    }

//    @Override
//    public float getPrefWidth() {
//        return WIDTH;
//    }
//
//    @Override
//    public float getPrefHeight() {
//        return HEIGHT;
//    }



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
