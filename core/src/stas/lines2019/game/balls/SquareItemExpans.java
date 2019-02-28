package stas.lines2019.game.balls;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import stas.lines2019.game.Screens.GameScreen;
import stas.lines2019.game.SquareItem;
import stas.lines2019.game.util.Assets;

/**
 * Created by seeyo on 28.02.2019.
 */

public class SquareItemExpans extends SquareItem {

    private int toughtness;
    private BitmapFont font;

    public SquareItemExpans(GameScreen gameScreen, int width, int height, Vector2 position) {
        super(gameScreen, width, height, position);
        ballIsTight(true);
//        font = Assets.instance.skinAssets.skin.get("inball-font",BitmapFont.class);
        toughtness = 2;
    }

    @Override
    public void drawToughNumber(SpriteBatch batch) {
//        font.draw(batch,Integer.toString(toughtness),getCenterPosition().x,getCenterPosition().y);

    }
}
