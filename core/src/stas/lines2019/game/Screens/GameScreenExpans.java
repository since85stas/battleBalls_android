package stas.lines2019.game.Screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.gameFields.GameFieldExpans;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

import static stas.lines2019.game.util.Constants.*;

/**
 * Created by seeyo on 28.02.2019.
 */

public class GameScreenExpans extends GameScreen {

    public GameScreenExpans(LinesGame lineGame, SpriteBatch batch) {
        super(lineGame, batch);
    }

    @Override
    public void setGameField() {
//        drawEnergy();
//        stage.addActor(energyLable);
        isRenderGamefield = true;
        gameField = new GameFieldExpans(this);
    }

    @Override
    public void setExpansionPlay() {
        isExpansionPlayed = true;
    }

    @Override
    public String setRulesLable() {
        return Assets.instance.bundle.get("rulesExpansText");
    }


}
