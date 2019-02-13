package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import stas.lines2019.game.LinesGame;

/**
 * Created by seeyo on 08.02.2019.
 */

public class ExitDialog extends Dialog {

    LinesGame game;

    public int WIDTH =(int)( Gdx.graphics.getWidth()*0.95);
    public int HEIGHT =(int)( Gdx.graphics.getHeight()*0.4);

    public ExitDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public ExitDialog(String title, Skin skin, LinesGame game) {
        super(title, skin);
        this.game = game;
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
        Gdx.app.log("Exit dialog", object.toString());
        if (object.equals(true)) {
            hide();
            game.setMainMenuScreen();

        } else if (object.equals(false)){
            game.getGameScreen().gameField.setInputProccActive(true);
        }
  }

    @Override
    public void hide() {
        super.hide();
    }
}
