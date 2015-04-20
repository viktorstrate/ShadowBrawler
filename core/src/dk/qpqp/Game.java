package dk.qpqp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.states.GameState;
import dk.qpqp.states.GameStateManager;
import dk.qpqp.utils.Content;
import dk.qpqp.utils.ControllerListener;

/**
 * The Game class is the main class.
 */

public class Game extends ApplicationAdapter {

	public static final String TITLE = "Fighting Game";
	public static final int WIDTH = 640;
	public static final int HEIGHT = 360;
	public static final int SCALE = 2;

	private SpriteBatch batch;
	private GameStateManager gsm;
	private ControllerListener controllerListener;

	protected static Content res;

	@Override
	public void create () {
		batch = new SpriteBatch();

		// Load resources
		// Background
		Content.loadTexture("bg/back.png", "bg_back");
		Content.loadTexture("bg/middle.png", "bg_middle");
		Content.loadTexture("bg/front.png", "bg_front");
		//Player
		Content.loadTexture("character/idle.png", "character_idle");
		Content.loadTexture("character/walking.png", "character_walking");
		// Misc
		Content.loadTexture("platform.png", "platform");
		Content.loadTexture("debug.png", "debug");

		controllerListener = new ControllerListener();
		Controllers.addListener(controllerListener);

		// Game State Manager
		gsm = new GameStateManager(GameStateManager.States.Game);
		gsm.loadState(new GameState(controllerListener), GameStateManager.States.Game);

	}

	@Override
	public void render () {

		update();

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw stuff here
		gsm.getCurrentState().render(batch);

	}

	public void update(){
		gsm.getCurrentState().update(Gdx.graphics.getDeltaTime());
	}

	public ControllerListener getControllerListener() {
		return controllerListener;
	}
}
