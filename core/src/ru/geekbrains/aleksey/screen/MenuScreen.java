package ru.geekbrains.aleksey.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.BaseScreen;
import ru.geekbrains.aleksey.base.Sprite;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.sprites.Background;
import ru.geekbrains.aleksey.sprites.ButtonExit;
import ru.geekbrains.aleksey.sprites.ButtonPlay;
import ru.geekbrains.aleksey.sprites.Logo;
import ru.geekbrains.aleksey.sprites.Star;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Logo logo;
    private Background background;
    private TextureAtlas atlas;
    private static final int STAR_COUNT = 128;
    private Star [] stars;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private final Game game;
    private Music music;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("spaceBG.jpg");
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.pack"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        try {
            background = new Background(bg);
            stars = new Star[STAR_COUNT];
            for(int i = 0; i < STAR_COUNT; i++) {
                stars[i] = new Star(atlas);
            }
            buttonExit = new ButtonExit(atlas);
            buttonPlay = new ButtonPlay(atlas, game);
            logo = new Logo(atlas);
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
        atlas.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }



    private void draw () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        logo.draw(batch);
        batch.end();
    }
}
