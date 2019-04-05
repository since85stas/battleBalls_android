package stas.lines2019.game.Entities;

import com.badlogic.gdx.math.Vector2;

public class GameFieldClass {
    public String name;
    public int dimX;
    public int dimY;
    public String gameField;

    public GameFieldClass () {

    }

    public GameFieldClass ( String name, int dimX, int dimY, String gameField ) {
        this.name = name;
        this.dimX = dimX;
        this.dimY = dimY;
        this.gameField = gameField;
    }
}
