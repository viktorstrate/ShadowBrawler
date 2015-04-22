package dk.qpqp.entities;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.controllers.mappings.ControllerInput;
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

    private boolean punching = false;
    private boolean blocking = false;

    private enum AnimationType{
        IDLE, WALKING, JUMP, PUNCH, BLOCK
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

        Animation punchAnim = new Animation();
        punchAnim.setAnimation("character_punch", 35, 50, 3, 100);
        punchAnim.addFrameEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                punching = false;
            }
        }, 2);
        animations.put(AnimationType.PUNCH, punchAnim);

        Animation blockAnim = new Animation();
        blockAnim.setAnimation("character_block", 25, 50, 2, 50);
        blockAnim.setCurrentFrame(1);
        blockAnim.setStopPoint(1);
        animations.put(AnimationType.BLOCK, blockAnim);

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
            batch.draw(animations.get(currentAnimation).getFrame(), x + getInitWidth(), y, -width, height);
        batch.end();

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
            punching = true;
        }
        if (!onGround) {
            punching = false;
        }

        // Moving
        float moveAmount = 0;

        if (controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue() > 0.1 && !blocking && !punching) {
            setDx(speed * controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue() * dt * 1000);
            facingRight = true;
            moveAmount = Math.abs(controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue());
        }

        if (controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdNegative()].getValue() < -0.1 && !blocking && !punching) {
            setDx(speed * controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdNegative()].getValue() * dt * 1000);
            facingRight = false;
            moveAmount = Math.abs(controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue());
        }

        // Set animation
        if (!onGround) { // jumping
            setCurrentAnimation(AnimationType.JUMP);
            if (lastFrameAnimation != AnimationType.JUMP) animations.get(AnimationType.JUMP).setCurrentFrame(0);
        } else if (punching) { // Basic punch
            setCurrentAnimation(AnimationType.PUNCH);
        } else if (blocking) {
            setCurrentAnimation(AnimationType.BLOCK);
        } else if (moveAmount > 0.1f) { //if moving
            setCurrentAnimation(AnimationType.WALKING);
            animations.get(AnimationType.WALKING).setDelay(400 - (int) (moveAmount * 400) + 100);
        } else { // idle
            setCurrentAnimation(AnimationType.IDLE);
        }

        setWidth(animations.get(currentAnimation).getWidth());
        setHeight(animations.get(currentAnimation).getHeight());

        lastFrameAnimation = currentAnimation;

        super.update(dt);
    }

    public void setCurrentAnimation(AnimationType animation){
        currentAnimation = animation;
        if (lastFrameAnimation != AnimationType.PUNCH) {
            animations.get(AnimationType.PUNCH).setCurrentFrame(0);
        }
    }
}
