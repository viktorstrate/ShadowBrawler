package dk.qpqp.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.qpqp.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Game.WIDTH * Game.SCALE;
		config.height = Game.HEIGHT * Game.SCALE;
		config.resizable = true;
		config.title = Game.TITLE;
		new LwjglApplication(new Game(), config);
	}
}
