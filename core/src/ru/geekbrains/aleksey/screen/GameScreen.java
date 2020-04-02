package ru.geekbrains.aleksey.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.BaseScreen;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.sprites.Background;
import ru.geekbrains.aleksey.sprites.SpaceShip;
import ru.geekbrains.aleksey.sprites.Star;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private static final int STAR_COUNT = 64;
    private Star[] stars;
    private TextureAtlas atlas;
    private SpaceShip ship;


    @Override
    public void render(float delta) {
      update(delta);
      draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ship.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        System.out.println("down " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
       ship.keyUp(keycode);
        System.out.println("up " + keycode);
       return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        ship.touchDown(touch, pointer, button);
        return false;

    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("spaceBG.jpg");
        atlas = new TextureAtlas(Gdx.files.internal("textures/game.pack"));
        try {
            background = new Background(bg);
            stars = new Star[STAR_COUNT];
            for(int i = 0; i < STAR_COUNT; i++) {
                stars[i] = new Star(atlas);
            }
            ship = new SpaceShip(atlas);
        } catch (GameException e) {
            e.printStackTrace();
        }
    }

    private void update (float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        ship.update(delta);
    }

    private void draw () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        ship.draw(batch);
        batch.end();
    }
}
