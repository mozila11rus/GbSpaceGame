package ru.geekbrains.aleksey.pool;

import ru.geekbrains.aleksey.base.SpritesPool;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.sprites.EnemyShip;

public class EnemyPool extends SpritesPool <EnemyShip> {
    private BulletPool bulletPool;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds);
    }
}


