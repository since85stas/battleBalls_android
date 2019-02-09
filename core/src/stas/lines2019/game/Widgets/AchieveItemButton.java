package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.bcel.internal.classfile.Constant;

public class AchieveItemButton extends Button {

    public AchieveItemButton(Skin skin) {
        super(skin);
    }

    public AchieveItemButton(Skin skin, String styleName) {
        super(skin, styleName);
    }

    public AchieveItemButton(Actor child, Skin skin, String styleName) {
        super(child, skin, styleName);
    }

    public AchieveItemButton(Actor child, ButtonStyle style) {
        super(child, style);
    }

    public AchieveItemButton(ButtonStyle style) {
        super(style);
    }

    @Override
    public float getPrefWidth() {
        return Gdx.graphics.getWidth()* stas.lines2019.game.util.Constants.ACHIEVE_WIDTH;
    }

    @Override
    public float getPrefHeight() {
        return Gdx.graphics.getWidth()* stas.lines2019.game.util.Constants.ACHIEVE_HEIGHT;
    }
}
