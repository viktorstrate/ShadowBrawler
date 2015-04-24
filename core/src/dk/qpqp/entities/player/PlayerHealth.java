package dk.qpqp.entities.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dk.qpqp.Game;
import dk.qpqp.entities.Entity;
import dk.qpqp.utils.Content;

/**
 * Created by Viktor on 23/04/2015.
 */
public class PlayerHealth extends Entity {

    private Player player;
    private static final int OFFSET_X = -5;
    private static final int OFFSET_Y = 55;

    private int health;

    private boolean show;
    private float showAmount;
    private float showTime;
    private static float DELAY_BEFORE_HIDE = 2000;
    private static final float SHOW_DELAY = 1;

    public PlayerHealth(Player player) {
        super((int) player.getX() + OFFSET_X, (int) player.getY() + OFFSET_Y, 30, 5);
        this.player = player;
        health = player.getHealth();
        show = false;
        showAmount = 0f;
    }

    public void update(float dt) {
        setX(player.getX() + OFFSET_X);
        setY(player.getY() + OFFSET_Y);

        if (show) {
            showAmount += dt * SHOW_DELAY;
            if (showAmount > 1) {
                showAmount = 1;
            }
            if (showTime + DELAY_BEFORE_HIDE / 1000 < Game.getTime()) {
                show = false;
            }
        } else {
            showAmount -= dt * SHOW_DELAY;
            if (showAmount < 0) {
                showAmount = 0;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.setColor(1, 1, 1, showAmount);
        batch.draw(new TextureRegion(Content.getTexture("character_health_bg")), getX(), getY(), getWidth(), getHeight());
        batch.end();

        batch.begin();
        batch.draw(new TextureRegion(Content.getTexture("character_health_fg")), getX(), getY(), getWidth() * health / 100, getHeight());
        batch.end();

        batch.setColor(1, 1, 1, 1);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        showAmount = 1;
        show = true;
        showTime = Game.getTime();
    }


}
