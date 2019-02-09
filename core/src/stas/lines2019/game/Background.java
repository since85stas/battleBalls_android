package stas.lines2019.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import stas.lines2019.game.util.Assets;

public class Background {

    int fieldDimension ;
    Vector2 position;
    int width;
    int height;
    int groundType;

    public Background (Vector2 position, int width, int height, int fieldSize) {
        fieldDimension = fieldSize;
        this.position  = position;
        this.width     = width;
        this.height    = height;
        groundType = MathUtils.random(0,1);
    }

    public void render(SpriteBatch batch) {
        int itemWidth = width/fieldDimension;
        for (int i = 0; i < fieldDimension ; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                int x = (int)position.x + j * itemWidth;
                int y = (int)position.y + i * itemWidth;

                batch.draw(Assets.instance.tileAssets.getTexture(groundType),
                        x,
                        y,
                        itemWidth,
                        itemWidth);
            }
        }
    }

}
