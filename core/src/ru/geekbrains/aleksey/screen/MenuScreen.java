package ru.geekbrains.aleksey.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private static final float V_LEN = 1.0f;

    private Texture background;
    private Texture spaceShip;
    private Vector2 pos;
    private Vector2 touch;
//    private Vector2 v;
//    private Vector2 tmp;


    @Override
    public void show() {
        super.show();
        background = new Texture("space.jpg");
        spaceShip = new Texture("spaceShip.png");
        pos = new Vector2(0,0);
        touch = new Vector2();
//        v = new Vector2();
//        tmp = new Vector2();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        spaceShip.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight()-screenY);
//        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }

    private void update(float delta) {
//        tmp.set(touch);
//        float remainDistance = (tmp.sub(pos).len());
//        if(remainDistance > V_LEN) {
//            pos.add(v);
//        } else {
//            v.setZero();
//            pos.set(touch);
//        }
    }

    private void draw () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, -1f, -1f,2f,2f);
        batch.draw(spaceShip,pos.x,pos.y,0.3f,0.3f);
        batch.end();
    }
}