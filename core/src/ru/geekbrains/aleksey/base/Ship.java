package ru.geekbrains.aleksey.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.pool.BulletPool;
import ru.geekbrains.aleksey.pool.ExplosionPool;
import ru.geekbrains.aleksey.sprites.Bullet;
import ru.geekbrains.aleksey.sprites.Explosion;

public abstract class  Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    private static final float DELTA_COEFF = 1.2f;
    private float saveDelta = 0f;


    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;
    protected Sound shootSound;
    protected int hp;
    protected  Vector2 v;
    protected  Vector2 v0;
    protected Vector2 bulletPos;
    protected  float reloadTimer ;
    protected float reloadInterval;
    protected float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    protected ExplosionPool explosionPool;

    public Ship (){

    }

    public Ship(TextureRegion region, int rows, int cols, int frames) throws GameException {
        super(region, rows, cols, frames);
    }

    public Vector2 getV() {
        return v;
    }

    public int getHp() {
        return hp;
    }

    @Override
    public void update(float delta) {
        if (saveDelta == 0f) {
            saveDelta = delta;
        }
        if (delta > saveDelta * DELTA_COEFF) {
            delta = saveDelta;
        }
        pos.mulAdd(v,delta);
        damageAnimateTimer += delta;
        if(damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval && getTop() <= worldBounds.getTop() ) {
            reloadTimer = 0;
                shoot();
        }
    }

    public void damage (int damage) {
        damageAnimateTimer = 0f;
        frame = 1;
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
            destroy();
        }
    }


    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public int getDamage() {
        return damage;
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
        shootSound.play();
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.setExplosion(pos, getHeight());
    }
}
