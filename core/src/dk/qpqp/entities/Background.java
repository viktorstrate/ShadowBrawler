package dk.qpqp.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.utils.Content;

/**
 * Created by Viktor on 19/04/2015.
 */
public class Background extends GameObject {

    private OrthographicCamera backCam, middleCam, frontCam;
    private final float perspectiveBack = 0.1f;
    private final float perspectiveMiddle = 0.05f;
    private final float CAMERA_RANGE_X = 46;
    private final float CAMERA_RANGE_Y = 26;
    private float cameraInitialX, cameraInitialY;

    public Background(OrthographicCamera frontCam) {
        super(0, 0, 1920, 1080);
        this.frontCam = frontCam;
        middleCam = new OrthographicCamera();
        middleCam.setToOrtho(false, frontCam.viewportWidth, frontCam.viewportHeight);
        backCam = new OrthographicCamera();
        backCam.setToOrtho(false, frontCam.viewportWidth, frontCam.viewportHeight);

        cameraInitialX = frontCam.position.x;
        cameraInitialY = frontCam.position.y;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(backCam.combined);
        batch.begin();
        batch.draw(Content.getTexture("bg_back"), x, y);
        batch.end();

        batch.setProjectionMatrix(middleCam.combined);
        batch.begin();
        batch.draw(Content.getTexture("bg_middle"), x, y);
        batch.end();

        batch.setProjectionMatrix(frontCam.combined);
        batch.begin();
        batch.draw(Content.getTexture("bg_front"), x, y);
        batch.end();
    }

    @Override
    public void update(float dt) {

        /*if(frontCam.position.x < cameraInitialX- CAMERA_RANGE_X) {
            frontCam.position.x = cameraInitialX- CAMERA_RANGE_X;
        }

        if(frontCam.position.x > cameraInitialX+ CAMERA_RANGE_X){
            frontCam.position.x = cameraInitialX+ CAMERA_RANGE_X;
        }*/

        if(frontCam.position.y < cameraInitialY - CAMERA_RANGE_Y){
            frontCam.position.y = cameraInitialY-CAMERA_RANGE_Y;
        }

        if(frontCam.position.y > cameraInitialY + CAMERA_RANGE_Y){
            frontCam.position.y = cameraInitialY+CAMERA_RANGE_Y;
        }

        backCam.position.set((frontCam.position.x - cameraInitialX) * perspectiveBack + cameraInitialX, (frontCam.position.y - cameraInitialY) * perspectiveBack + cameraInitialY, 0);
        backCam.update();
        middleCam.position.set((frontCam.position.x - cameraInitialX) * perspectiveMiddle + cameraInitialX, (frontCam.position.y - cameraInitialY) * perspectiveMiddle + cameraInitialY, 0);
        middleCam.update();
    }
}
