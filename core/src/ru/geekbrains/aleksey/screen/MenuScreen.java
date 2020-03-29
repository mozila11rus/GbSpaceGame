package ru.geekbrains.aleksey.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.BaseScreen;
import ru.geekbrains.aleksey.exception.GameException;
import ru.geekbrains.aleksey.math.Rect;
import ru.geekbrains.aleksey.sprites.Background;

public class MenuScreen extends BaseScreen {
    private static final float V_LEN = 1.0f;

    private Texture bg;
    private Background background;
    private Texture spaceShip;
    private Vector2 pos;

//    private Vector2 v;
//    private Vector2 tmp;


    @Override
    public void show() {
        super.show();
        bg = new Texture("spaceBG.jpg");
        spaceShip = new Texture("spaceShip.png");
        try {
            background = new Background(bg);
        } catch (GameException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        pos = new Vector2(0,0);
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
        bg.dispose();
        spaceShip.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        pos.set(touch);
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
        background.draw(batch);
        batch.draw(spaceShip,pos.x,pos.y,0.3f,0.3f);
        batch.end();
    }
}
