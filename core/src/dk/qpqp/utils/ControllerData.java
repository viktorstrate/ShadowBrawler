package dk.qpqp.utils;

import com.badlogic.gdx.controllers.Controller;

/**
 * Created by viktorstrate on 20/04/15.
 */
public class ControllerData {
    private AxisData[] axisData;
    private ButtonData[] buttonData;
    private Controller controller;

    public ControllerData(int axises, int buttons, Controller controller){
        this.axisData = new AxisData[axises];
        this.buttonData = new ButtonData[buttons];
        this.controller = controller;

        for(int i = 0; i < axises; i++){
            axisData[i] = new AxisData(controller, i, 0);
        }
        for(int i = 0; i < buttons; i++){
            buttonData[i] = new ButtonData(controller, i, false);
        }

    }

    public AxisData[] getAxisData() {
        return axisData;
    }

    public void setAxisData(AxisData[] axisData) {
        this.axisData = axisData;
    }

    public ButtonData getButtonData(int buttonCode) {
        return buttonData[buttonCode];
    }

    public void setButtonData(int buttonCode, ButtonData buttonData) {
        this.buttonData[buttonCode] = buttonData;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}