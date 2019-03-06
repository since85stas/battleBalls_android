package stas.lines2019.game.balls;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import stas.lines2019.game.Screens.GameScreen;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

/**
 * Created by seeyo on 28.02.2019.
 */

public class SquareItemExpans extends SquareItem {

    public int toughtness;

    public SquareItemExpans(GameScreen gameScreen, int width, int height, Vector2 position) {
        super(gameScreen, width, height, position);
        ballIsTight(true);
//        font = Assets.instance.skinAssets.skin.get("inball-font",BitmapFont.class);
        toughtness = 2;
    }

    @Override
    public void drawToughNumber(SpriteBatch batch) {
        float numberX = getCenterPosition().x ;
        float numberY = getCenterPosition().y ;
        batch.draw(Assets.instance.numberAssets.getNumTexture(toughtness),
                numberX,
                numberY,
                width* Constants.NUMBER_RATIO,
                height* Constants.NUMBER_RATIO);
    }

    public void ballDestroy() {
        if (ballIsTough && toughtness > 1) {
            toughtness--;
        } else {
            toughtness = 0;
        }
    }

    public void setToughtness(int toughtness) {
        this.toughtness = toughtness;
    }

    public int getToughtness() {
        return toughtness;
    }
}
