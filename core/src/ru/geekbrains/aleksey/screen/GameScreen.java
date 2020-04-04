package ru.geekbrains.aleksey.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.BaseScreen;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.pool.BulletPool;
import ru.geekbrains.aleksey.sprites.Background;
import ru.geekbrains.aleksey.sprites.MainShip;
import ru.geekbrains.aleksey.sprites.Star;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private static final int STAR_COUNT = 64;
    private Star[] stars;
    private TextureAtlas atlas;
    private TextureAtlas atlas2;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private Music music;

    @Override
    public void show() {
        super.show();
        bg = new Texture("spaceBG.jpg");
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        atlas2 = new TextureAtlas(Gdx.files.internal("textures/gameAtlas.pack"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainScreenMusic.mp3"));
        music.play();
        bulletPool = new BulletPool();
        try {
            background = new Background(bg);
            stars = new Star[STAR_COUNT];
            for(int i = 0; i < STAR_COUNT; i++) {
                stars[i] = new Star(atlas2);
            }
            mainShip = new MainShip(atlas, bulletPool);
        } catch (GameException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void render(float delta) {
      update(delta);
      freeAllDestroyed();
      draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        mainShip.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        music.dispose();
        mainShip.dispose();
        super.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
       mainShip.keyUp(keycode);
       return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;

    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }



    private void update (float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
    }

    public void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
    }

    private void draw () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }
}
