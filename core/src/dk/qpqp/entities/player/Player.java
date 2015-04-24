package dk.qpqp.entities.player;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.controllers.mappings.ControllerInput;
import dk.qpqp.entities.Entity;
import dk.qpqp.utils.Animation;
import dk.qpqp.utils.ControllerData;
import dk.qpqp.utils.MyControllerListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by Viktor on 19/04/2015.
 */
public class Player extends Entity {

    private HashMap<AnimationType, Animation> animations;
    private AnimationType currentAnimation;
    private OrthographicCamera cam;
    private float gravity = 400;
    private boolean onGround = false;
    private final int groundLvl = 145;
    private MyControllerListener controllerListener;

    private boolean facingRight;
    private Controller controller;
    private boolean jumpKeyRelased = true;
    private AnimationType lastFrameAnimation;

    private PlayerHit hittingType;
    /**
     * True if the punch button was pressed, last frame.
     */
    private boolean canHit;
    /**
     * If true switches to the punch animation
     */
    private boolean hitting;
    /**
     * Is true the frame where the punch should deal damage
     */
    private boolean punchHit;

    private boolean blocking = false;

    private PlayerHealth healthBar;

    private int health;
    private boolean hit;

    private PlayerMove move;

    private enum AnimationType{
        IDLE, WALKING, JUMP, PUNCH, BLOCK, STUN, HIT
    }

    /**
     * Types of player attacks
     */
    public enum PlayerHit {
        PUNCH
    }

    public Player(int x, int y, int width, int height, OrthographicCamera camera, MyControllerListener controllerListener, Controller controller) {
        super(x, y, width, height);

        this.health = 100;
        this.hit = false;

        animations = new HashMap<AnimationType, Animation>();
        Animation idleAnim = new Animation();
        idleAnim.setAnimation("character_idle", 23, 50, 4, 175);
        animations.put(AnimationType.IDLE, idleAnim);

        Animation walkingAnim = new Animation();
        walkingAnim.setAnimation("character_walking", 23, 50, 4, 200);
        animations.put(AnimationType.WALKING, walkingAnim);

        Animation jumpAnim = new Animation();
        jumpAnim.setAnimation("character_jump", 19, 50, 7, 75);
        jumpAnim.setStopPoint(3);
        animations.put(AnimationType.JUMP, jumpAnim);

        Animation punchAnim = new Animation();
        punchAnim.setAnimation("character_punch", 35, 50, 3, 100);
        punchAnim.addFrameEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hittingType = PlayerHit.PUNCH;
                hitting = false;
                canHit = true;
                punchHit = true;
            }
        }, 2);
        animations.put(AnimationType.PUNCH, punchAnim);

        Animation stunAnim = new Animation();
        stunAnim.setAnimation("character_stun", 31, 50, 4, 150);
        animations.put(AnimationType.STUN, stunAnim);

        Animation blockAnim = new Animation();
        blockAnim.setAnimation("character_block", 25, 50, 2, 50);
        blockAnim.setCurrentFrame(1);
        blockAnim.setStopPoint(1);
        animations.put(AnimationType.BLOCK, blockAnim);

        Animation hitAnim = new Animation();
        hitAnim.setAnimation("character_hit", 31, 50, 4, 100);
        hitAnim.addFrameEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hit = false;
            }
        }, 3);
        animations.put(AnimationType.HIT, hitAnim);

        currentAnimation = AnimationType.IDLE;
        lastFrameAnimation = currentAnimation;

        facingRight = true;

        this.healthBar = new PlayerHealth(this);

        this.controllerListener = controllerListener;
        this.cam = camera;
        this.controller = controller;

        hittingType = PlayerHit.PUNCH;
        hitting = false;
        punchHit = false;

        move = new PlayerMove(this);
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.setProjectionMatrix(cam.combined);
        //super.render(batch);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        if (facingRight)
            batch.draw(animations.get(currentAnimation).getFrame(), x, y, width, height);
        else
            batch.draw(animations.get(currentAnimation).getFrame(), x + getInitWidth(), y, -width, height);
        batch.end();

        healthBar.render(batch);

    }

    @Override
    public void update(float dt) {
        animations.get(currentAnimation).update(dt);

        blocking = false;

        if(!onGround) {
            setDy(getDy() - gravity * dt);
        }

        setDx(0);

        if(getY()>groundLvl){
            onGround = false;
        } else {
            onGround = true;
            if(getY()!=groundLvl)
                setY(groundLvl);
            setDy(0);

        }

        ControllerData controllerData = controllerListener.getControllers()
                                        .get(controller);


        // Blocking
        if (controllerData.getValueAsAxis(ControllerInput.RT, ControllerData.AxisDirection.BOTH) < -0.3f && onGround) {
            blocking = true;
        }


        // Jump
        if (controller.getButton(controllerData.getType().getBtnA().getEventId()) && onGround && jumpKeyRelased) {
            onGround = false;
            setDy(200);
            jumpKeyRelased = false;
        }

        if (!jumpKeyRelased && !controller.getButton(controllerData.getType().getBtnA().getEventId())) {
            jumpKeyRelased = true;
        }

        // Punching
        if (controllerData.getValueAsButton(ControllerInput.X) ||
                Math.abs(controllerData.getValueAsAxis(ControllerInput.STICK_RIGHT_HORIZONTAL, ControllerData.AxisDirection.BOTH)) > 0.3f) {
            hittingType = PlayerHit.PUNCH;
            hitting = true;
            if (controllerData.getValueAsAxis(ControllerInput.STICK_RIGHT_HORIZONTAL, ControllerData.AxisDirection.POSITIVE) > 0.3f) {
                facingRight = true;
            } else if (controllerData.getValueAsAxis(ControllerInput.STICK_RIGHT_HORIZONTAL, ControllerData.AxisDirection.POSITIVE) < -0.3f) {
                facingRight = false;
            }
        } else {
            hitting = false;
        }

        if (!onGround) {
            hitting = false;
        }

        float moveAmount = 0;
        // Moving
        if (!hit) {
            moveAmount = move.update(dt, controllerData);
        }

        // Set animation
        if (!onGround) { // jumping
            setCurrentAnimation(AnimationType.JUMP);
            if (lastFrameAnimation != AnimationType.JUMP) animations.get(AnimationType.JUMP).setCurrentFrame(0);
        } else if (hittingType == PlayerHit.PUNCH && hitting) { // Basic punch
            setCurrentAnimation(AnimationType.PUNCH);
        } else if (blocking) {
            setCurrentAnimation(AnimationType.BLOCK);
        } else if (moveAmount > 0.1f) { //if moving
            setCurrentAnimation(AnimationType.WALKING);
            animations.get(AnimationType.WALKING).setDelay(400 - (int) (moveAmount * 400) + 100);
        } else if (hit) {
            setCurrentAnimation(AnimationType.HIT);
            if (lastFrameAnimation != AnimationType.HIT)
                animations.get(AnimationType.HIT).setCurrentFrame(0);
        } else { // idle
            setCurrentAnimation(AnimationType.IDLE);
        }

        setWidth(animations.get(currentAnimation).getWidth());
        setHeight(animations.get(currentAnimation).getHeight());

        lastFrameAnimation = currentAnimation;

        healthBar.update(dt);

        super.update(dt);
    }

    public void setCurrentAnimation(AnimationType animation){
        currentAnimation = animation;
        if (lastFrameAnimation != AnimationType.PUNCH) {
            animations.get(AnimationType.PUNCH).setCurrentFrame(0);
        }
    }

    /**
     * Hits the player
     *
     * @param hitType   The type of hit, changes the stun time and damage dealt
     * @param fromRight The way the opponent is facing.
     */
    public void hit(PlayerHit hitType, boolean fromRight) {

        switch (hitType) {
            case PUNCH:
                facingRight = fromRight;
                if (!isBlocking()) {
                    setHealth(getHealth() - 7);
                    hit = true;
                } else {
                    setHealth(getHealth() - 2);
                }
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health <= 0) {
            this.health = 0;
        } else {
            this.health = health;
        }
        healthBar.setHealth(this.getHealth());
    }

    public PlayerHit getHittingType() {
        return hittingType;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public boolean isPunchHit() {
        return punchHit;
    }

    public void setPunchHit(boolean punchHit) {
        this.punchHit = punchHit;
    }

    public void resetHits() {
        punchHit = false;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isHitting() {
        return hitting;
    }
}
