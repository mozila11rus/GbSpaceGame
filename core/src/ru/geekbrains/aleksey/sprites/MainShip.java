package ru.geekbrains.aleksey.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.Ship;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.pool.BulletPool;


public class MainShip extends Ship {
    private static final float SHIP_HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.02f;
    private static final int INVALID_POINTER = -1;

    private Sound sound;

    private boolean pressedLeft;
    private boolean pressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool) throws GameException {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletV = new Vector2(0, 0.5f);
        vHorizon = new Vector2(0.3f,0f);
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_HEIGHT);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if(pressedLeft) {
                    moveLeft();
                }else {
                    stop();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
           rightPointer = INVALID_POINTER;
           if (leftPointer != INVALID_POINTER) {
               moveLeft();
           } else {
               stop();
           }
        }
        return false;
    }

    @Override
    public void update(float delta) {
       pos.mulAdd(v,delta);
       reloadTimer += delta;
       if (getLeft() < worldBounds.getLeft()) {
           setLeft(worldBounds.getLeft());
           stop();
       }
       if (getRight() > worldBounds.getRight()) {
           setRight(worldBounds.getRight());
           stop();
       }
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0;
            shoot();
            sound.play(0.5f);
        }
    }

    private void moveRight() {
        v.set(vHorizon);
    }

    private void moveLeft() {
        v.set(vHorizon).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    public void dispose () {
        sound.dispose();
    }


}
