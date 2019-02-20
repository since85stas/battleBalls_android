package stas.lines2019.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by seeyo on 07.02.2019.
 */

public class LibGdxTextureItem extends Actor {

    public Vector2 position;
    public Vector2 velocity;
    public int width;
    public int height;
    public Texture texture;

    public LibGdxTextureItem() {

    }



    public LibGdxTextureItem (Vector2 position, int width, int height) {
        this.position = position;
        this.width    = width;
        this.height   = height;
        setWidth(width);
        setHeight(height);
        setBounds(position.x,position.y,width,height  );
    }

    public void render(Batch batch, float dt) {

    }

    public void update(float dt) {

    }

    public void initTexture(Texture texture) {
        this.texture = texture;

    }

    public void initTexture(int textNumber) {

    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }


}
