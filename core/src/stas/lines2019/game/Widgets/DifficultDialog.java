package stas.lines2019.game.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.util.ArrayList;

import stas.lines2019.game.LinesGame;
import stas.lines2019.game.Screens.MainMenuScreen;
import stas.lines2019.game.util.Assets;
import stas.lines2019.game.util.Constants;

/**
 * Created by seeyo on 14.02.2019.
 */

public class DifficultDialog extends Dialog {

    LinesGame game;
    MainMenuScreen screen;

    public int WIDTH = (int) (Gdx.graphics.getWidth() * 0.95);
    public int HEIGHT = (int) (Gdx.graphics.getHeight() * 0.6);

    final DisabledTextButton normalButton;
    final DisabledTextButton hardButton;
    final DisabledTextButton nightmareButton;
    final DisabledTextButton endlessButton;


    public DifficultDialog(String title, Skin skin, final LinesGame game, MainMenuScreen screen) {
        super(title, skin);
        this.screen = screen;
        String styleName = "small";
        TextButton.TextButtonStyle style =
                Assets.instance.skinAssets.skin.get(styleName, TextButton.TextButtonStyle.class);
        float size = style.font.getCapHeight();
        HEIGHT = (int)(6*size*(Constants.NUM_DIFFICULTIES+1));
        this.game = game;
        VerticalGroup group = new VerticalGroup().pad(size).top().space(size );

        DisabledTextButton easyButton = new DisabledTextButton(Assets.instance.bundle.get("easyB"),
                Assets.instance.skinAssets.skin,
                styleName);

        final ArrayList<DisabledTextButton> diffButtonsList = new ArrayList<DisabledTextButton>();

        easyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("dial", "easy mode");
                game.setGameSurvivScreen(Constants.DIFFICULT_EASY);
                hide();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        group.addActor(easyButton);
        diffButtonsList.add(easyButton);

        normalButton = new DisabledTextButton(Assets.instance.bundle.get("normalB"),
                Assets.instance.skinAssets.skin,
                styleName);
//        normalButton.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                Gdx.app.log("dial", "easy mode");
//                game.setGameSurvivScreen(Constants.DIFFICULT_NORMAL);
//                hide();
//                return super.touchDown(event, x, y, pointer, button);
//            }
//        });

        boolean bol = normalButton.isDisabled();
        Gdx.app.log("dial", "normal mode");
        ;
        group.addActor(normalButton);
        diffButtonsList.add(normalButton);

        hardButton = new DisabledTextButton(Assets.instance.bundle.get("hardB"),
                Assets.instance.skinAssets.skin,
                styleName);

        group.addActor(hardButton);
        diffButtonsList.add(hardButton);

        nightmareButton = new DisabledTextButton(Assets.instance.bundle.get("nightB"),
                Assets.instance.skinAssets.skin,
                styleName);

        group.addActor(nightmareButton);
        diffButtonsList.add(nightmareButton);

        endlessButton = new DisabledTextButton(Assets.instance.bundle.get("endlessB"),
                Assets.instance.skinAssets.skin,
                styleName);

        group.addActor(endlessButton);
        diffButtonsList.add(endlessButton);

        boolean[] buttonsIsDis = new boolean[diffButtonsList.size()];
        for (int i = 1; i < diffButtonsList.size(); i++) {

            boolean bool = game.survLevelIsComp[i - 1];
            if (!game.survLevelIsComp[i - 1]) {
                Gdx.app.log("dial", "set");
                diffButtonsList.get(i).setDisabled(true);
                buttonsIsDis[i] = true;
            } else {
                addListner(getDiffType(i));
            }
        }

        TextButton cancelButton = new TextButton(Assets.instance.bundle.get("cancelsB"),
                Assets.instance.skinAssets.skin,
                styleName);
        cancelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                Gdx.app.log("dial", "endless mode");
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        group.addActor(cancelButton);

        getButtonTable().defaults().center().top();
        getButtonTable().add(group);
    }

    void addListner(int diffType) {
        switch (diffType) {
            case Constants.DIFFICULT_NORMAL:
                normalButton.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.log("dial", "normal mode");
                        if (true) {
                            game.setGameSurvivScreen(Constants.DIFFICULT_NORMAL);
                            hide();
                        } else {
//                            screen.showStarBuyDialog(Constants.SURV_MODE_COST,);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
                break;
            case Constants.DIFFICULT_HARD:
                hardButton.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.log("dial", "hard mode");
                        if (game.friendGameIsBought || game.hadrModeisBought) {
                            game.setGameSurvivScreen(Constants.DIFFICULT_HARD);
                            hide();
                        }else {
                            screen.showStarBuyDialog(Constants.SURV_MODE_COST, Constants.DIFFICULT_HARD_BOUGHT);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
                break;
            case Constants.DIFFICULT_NIGHTMARE:
                nightmareButton.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.log("dial", "nightmare mode");
                        if (game.friendGameIsBought || game.nightmareModeisBought) {
                            game.setGameSurvivScreen(Constants.DIFFICULT_NIGHTMARE);
                            hide();
                        }else {
                            screen.showStarBuyDialog(Constants.SURV_MODE_COST,
                                    Constants.DIFFICULT_NIGHTMARE_BOUGHT);
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
                break;
            case Constants.DIFFICULT_ENDLESS:
                endlessButton.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (game.friendGameIsBought) {
                            game.setGameSurvivScreen(Constants.DIFFICULT_ENDLESS);
                            hide();
                        }else {
                            screen.showStarBuyDialog(Constants.SURV_MODE_COST,
                                    Constants.DIFFICULT_NIGHTMARE_BOUGHT);
                        }
                        Gdx.app.log("dial", "endless mode");
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
                break;
        }
    }



    int getDiffType(int i) {
        int[] diifArray = new int[Constants.NUM_DIFFICULTIES];
        diifArray[0] = Constants.DIFFICULT_EASY;
        diifArray[1] = Constants.DIFFICULT_NORMAL;
        diifArray[2] = Constants.DIFFICULT_HARD;
        diifArray[3] = Constants.DIFFICULT_NIGHTMARE;
        diifArray[4] = Constants.DIFFICULT_ENDLESS;
        return diifArray[i];
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
        } else if (object.equals(false)) {
//            game.getGameScreen().gameField.setInputProccActive(true);
        }
    }

    @Override
    public void hide() {
        super.hide();
    }
}
