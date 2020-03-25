package ru.geekbrains.aleksey.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.aleksey.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture background;
    private Texture spaceShip;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 pos2;

    @Override
    public void show() {
        super.show();
        background = new Texture("space.jpg");
        spaceShip = new Texture("spaceShip.png");
        pos = new Vector2(0,0);
        pos2 = new Vector2();
        v = new Vector2();
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
        pos2.set(screenX - spaceShip.getWidth()/2, (Gdx.graphics.getHeight()-screenY) - spaceShip.getHeight()/2);
        System.out.println(pos2);
        v.set(pos2.cpy().sub(pos));
        v.nor().scl(2.0f);
        return false;
    }

    private void update(float delta) {
        if(pos2.len() - pos.len() > -1.0f && pos2.len() - pos.len() < 1.0f) {
            v.set(0.0f,0.0f);
        } else {
            pos.add(v);
        }
    }

    private void draw () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(spaceShip,pos.x,pos.y);
        batch.end();
    }
}