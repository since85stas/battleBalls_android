package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import static stas.lines2019.game.util.Assets.*;
import static stas.lines2019.game.util.Constants.*;

public class SkillButton extends TextButton {

    public int WIDTH =(int)( Gdx.graphics.getWidth()*SKILL_BUTTON_SIZE);
    public int HEIGHT =(int)( Gdx.graphics.getWidth()*SKILL_BUTTON_SIZE);

//    @Override
//    public float getPrefWidth() {
//        return WIDTH;
//    }
//
//    @Override
//    public float getPrefHeight() {
//        return HEIGHT;
//    }

    public SkillButton(String text, Skin skin, Drawable drawable) {
        super(text, skin);
        getLabel().setStyle(skin.get("small-energy", Label.LabelStyle.class));

//        setSize(50,50);
        add(new Image(drawable));
//        getStyle().imageUp = drawable;
    }

}
