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

    private enum AnimationType{
        IDLE, WALKING
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

        currentAnimation = AnimationType.IDLE;

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

        if (controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue() > 0.1) {
            setDx(speed * controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue() * dt * 1000);
            facingRight = true;
        }

        if (controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdNegative()].getValue() < -0.1) {
            setDx(speed * controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdNegative()].getValue() * dt * 1000);
            facingRight = false;
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

        super.update(dt);
    }

    public void setCurrentAnimation(AnimationType animation){
        currentAnimation = animation;
    }
}
