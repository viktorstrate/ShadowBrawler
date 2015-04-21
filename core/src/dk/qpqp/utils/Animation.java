package dk.qpqp.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Viktor on 19/04/2015.
 */
public class Animation {

    private TextureRegion[] frames;
    private int currentFrame;

    private float delay;
    private float time;

    private int stopFrame;

    public Animation() {
        stopFrame = -1;
    }

    public void setAnimation(TextureRegion[] frames, float delay){
        this.frames = frames;
        this.delay = delay;
        currentFrame = 0;
        time = 0;
    }

    public void setAnimation(String texturePath, int width, int height, int frames, float delay){
        TextureRegion[] textures;
        textures = new TextureRegion[frames];
        for(int i = 0; i < frames; i++){
            textures[i] = new TextureRegion(Content.getTexture(texturePath), i*width, 0, width, height);
        }
        this.setAnimation(textures, delay);
    }

    public void update(float dt){
        if(delay<=0) return;
        if (stopFrame == currentFrame) {
            return;
        }

        if(delay/1000<time){
            time -= delay/1000;
            if(currentFrame==frames.length-1){
                currentFrame=0;
            } else currentFrame++;
        } else {
            time += dt;
        }
    }

    public void setStopPoint(int frame) {
        stopFrame = frame;
    }

    public TextureRegion getFrame(){
        return frames[getCurrentFrame()];
    }

    public TextureRegion[] getFrames() {
        return frames;
    }

    public void setFrames(TextureRegion[] frames) {
        this.frames = frames;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
