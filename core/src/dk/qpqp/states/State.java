package dk.qpqp.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Viktor on 19/04/2015.
 */
public abstract interface State {

    public void render(SpriteBatch batch);

    public void update(float dt);

}
