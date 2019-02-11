package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import stas.lines2019.game.LinesGame;

/**
 * Created by seeyo on 11.02.2019.
 */

public class RulesDialog extends Dialog {

    LinesGame game;

    public int WIDTH =(int)( Gdx.graphics.getWidth()*0.95);
    public int HEIGHT =(int)( Gdx.graphics.getHeight()*0.5);

    public RulesDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public RulesDialog(String title, Skin skin, LinesGame game) {
        super(title, skin);
        this.game = game;
        getContentTable().defaults().width(WIDTH - 0.05f*WIDTH);
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
        Gdx.app.log("dia", object.toString());
        if (object.equals(true)) {
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
