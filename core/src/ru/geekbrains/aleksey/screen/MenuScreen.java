package ru.geekbrains.aleksey.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.BaseScreen;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.sprites.Background;
import ru.geekbrains.aleksey.sprites.Spaceship;

public class MenuScreen extends BaseScreen {


    private Texture bg;
    private Background background;
    private Texture spaceShip;
    private Spaceship ship;


    @Override
    public void show() {
        super.show();
        bg = new Texture("spaceBG.jpg");
        spaceShip = new Texture("spaceShip.png");
        try {
            background = new Background(bg);
            ship = new Spaceship(spaceShip);
        } catch (GameException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        spaceShip.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        ship.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        ship.touchDown(touch,pointer,button);
        return false;
    }

    private void update(float delta) {
        ship.update(delta);
    }



    private void draw () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        ship.draw(batch);
        batch.end();
    }
}
