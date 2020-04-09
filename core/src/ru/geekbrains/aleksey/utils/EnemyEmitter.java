package ru.geekbrains.aleksey.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.math.Rnd;
import ru.geekbrains.aleksey.pool.EnemyPool;
import ru.geekbrains.aleksey.sprites.EnemyShip;

public class EnemyEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE =1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.2f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_MEDIUM_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.25f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_BIG_HP = 10;

    private Rect worldBounds;
    private Sound shootSound;
    private TextureRegion bulletRegion;

    private float generateInterval = 4f;
    private float generateTimer;

    private final TextureRegion[] enemySmallRegion;
    private final TextureRegion[] enemyMediumRegion;
    private final TextureRegion[] enemyBigRegion;
    private final Vector2 enemySmallV;
    private final Vector2 enemyMediumV;
    private final Vector2 enemyBigV;
    private final EnemyPool enemyPool;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds, Sound shootSound) {
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.enemyPool = enemyPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        TextureRegion enemy0 = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(enemy0,1,2,2);
        TextureRegion enemy1 = atlas.findRegion("enemy1");
        this.enemyMediumRegion = Regions.split(enemy1,1,2,2);
        TextureRegion enemy2 = atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(enemy2,1,2,2);
        this.enemySmallV = new Vector2(0,-0.15f);
        this.enemyMediumV = new Vector2(0,-0.01f);
        this.enemyBigV = new Vector2(0,-0.005f);
    }

    public void generate (float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.6f) {
                enemyShip.setEnemyShipParameters(enemySmallRegion, enemySmallV, bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT, ENEMY_SMALL_BULLET_VY, ENEMY_SMALL_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL, shootSound, ENEMY_SMALL_HEIGHT, ENEMY_SMALL_HP);
            } else if (type < 0.85f) {
                enemyShip.setEnemyShipParameters(enemyMediumRegion, enemyMediumV, bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT, ENEMY_MEDIUM_BULLET_VY, ENEMY_MEDIUM_DAMAGE,
                        ENEMY_MEDIUM_RELOAD_INTERVAL, shootSound, ENEMY_MEDIUM_HEIGHT, ENEMY_MEDIUM_HP);
            }  else {
                enemyShip.setEnemyShipParameters(enemyBigRegion, enemyBigV, bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT, ENEMY_BIG_BULLET_VY, ENEMY_BIG_DAMAGE,
                        ENEMY_BIG_RELOAD_INTERVAL, shootSound, ENEMY_BIG_HEIGHT, ENEMY_BIG_HP);
            }
            enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemyShip.getHalfWidth(), worldBounds.getRight() - enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop());
        }
    }
}
