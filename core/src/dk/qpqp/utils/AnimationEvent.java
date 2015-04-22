package dk.qpqp.utils;

import java.awt.event.ActionListener;

/**
 * Created by Viktor on 22/04/2015.
 */
public class AnimationEvent {
    private ActionListener listener;
    private int frame;

    public AnimationEvent(ActionListener listener, int frame) {
        this.listener = listener;
        this.frame = frame;
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }
}
