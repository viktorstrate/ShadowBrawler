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
    private float speed = 7;
    private boolean facingRight;
    private Controller controller;
    private boolean jumpKeyRelased = true;
    private AnimationType lastFrameAnimation;

    private PlayerHit hittingType;
    /**
     * If true switches to the punch animation
     */
    private boolean hitting;
    private boolean punchButtonJustPressed;
    /**
     * Is true the frame where the punch should deal damage
     */
    private boolean punchHit;
    /**
     * True if the punch button was pressed, last frame.
     */
    private boolean punchButtonPressedLastFrame;

    private boolean blocking = false;

    private PlayerHealth healthBar;

    private int health;
    private double stunTime;

    private enum AnimationType{
        IDLE, WALKING, JUMP, PUNCH, BLOCK, STUN
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
        this.stunTime = 0;

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
        punchButtonJustPressed = false;

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
        if (controllerData.getValueAsAxis(ControllerInput.RT) < -0.3f && onGround) {
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
        if (controllerData.getValueAsButton(ControllerInput.X) && onGround) {
            hittingType = PlayerHit.PUNCH;
            if (controllerData.getValueAsButton(ControllerInput.X) && !punchButtonPressedLastFrame) {
                punchButtonJustPressed = true;
                hitting = true;
            }
        }
        if (!onGround) {
            hitting = false;
        }

        // Moving
        float moveAmount = 0;

        if (controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue() > 0.1 && !blocking && !hitting) {
            setDx(speed * controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue() * dt * 1000);
            facingRight = true;
            moveAmount = Math.abs(controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue());
        }

        if (controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdNegative()].getValue() < -0.1 && !blocking && !hitting) {
            setDx(speed * controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdNegative()].getValue() * dt * 1000);
            facingRight = false;
            moveAmount = Math.abs(controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue());
        }

        if (stunTime > 0) {
            moveAmount = 0;
            setDx(0);
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
        } else if (stunTime > 0) {
            setCurrentAnimation(AnimationType.STUN);
        } else { // idle
            setCurrentAnimation(AnimationType.IDLE);
        }

        if (stunTime <= 0) {
            stunTime = 0;
        } else {
            stunTime -= dt * 1000;
        }


        setWidth(animations.get(currentAnimation).getWidth());
        setHeight(animations.get(currentAnimation).getHeight());

        lastFrameAnimation = currentAnimation;


        healthBar.update(dt);

        if (punchButtonJustPressed)
            System.out.println(punchButtonJustPressed);

        punchButtonPressedLastFrame = controllerData.getValueAsButton(ControllerInput.X);

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
                if (!isBlocking()) {
                    setHealth(getHealth() - 7);
                    stunTime = 50;
                } else {
                    stunTime = 20;
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

    public double getStunTime() {
        return stunTime;
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

    public boolean isPunchHit() {
        return punchHit;
    }

    public void resetHits() {
        punchHit = false;
    }
}
