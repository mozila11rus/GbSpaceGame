package ru.geekbrains.aleksey.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.Sprite;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;

public class Spaceship extends Sprite {
    private static final float V_LEN = 0.01f;

    private Vector2 v;
    private Vector2 tmp;
    private Vector2 touch;


    public Spaceship (Texture texture) throws GameException {
        super(new TextureRegion(texture));
        v = new Vector2();
        tmp = new Vector2();
        touch = new Vector2();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
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
    }
}
