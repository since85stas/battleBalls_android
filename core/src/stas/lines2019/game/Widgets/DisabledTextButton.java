package stas.lines2019.game.Widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class DisabledTextButton extends TextButton {

    public DisabledTextButton(String text, Skin skin) {
        super(text, skin);
    }

    public DisabledTextButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    @Override
    public boolean isDisabled() {
        ClickListener listner = getClickListener();
        this.removeListener(listner);
        return super.isDisabled();
    }

    @Override
    public void setDisabled(boolean isDisabled) {
        ClickListener listner = this.getClickListener();
        this.removeListener(listner);
        super.setDisabled(isDisabled);
    }
}
