package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static stas.lines2019.game.util.Assets.*;
import static stas.lines2019.game.util.Constants.*;

public class SkillButton extends ImageTextButton {

    public int WIDTH =(int)( Gdx.graphics.getWidth()*SKILL_BUTTON_SIZE);
    public int HEIGHT =(int)( Gdx.graphics.getWidth()*SKILL_BUTTON_SIZE);

    @Override
    public float getPrefWidth() {
        return WIDTH;
    }

    @Override
    public float getPrefHeight() {
        return HEIGHT;
    }

    public SkillButton(String text, Skin skin) {
        super(text, skin);
    }

//    public SkillButton(Skin skin, String text) {
//        super(skin);
//        Label energyLable = new Label(text,skin);
//        add(energyLable);
//        add(energyLable);
//    }
}
