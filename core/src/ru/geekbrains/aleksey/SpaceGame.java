package ru.geekbrains.aleksey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture backGround;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		backGround = new Texture("space.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backGround,0,0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		backGround.dispose();
	}
}
