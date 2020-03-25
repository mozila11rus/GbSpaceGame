package ru.geekbrains.aleksey;
import com.badlogic.gdx.Game;
import ru.geekbrains.aleksey.screen.MenuScreen;

public class SpaceGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
