package ru.geekbrains.aleksey.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.Sprite;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;

public class SpaceShip extends Sprite {
    private static final float V_LEN = 0.008f;

    private Vector2 v;
    private Vector2 tmp;
    private Vector2 touch;
    private int keycode;
    private final Vector2 vHorizon = new Vector2(0.01f,0f);
    private final Vector2 vVertical = new Vector2(0f,0.01f);


    public SpaceShip (TextureAtlas atlas) throws GameException {
        super(atlas.findRegion("ship"));
        v = new Vector2();
        tmp = new Vector2();
        touch = new Vector2();
    }
    public boolean keyDown(int keycode) {
        this.keycode = keycode;
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + 0.02f);
    }


    @Override
    public void update(float delta) {
        tmp.set(touch);
        float remainDistance = (tmp.sub(pos).len());
        if(remainDistance > V_LEN) {
            pos.add(v);
        } else {
            v.setZero();
            pos.set(touch);
        }

        if (keycode == 21) {
            pos.sub(vHorizon);
        }

    }
}
