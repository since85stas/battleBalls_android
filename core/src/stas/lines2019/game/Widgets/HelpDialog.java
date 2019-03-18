package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
//        setTransform(true);

        getContentTable().setWidth(WIDTH);
        getContentTable().setHeight(HEIGHT);
        getContentTable().defaults().maxHeight(40);

        Label label = new Label("Buttons:",skin);
        label.setHeight(60);
        getContentTable().add(label);
        getContentTable().row();

        SkillButton textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.teleportTexture)));
//        textButton.setSize(WIDTH*SKILL_BUTTON_SIZE,WIDTH*SKILL_BUTTON_SIZE/1.5f );
//        Image icon = new Image(Assets.instance.skillAssets.teleportTexture);
//        icon.setSize(60,60);
        getContentTable().add(textButton);
        label = new Label("teloport:",skin);
        getContentTable().add(label);
        getContentTable().row();

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.teleportTexture)));
//        textButton.setSize(WIDTH*SKILL_BUTTON_SIZE,WIDTH*SKILL_BUTTON_SIZE/1.5f );
//        Image icon = new Image(Assets.instance.skillAssets.teleportTexture);
//        icon.setSize(60,60);
        getContentTable().add(textButton);
        label = new Label("teloport:",skin);
        getContentTable().add(label);
        getContentTable().row();

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.teleportTexture)));
//        textButton.setSize(WIDTH*SKILL_BUTTON_SIZE,WIDTH*SKILL_BUTTON_SIZE/1.5f );
//        Image icon = new Image(Assets.instance.skillAssets.teleportTexture);
//        icon.setSize(60,60);
        getContentTable().add(textButton);
        label = new Label("teloport:",skin);
        getContentTable().add(label);
        getContentTable().row();

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.teleportTexture)));
//        textButton.setSize(WIDTH*SKILL_BUTTON_SIZE,WIDTH*SKILL_BUTTON_SIZE/1.5f );
//        Image icon = new Image(Assets.instance.skillAssets.teleportTexture);
//        icon.setSize(60,60);
        getContentTable().add(textButton);
        label = new Label("teloport:",skin);
        getContentTable().add(label);
        getContentTable().row();

        textButton = new SkillButton("",Assets.instance.skinAssets.skin,
                new TextureRegionDrawable( new TextureRegion(Assets.instance.skillAssets.teleportTexture)));
//        textButton.setSize(WIDTH*SKILL_BUTTON_SIZE,WIDTH*SKILL_BUTTON_SIZE/1.5f );
//        Image icon = new Image(Assets.instance.skillAssets.teleportTexture);
//        icon.setSize(60,60);
        getContentTable().add(textButton);
        label = new Label("teloport:",skin);
        getContentTable().add(label);
        getContentTable().row();
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
