package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Created by seeyo on 08.02.2019.
 */

public class ExitDialog extends Dialog {

    public int WIDTH =(int)( Gdx.graphics.getWidth()*0.95);
    public int HEIGHT =(int)( Gdx.graphics.getWidth()*0.4);

    public ExitDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public ExitDialog(String title, Skin skin) {
        super(title, skin);
    }

    @Override
    public float getPrefWidth() {
        return WIDTH;
    }

    @Override
    public float getPrefHeight() {
        return HEIGHT;
    }

    @Override
    protected void result(Object object) {
        if (object.equals(true)) {

        } else if (object.equals(false)){

        }
  }
}
