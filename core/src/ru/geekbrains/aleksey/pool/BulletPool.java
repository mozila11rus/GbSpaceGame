package ru.geekbrains.aleksey.pool;

import ru.geekbrains.aleksey.base.Sprite;
import ru.geekbrains.aleksey.base.SpritesPool;
import ru.geekbrains.aleksey.sprites.Bullet;

public class BulletPool extends SpritesPool <Bullet> {


    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
