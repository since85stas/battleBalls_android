package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.util.ArrayList;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

/**
 * Created by seeyo on 14.02.2019.
 */

public class DifficultDialog extends Dialog {

    LinesGame game;

    public int WIDTH =(int)( Gdx.graphics.getWidth()*0.95);
    public int HEIGHT =(int)( Gdx.graphics.getHeight()*0.5);

    public DifficultDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public DifficultDialog(String title, Skin skin, final LinesGame game) {
        super(title, skin);
        String styleName = "small";
        TextButton.TextButtonStyle style =
                Assets.instance.skinAssets.skin.get(styleName, TextButton.TextButtonStyle.class);
        float size = style.font.getCapHeight();
        this.game = game;
        VerticalGroup group = new VerticalGroup().pad(size).space(size/2);

        TextButton easyButton = new TextButton("easy",
                Assets.instance.skinAssets.skin,
                styleName);

        final ArrayList<TextButton> diffButtonsList = new ArrayList<TextButton>();
        easyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("dial","easy mode");
                game.setGameSurvivScreen(Constants.DIFFICULT_EASY);
                hide();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        group.addActor(easyButton);
        diffButtonsList.add(easyButton);

        final TextButton normalButton = new TextButton("normal",
                Assets.instance.skinAssets.skin,
                styleName);
        normalButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("dial","normal mode");
                game.setGameSurvivScreen(Constants.DIFFICULT_NORMAL);
                hide();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        group.addActor(normalButton);
        diffButtonsList.add(normalButton);

        TextButton hardButton = new TextButton("hard",
                Assets.instance.skinAssets.skin,
                styleName);
        hardButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("dial","hard mode");
                game.setGameSurvivScreen(Constants.DIFFICULT_HARD);
                hide();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        group.addActor(hardButton);
        diffButtonsList.add(hardButton);

        TextButton nightmareButton = new TextButton("nightmare",
                Assets.instance.skinAssets.skin,
                styleName);
        nightmareButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("dial","nightmare mode");
                game.setGameSurvivScreen(Constants.DIFFICULT_NIGHTMARE);
                hide();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        group.addActor(nightmareButton);
        diffButtonsList.add(nightmareButton);

        TextButton endlessButton = new TextButton("endless",
                Assets.instance.skinAssets.skin,
                styleName);
        endlessButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("dial","endless mode");
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        group.addActor(endlessButton);
        diffButtonsList.add(endlessButton);

        for (int i = 1; i < diffButtonsList.size(); i++) {

            if(!game.survLevelIsComp[i-1]) {
                diffButtonsList.get(i).setDisabled(true);

            }
        }

        TextButton cancelButton = new TextButton("cancel",
                Assets.instance.skinAssets.skin,
                styleName);
        cancelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                Gdx.app.log("dial","endless mode");
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        group.addActor(cancelButton);

        getButtonTable().defaults().center().top();
        getButtonTable().add(group);
    }

    @Override
    public float getPrefWidth() {
        return WIDTH;
    }

    @Override
    public float getPrefHeight() {
        return HEIGHT;
    }

    @Override
    protected void result(Object object) {
        Gdx.app.log("dia", object.toString());
        if (object.equals(true)) {
//            game.setMainMenuScreen();
        } else if (object.equals(false)){
//            game.getGameScreen().gameField.setInputProccActive(true);
        }
    }

    @Override
    public void hide() {
        super.hide();
    }
}
