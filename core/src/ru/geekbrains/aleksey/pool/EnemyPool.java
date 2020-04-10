package ru.geekbrains.aleksey.pool;

import ru.geekbrains.aleksey.base.SpritesPool;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.sprites.EnemyShip;

public class EnemyPool extends SpritesPool <EnemyShip> {
    private BulletPool bulletPool;
    private Rect worldBounds;
    private ExplosionPool explosionPool;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, worldBounds);
    }
}


