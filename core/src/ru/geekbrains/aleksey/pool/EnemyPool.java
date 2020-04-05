package ru.geekbrains.aleksey.pool;

import ru.geekbrains.aleksey.base.SpritesPool;
import ru.geekbrains.aleksey.sprites.EnemyShip;

public class EnemyPool extends SpritesPool <EnemyShip> {

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip();
    }
}


