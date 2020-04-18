package ru.geekbrains.aleksey.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.aleksey.base.BaseScreen;
import ru.geekbrains.aleksey.base.Font;
import ru.geekbrains.aleksey.base.Ship;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.pool.BulletPool;
import ru.geekbrains.aleksey.pool.EnemyPool;
import ru.geekbrains.aleksey.pool.ExplosionPool;
import ru.geekbrains.aleksey.sprites.Background;
import ru.geekbrains.aleksey.sprites.Bullet;
import ru.geekbrains.aleksey.sprites.ButtonExit;
import ru.geekbrains.aleksey.sprites.ButtonNewGame;
import ru.geekbrains.aleksey.sprites.EnemyShip;
import ru.geekbrains.aleksey.sprites.GameOver;
import ru.geekbrains.aleksey.sprites.MainShip;
import ru.geekbrains.aleksey.sprites.Star;
import ru.geekbrains.aleksey.sprites.TrackStar;
import ru.geekbrains.aleksey.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private static final int STAR_COUNT = 64;
    private static final float FONT_SIZE = 0.02f;
    private static final float FONT_MARGIN = 0.01f;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private Texture bg;
    private Background background;
    private TrackStar[] stars;
    private TextureAtlas atlas;
    private TextureAtlas atlas2;
    private TextureAtlas atlas3;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private Music music;
    private EnemyPool enemyPool;
    private Sound laserSound;
    private Sound bulletSound;
    private EnemyEmitter enemyEmitter;
    private ExplosionPool explosionPool;
    private Sound explosion;
    private State state;
    private GameOver gameOver;
    private ButtonNewGame buttonNewGame;
    private State prevState;
    private int frags;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    @Override
    public void show() {
        super.show();
        bg = new Texture("spaceBG.jpg");
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        atlas2 = new TextureAtlas(Gdx.files.internal("textures/gameAtlas.pack"));
        atlas3 = new TextureAtlas(Gdx.files.internal("textures/goAtlas.pack"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainScreenMusic.mp3"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        music.setLooping(true);
        music.play();
        state = State.PLAYING;
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosion);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyEmitter = new EnemyEmitter(atlas2, enemyPool, worldBounds, bulletSound);
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        prevState = State.PLAYING;
        frags = 0;
        try {
            background = new Background(bg);
            stars = new TrackStar[STAR_COUNT];
            mainShip = new MainShip(atlas2, bulletPool, explosionPool, laserSound);
            for(int i = 0; i < STAR_COUNT; i++) {
                stars[i] = new TrackStar (atlas2, mainShip.getV());
            }
            gameOver = new GameOver(atlas3);
            buttonNewGame = new ButtonNewGame(atlas3, this);
        } catch (GameException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        state = State.PLAYING;
        mainShip.resetShip();
        enemyPool.freeAllActiveObjects();
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        frags = 0;
    }

    @Override
    public void pause() {
        prevState = state;
        state =State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
       state = prevState;
       music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
      update(delta);
      checkCollisions();
      freeAllDestroyed();
      draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        atlas3.dispose();
        bulletPool.dispose();
        music.dispose();
        enemyPool.dispose();
        laserSound.dispose();
        enemyPool.dispose();
        explosion.dispose();
        bulletSound.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
       return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch,pointer,button);
        }
        return false;

    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    private void update (float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
            } else if (state == State.GAME_OVER) {
            buttonNewGame.update(delta);
            }
        }

        private void checkCollisions () {

        if (state != State.PLAYING) {
            return;
        }
            List <EnemyShip> enemyList = enemyPool.getActiveObjects();
            List <Bullet> bulletList = bulletPool.getActiveObjects();
            for (EnemyShip enemyShip : enemyList) {
                if (enemyShip.isDestroyed()) {
                    continue;
                }
                float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
                if (mainShip.pos.dst(enemyShip.pos) < minDist) {
                    enemyShip.destroy();
                    frags ++;
                    mainShip.damage(enemyShip.getDamage());
                }

                for (Bullet bullet : bulletList) {
                    if (bullet.getOwner() != mainShip || bullet.isDestroyed() ) {
                        continue;
                    }
                    if (enemyShip.isBulletCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage());
                        bullet.destroy();
                        if (enemyShip.isDestroyed()) {
                            frags++;
                        }
                    }
                }
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
            if (mainShip.isDestroyed()) {
                state = State.GAME_OVER;
            }

        }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();

    }

    private void draw () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
       switch (state) {
           case PLAYING:
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            break;
           case GAME_OVER:
               gameOver.draw(batch);
               buttonNewGame.draw(batch);
               break;
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo () {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + FONT_MARGIN, worldBounds.getTop() - FONT_MARGIN);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - FONT_MARGIN, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - FONT_MARGIN, worldBounds.getTop() - FONT_MARGIN, Align.right);

    }

}
