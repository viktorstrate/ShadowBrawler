package dk.qpqp.utils;

import com.badlogic.gdx.controllers.Controller;

/**
 * Created by viktorstrate on 20/04/15.
 */
public class AxisData {
    private Controller controller;
    private int axisCode;
    private float value;

    public AxisData(Controller controller, int axisCode, float value) {
        this.controller = controller;
        this.axisCode = axisCode;
        this.value = value;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public int getAxisCode() {
        return axisCode;
    }

    public void setAxisCode(int axisCode) {
        this.axisCode = axisCode;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
