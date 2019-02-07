package stas.lines2019.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import stas.lines2019.game.util.Assets;

public class Background {

    int fieldDimension ;
    Vector2 position;
    int width;
    int height;

    public Background (Vector2 position, int width, int height, int fieldSize) {
        fieldDimension = fieldSize;
        this.position  = position;
        this.width     = width;
        this.height    = height;
    }

    public void render(SpriteBatch batch) {
        int itemWidth = width/fieldDimension;
        for (int i = 0; i < fieldDimension ; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                int x = (int)position.x + j * itemWidth;
                int y = (int)position.y + i * itemWidth;

                batch.draw(Assets.instance.tileAssets.texture,
                        x,
                        y,
                        itemWidth,
                        itemWidth);
            }
        }
    }

}
