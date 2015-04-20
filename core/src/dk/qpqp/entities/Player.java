package dk.qpqp.entities;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.utils.Animation;
import dk.qpqp.utils.ControllerData;
import dk.qpqp.utils.ControllerListener;

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
    private ControllerListener controllerListener;
    private float speed = 7;
    private boolean faceingRight;
    private Controller controller;

    private enum AnimationType{
        IDLE, WALKING
    }

    public Player(int x, int y, int width, int height, OrthographicCamera camera, ControllerListener controllerListener, Controller controller) {
        super(x, y, width, height);

        animations = new HashMap<AnimationType, Animation>();
        Animation idleAnim = new Animation();
        idleAnim.setAnimation("character_idle", 23, 50, 4, 175);
        animations.put(AnimationType.IDLE, idleAnim);
        Animation walkingAnim = new Animation();
        walkingAnim.setAnimation("character_walking", 37, 80, 4, 200);
        animations.put(AnimationType.WALKING, walkingAnim);

        currentAnimation = AnimationType.IDLE;

        faceingRight = true;

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
        if(faceingRight)
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

        if(Math.abs(controllerData.getAxisData()[0].getValue())>0.5){
            setCurrentAnimation(AnimationType.WALKING);
            animations.get(AnimationType.WALKING).setDelay(400-(int)(Math.abs(controllerData.getAxisData()[0].getValue())*400)+100);
            System.out.println(animations.get(AnimationType.WALKING).getDelay());
            faceingRight = controllerData.getAxisData()[0].getValue()>0;
            setDx(speed*controllerData.getAxisData()[0].getValue()*dt*1000);
        } else {
            setCurrentAnimation(AnimationType.IDLE);
            setDx(0);
        }

        super.update(dt);
    }

    public void setCurrentAnimation(AnimationType animation){
        currentAnimation = animation;
    }
}
