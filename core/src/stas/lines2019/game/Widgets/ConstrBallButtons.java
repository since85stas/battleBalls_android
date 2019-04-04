package stas.lines2019.game.Widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by seeyo on 01.04.2019.
 */

public class ConstrBallButtons extends TextButton {

    public int colorNumber;


    public ConstrBallButtons(int colorNumber, Skin skin, Drawable drawable) {
        super("", skin);
        this.colorNumber = colorNumber;
        getLabel().setStyle(skin.get("small-energy", Label.LabelStyle.class));
        add(new Image(drawable));
//        getStyle().imageUp = drawable;
    }
}
