package ru.geekbrains.aleksey.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.pool.BulletPool;
import ru.geekbrains.aleksey.sprites.Bullet;

public abstract class Ship extends Sprite {

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;

    protected  Vector2 v;
    protected  Vector2 vHorizon;

    protected float reloadInterval = 0.25f;
    protected  float reloadTimer ;

    public Ship(TextureRegion region, int rows, int cols, int frames) throws GameException {
        super(region, rows, cols, frames);
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1);
    }
}
