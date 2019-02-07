package stas.lines2019.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import stas.lines2019.game.Entities.LibGdxTextureItem;

/**
 * Created by seeyo on 07.02.2019.
 */

public class MenuBall extends LibGdxTextureItem {


    public MenuBall() {
        super();
    }

    public MenuBall(Vector2 position, int width, int height) {
        super(position, width, height);
//        setWidth(width);
//        setHeight(height);
//        setBounds(position.x,position.y,width,height  );

    }

    @Override
    public void render(Batch batch, float dt) {

        if (texture != null) {
            batch.draw(texture,position.x,position.y,width,height );
        }
    }

    @Override
    public void update(float dt) {
        if (velocity != null) {
            position.x += velocity.x*dt;
            position.y += velocity.y*dt;
            setBounds(position.x,position.y,width,height);
        }
    }


}
