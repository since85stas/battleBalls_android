package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.util.Constants;

public class BuyDialog extends Dialog {
    LinesGame game;
    int stars;

    public int WIDTH =(int)( Gdx.graphics.getWidth()*0.95);
    public int HEIGHT =(int)( Gdx.graphics.getHeight()*0.5);

    public BuyDialog(String title, Window.WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public BuyDialog(String title, Skin skin, LinesGame game) {
        super(title, skin);
        this.game = game;
        getContentTable().defaults().width(WIDTH - 0.05f*WIDTH);
        getButtonTable().defaults().width(WIDTH/3 - 0.07f*WIDTH);

    }

    @Override
    public Dialog button(String text) {
        return super.button(text);
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
            hide();
        } else if (object.equals(1) && game.numberOfStars >= stars ) {
            Gdx.app.log("buy","fal"  );
            if  ( game.numberOfStars >= stars  ) {
                game.setExpansGameIsBought();
                game.setGameScreenExpans();
            }
        }

    }

    @Override
    public void hide() {
        super.hide();
    }
}
