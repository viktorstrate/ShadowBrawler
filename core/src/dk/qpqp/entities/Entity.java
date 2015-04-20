package dk.qpqp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Viktor on 19/04/2015.
 */
public abstract class Entity extends GameObject {

    // Collision
    protected int cWidth;
    protected int cHeight;
    protected int cxOffset, cyOffset;

    public Entity(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.cWidth = width;
        this.cHeight = height;
        this.cxOffset = 0;
        this.cyOffset = 0;

    }

    @Override
    public void render(SpriteBatch batch) {
        new DebugRect((int)(cxOffset+getX()), (int)(cyOffset+getY()), cWidth, cHeight).render(batch);
    }

    @Override
    public void update(float dt){
        x += getDx() * dt;
        y += getDy() * dt;
    }

    public int getcWidth() {
        return cWidth;
    }

    public void setcWidth(int cWidth) {
        this.cWidth = cWidth;
    }

    public int getcHeight() {
        return cHeight;
    }

    public void setcHeight(int cHeight) {
        this.cHeight = cHeight;
    }

    public float getCxOffset() {
        return cxOffset;
    }

    public void setCxOffset(int cX) {
        this.cxOffset = cX;
    }

    public float getCyOffset() {
        return cyOffset;
    }

    public void setCyOffset(int cY) {
        this.cyOffset = cY;
    }
}
