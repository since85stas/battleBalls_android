package stas.lines2019.game.Entities;

import java.util.ArrayList;

public class GameFieldsFactory {

    private ArrayList<GameFieldClass> gameFieldClass;

    public GameFieldClass createField(int id) {

        GameFieldClass p = gameFieldClass.get(id);

        GameFieldClass m = new GameFieldClass(p.name, p.dimX, p.dimY, p.gameField);
        //...other code to add behaviours

        return m;
    }
}
