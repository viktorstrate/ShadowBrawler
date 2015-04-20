package dk.qpqp.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.utils.Content;

/**
 * Created by Viktor on 19/04/2015.
 */
public class DebugRect extends GameObject {
    public DebugRect(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(Content.getTexture("debug"), x, y, width, height);
        batch.end();
    }

    @Override
    public void update(float dt) {

    }
}
