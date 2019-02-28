package stas.lines2019.game.Screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.gameFields.GameFieldExpans;

/**
 * Created by seeyo on 28.02.2019.
 */

public class GameScreenExpans extends GameScreen {
    public GameScreenExpans(LinesGame lineGame, SpriteBatch batch) {
        super(lineGame, batch);


    }


    @Override
    public void setGameField() {
        gameField = new GameFieldExpans(this);
    }
}
