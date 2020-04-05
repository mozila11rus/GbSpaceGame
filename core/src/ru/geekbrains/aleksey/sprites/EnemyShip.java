package ru.geekbrains.aleksey.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.Sprite;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.math.Rnd;
import ru.geekbrains.aleksey.utils.Regions;


public class EnemyShip extends Sprite {
    private Rect worldBounds;

    private static final float SHIP_HEIGHT = 0.2f;
    private Vector2 v;



    public EnemyShip () {
       regions = new TextureRegion[1];
    }

    public void setEnemyShipParameters (TextureRegion region, Rect worldBounds) {
        this.worldBounds = worldBounds;
        regions = Regions.split(region,1,2,2);
        float vx = 0;
        float vy = Rnd.nextFloat(-0.05f, -0.1f);
        v = new Vector2(vx, vy);
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = worldBounds.getTop() + getHalfHeight();
        this.pos.set(posX, posY);
        setHeightProportion(SHIP_HEIGHT);
    }


    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }
}
