package dk.qpqp.entities;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.utils.Animation;
import dk.qpqp.utils.ControllerData;
import dk.qpqp.utils.MyControllerListener;

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

    private enum AnimationType{
        IDLE, WALKING, JUMP
    }

    public Player(int x, int y, int width, int height, OrthographicCamera camera, MyControllerListener controllerListener, Controller controller) {
        super(x, y, width, height);

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

        currentAnimation = AnimationType.IDLE;
        lastFrameAnimation = currentAnimation;

        facingRight = true;

        this.controllerListener = controllerListener;
        this.cam = camera;
        this.controller = controller;
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
            batch.draw(animations.get(currentAnimation).getFrame(), x+width, y, -width, height);
        batch.end();

    }

    @Override
    public void update(float dt) {
        animations.get(currentAnimation).update(dt);

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

        float moveAmount = 0;

        if (controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue() > 0.1) {
            setDx(speed * controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue() * dt * 1000);
            facingRight = true;
            moveAmount = Math.abs(controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue());
        }

        if (controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdNegative()].getValue() < -0.1) {
            setDx(speed * controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdNegative()].getValue() * dt * 1000);
            facingRight = false;
            moveAmount = Math.abs(controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue());
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

        // Set animation
        if (!onGround) { // jumping
            setCurrentAnimation(AnimationType.JUMP);
            if (lastFrameAnimation != AnimationType.JUMP) animations.get(AnimationType.JUMP).setCurrentFrame(0);
        } else if (moveAmount > 0.1f) { //if moving
            setCurrentAnimation(AnimationType.WALKING);
            animations.get(AnimationType.WALKING).setDelay(400 - (int) (moveAmount * 400) + 100);
        } else { // idle
            setCurrentAnimation(AnimationType.IDLE);
        }

        lastFrameAnimation = currentAnimation;

        super.update(dt);
    }

    public void setCurrentAnimation(AnimationType animation){
        currentAnimation = animation;
    }
}
