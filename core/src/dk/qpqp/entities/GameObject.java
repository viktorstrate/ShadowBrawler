package dk.qpqp.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by Viktor on 19/04/2015.
 */
public abstract class GameObject extends Rectangle {
    // position
    private float dx, dy;

    // dimensions

    private Texture texture;

    public GameObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update(float dt);

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
