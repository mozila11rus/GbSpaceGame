package ru.geekbrains.aleksey.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import ru.geekbrains.aleksey.base.Sprite;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) throws GameException {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setTop(0.2f);
    }
}
