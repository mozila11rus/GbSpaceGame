package ru.geekbrains.aleksey.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.aleksey.base.Ship;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.pool.BulletPool;
import ru.geekbrains.aleksey.pool.ExplosionPool;


public class EnemyShip extends Ship {
    private final Vector2 enemyV = new Vector2(0, -0.5f);


    public EnemyShip (BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        v = new Vector2();
        v0 = new Vector2();
        bulletV = new Vector2();
    }

    public void setEnemyShipParameters (TextureRegion[] regions, Vector2 v0,
                                       TextureRegion bulletRegion, float bulletHeight,
                                       float bulletVY, int damage, float reloadInterval,
                                       Sound shootSound, float height, int hp) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0,bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.shootSound = shootSound;
        this.hp = hp;
        this.v.set(v0);
        bulletPos = new Vector2();
        setHeightProportion(height);

    }
    private void fightMode () {
            if (getTop() > worldBounds.getTop()) {
                v.set(enemyV);
            } else {
                v.set(v0);
            }
        }



    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y - getHalfHeight());
        fightMode();
    if (getBottom() <= worldBounds.getBottom()){
        destroy();
        }
    }

    public boolean isBulletCollision (Rect bullet) {
        return ! (bullet.getRight() < getLeft() || bullet.getLeft() > getRight() ||
                bullet.getBottom() > getTop() || bullet.getTop() < pos.y);
    }
}
