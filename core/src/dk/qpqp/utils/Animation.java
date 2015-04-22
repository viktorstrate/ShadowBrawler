package dk.qpqp.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Viktor on 19/04/2015.
 */
public class Animation {

    private TextureRegion[] frames;
    private int currentFrame;

    private float delay;
    private float time;

    private int stopFrame;
    private ArrayList<AnimationEvent> frameEvents;

    private int width, height;

    public Animation() {
        stopFrame = -1;
        this.frameEvents = new ArrayList<AnimationEvent>();
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

        this.width = width;
        this.height = height;

        for(int i = 0; i < frames; i++){
            textures[i] = new TextureRegion(Content.getTexture(texturePath), i*width, 0, width, height);
        }
        this.setAnimation(textures, delay);
    }

    public void addFrameEvent(ActionListener listener, int frame) {
        frameEvents.add(new AnimationEvent(listener, frame));
    }

    public void addFrameEvent(AnimationEvent event) {
        frameEvents.add(event);
    }

    public void update(float dt){
        if(delay<=0) return;
        if (stopFrame == currentFrame) {
            return;
        }

        for (AnimationEvent event : frameEvents) {
            if (currentFrame == event.getFrame())
                event.getListener().actionPerformed(new ActionEvent(this, 0, ""));
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
