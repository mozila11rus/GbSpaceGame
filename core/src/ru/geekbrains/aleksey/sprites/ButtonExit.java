package ru.geekbrains.aleksey.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.aleksey.base.ScaledButton;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;

public class ButtonExit extends ScaledButton {
    public ButtonExit(TextureAtlas atlas) throws GameException {
        super(atlas.findRegion("btnExit"));
    }


    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.17f);
        pos.x = 0;
//        setRight(worldBounds.getRight() - 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
