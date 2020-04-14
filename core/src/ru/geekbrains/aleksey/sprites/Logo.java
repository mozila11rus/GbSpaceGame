package ru.geekbrains.aleksey.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.aleksey.base.Sprite;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;

public class Logo extends Sprite {
    public Logo (TextureAtlas atlas) throws GameException {
        super(atlas.findRegion("logo"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.3f);
        setTop(0.3f);
    }

}
