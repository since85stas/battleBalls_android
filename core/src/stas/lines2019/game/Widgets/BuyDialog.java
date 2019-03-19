package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

public class BuyDialog extends Dialog {
    LinesGame game;
    int stars;

    public int WIDTH =(int)( Gdx.graphics.getWidth()*0.95);
    public int HEIGHT =(int)( Gdx.graphics.getHeight()*0.5);

    public BuyDialog(String title, Window.WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public BuyDialog(String title, String key,int cost, Skin skin, LinesGame game) {
        super(title, skin);
        stars = cost;
        this.game = game;
        getContentTable().defaults().width(WIDTH - 0.05f*WIDTH);
        getButtonTable().defaults().width(WIDTH/3 - 0.02f*WIDTH);

        setTransform(true);
        getBackground();

        Label gameLable = new Label(Assets.instance.bundle.get("buyDialog"),
                Assets.instance.skinAssets.skin,
                "dialog");
        gameLable.setWrap(true);
        gameLable.setAlignment(Align.left);
        getContentTable().add(gameLable).padTop(40);
        getContentTable().row();

        button( Assets.instance.bundle.get("buy"),
                true,
                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
        );

        TextButton starButton = new TextButton( Integer.toString(cost),
                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
        );

        starButton.add(new Image(Assets.instance.starAssets.achieveTexture));
        starButton.padLeft(WIDTH*0.05f);
        starButton.padRight(WIDTH*0.05f);
        starButton.align(Align.center);

        button( starButton,
                key
        );

        button( Assets.instance.bundle.get("no"),
                false,
                Assets.instance.skinAssets.skin.get("small", TextButton.TextButtonStyle.class)
        );
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
        } else if (object.equals(Constants.EXPANS_GAME_IS_BOUGHT) && game.numberOfStars >= stars ) {
            Gdx.app.log("buy","fal"  );
            if  ( game.numberOfStars >= stars  ) {
                game.setExpansGameIsBought();
                game.setGameScreenExpans();
            }
        } else if (object.equals(Constants.DIFFICULT_HARD_BOUGHT) && game.numberOfStars >= stars) {
            Gdx.app.log("buy","fal"  );
            if  ( game.numberOfStars >= stars  ) {
                game.setHardGameIsBought();
//                game.setGameScreenExpans();
            }
        }

    }

    @Override
    public void hide() {
        super.hide();
    }
}
