package dk.qpqp.states;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.Game;
import dk.qpqp.entities.Background;
import dk.qpqp.entities.Platform;
import dk.qpqp.entities.Player;
import dk.qpqp.utils.ControllerListener;

import java.util.ArrayList;

/**
 * Created by Viktor on 19/04/2015.
 */
public class GameState implements State {

    private OrthographicCamera gameCam;
    private final float CAM_ZOOM = 1.3f;

    private Background background;
    private Platform platform;
    private ArrayList<Player> players;
    private ControllerListener controllerListener;

    public GameState(ControllerListener controllerListener) {
        gameCam = new OrthographicCamera();
        gameCam.setToOrtho(false, Game.WIDTH/ CAM_ZOOM, Game.HEIGHT/ CAM_ZOOM);
        gameCam.position.set(gameCam.position.x + Game.WIDTH / 2 - (Game.WIDTH/ CAM_ZOOM)/2, gameCam.position.y +  Game.HEIGHT/2 - (Game.HEIGHT/ CAM_ZOOM)/2, 0);
        gameCam.update();

        background = new Background(gameCam);
        platform = new Platform(gameCam);
        players = new ArrayList<Player>();

        this.controllerListener = controllerListener;


        for(Controller controller: Controllers.getControllers()){
            players.add(new Player(200, 200, 23, 50, gameCam, controllerListener, controller));
        }


    }

    @Override
    public void render(SpriteBatch batch) {
        background.render(batch);
        platform.render(batch);
        for(Player player: players)
            player.render(batch);
    }

    @Override
    public void update(float dt) {
        background.update(dt);
        platform.update(dt);
        for(Player player: players)
            player.update(dt);

        int totalX = 0;
        for(Player player: players){
            totalX+=player.getX();
        }
        int averageX = 100;
        if (players.size() != 0) {
            averageX = totalX / players.size();
            System.out.println("No consoles connected, connect a console and restart the game.");
        }
        gameCam.position.set(averageX, 0, 0);
    }
}
