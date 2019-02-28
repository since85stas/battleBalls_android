package stas.lines2019.game.balls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import stas.lines2019.game.Screens.GameScreen;

public class Ball {
    private GameScreen gameScreen;
    private Texture texture;

    public Vector2 position;

    // ball dimensions
    private final int WIDTH = 100;
    private final int HEIGHT = 100;

    public Ball (GameScreen gameScreen){
        this.gameScreen = gameScreen;
        //texture = new Texture("sphere_blue.png");
        this.position = new Vector2(0,0);
    }


}
