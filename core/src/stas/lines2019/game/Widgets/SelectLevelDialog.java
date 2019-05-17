package stas.lines2019.game.Widgets;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import stas.lines2019.game.Screens.PuzzleConstrScreen;

/**
 * Created by seeyo on 15.04.2019.
 */

public class SelectLevelDialog extends Dialog {

    PuzzleConstrScreen screen;

    public int WIDTH =(int)( Gdx.graphics.getWidth()*0.6);
    public int HEIGHT =(int)( Gdx.graphics.getHeight()*0.6);

    public SelectLevelDialog(String title, Skin skin, int baseCapacity, PuzzleConstrScreen screen) {
        super(title, skin);
        this.screen = screen;
        Label levels = new Label("lavels:"+baseCapacity,skin,"small");

        SelectBox<String> fieldSelect = new SelectBox<String>(skin);
        fieldSelect.setSize(50,50);
        String[] str = new String[baseCapacity];
        for (int i = 0; i < str.length; i++) {
            str[i] = Integer.toString(i);
        }
        fieldSelect.setItems(str );
        fieldSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("sett","select");
//                String str = soundsSelect.getSelected();
            }
        });
        fieldSelect.setSelected(str[0]);

        Slider slider = new Slider(0,baseCapacity-1, 1,false,skin );
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("sett","select");
            }
        });

        getContentTable().add(levels);
        getContentTable().add(slider);

        button("load",true);
//        stage.addActor(slider);
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
        if (object.equals(true)) {
            screen.setGameFieldConstr();
        }
    }
}
