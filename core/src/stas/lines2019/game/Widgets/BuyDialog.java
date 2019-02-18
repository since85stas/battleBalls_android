package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.util.Constants;

public class BuyDialog extends Dialog {
    LinesGame game;

    public int WIDTH =(int)( Gdx.graphics.getWidth()*0.95);
    public int HEIGHT =(int)( Gdx.graphics.getHeight()*0.5);

    public BuyDialog(String title, Window.WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public BuyDialog(String title, Skin skin, LinesGame game) {
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
            game.purchaseManager.purchase(Constants.FRIEND_VERSION);
            Gdx.app.log("buy","true");
        } else if (object.equals(false)){
            Gdx.app.log("buy","fal"  );
            hide();
        }
    }

    @Override
    public void hide() {
        super.hide();
    }
}
