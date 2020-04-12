package ru.geekbrains.aleksey.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.aleksey.base.ScaledButton;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private GameScreen gameScreen = new GameScreen();

    public ButtonNewGame(TextureAtlas atlas) throws GameException {
        super(atlas.findRegion("button_new_game"));
    }


    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.07f);
        setTop(-0.01f);
    }

    @Override
    public void action() {
       gameScreen.reset();
    }

}

