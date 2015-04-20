package dk.qpqp.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.Game;
import dk.qpqp.utils.Content;

/**
 * Created by Viktor on 19/04/2015.
 */
public class Platform extends Entity {

    private OrthographicCamera cam;

    public Platform(OrthographicCamera cam) {
        super(Game.WIDTH/2 - 171, 70, 343, 78);
        this.cam = cam;
        setcHeight(147);
        setCyOffset(-72);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(Content.getTexture("platform"), x, y, width, height);
        batch.end();

        //super.render(batch);
    }

    @Override
    public void update(float dt) {

    }
}
