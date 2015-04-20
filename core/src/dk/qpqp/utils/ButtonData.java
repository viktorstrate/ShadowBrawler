package dk.qpqp.utils;

import com.badlogic.gdx.controllers.Controller;

/**
 * Created by viktorstrate on 20/04/15.
 */
public class ButtonData {
    private Controller controller;
    private int buttonKey;
    private boolean pressed;

    public ButtonData(Controller controller, int buttonKey, boolean pressed) {
        this.controller = controller;
        this.buttonKey = buttonKey;
        this.pressed = pressed;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public int getButtonKey() {
        return buttonKey;
    }

    public void setButtonKey(int buttonKey) {
        this.buttonKey = buttonKey;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
