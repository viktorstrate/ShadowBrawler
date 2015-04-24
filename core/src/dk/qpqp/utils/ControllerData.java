package dk.qpqp.utils;

import com.badlogic.gdx.controllers.Controller;
import dk.qpqp.controllers.mappings.ControllerEvent;
import dk.qpqp.controllers.mappings.ControllerInput;
import dk.qpqp.controllers.mappings.ControllerInterface;
import dk.qpqp.controllers.mappings.ControllerList;

/**
 * Created by viktorstrate on 20/04/15.
 */
public class ControllerData {
    private AxisData[] axisData;
    private ButtonData[] buttonData;
    private Controller controller;
    private ControllerInterface type = null;

    public enum AxisDirection {
        POSITIVE, NEGATIVE, NONE, BOTH
    }

    public ControllerData(int axises, int buttons, Controller controller){
        this.axisData = new AxisData[axises];
        this.buttonData = new ButtonData[buttons];
        this.controller = controller;

        for (ControllerList cList : ControllerList.values()) {
            if (cList.getController().getId().equals(controller.getName())) {
                type = cList.getController();
                System.out.printf("Using controller mapping %s, for controller\n", cList.toString());
                break;
            }
        }
        if (type == null) {
            type = ControllerList.XBOX.getController();
        }

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

    public ControllerInterface getType() {
        return type;
    }

    public boolean getValueAsButton(ControllerEvent event) {
        switch (event.getType()) {
            case BUTTON:
                return buttonData[event.getEventId()].isPressed();
            case AXIS:
                System.out.println("VALUE: " + controller.getAxis(event.getEventId()));
                if (Math.abs(controller.getAxis(event.getEventIdPositive())) > 0.2f)
                    return true;

                if (Math.abs(controller.getAxis(event.getEventIdNegative())) > 0.2f)
                    return true;
            case POV:
                return controller.getPov(event.getEventId()) == event.getDirection();
        }
        return false;
    }

    public boolean getValueAsButton(ControllerInput input) {
        return getValueAsButton(getInputType(input));
    }

    public float getValueAsAxis(ControllerEvent event, AxisDirection direction) {
        switch (event.getType()) {
            case BUTTON:
                if (controller.getButton(event.getEventId())) {
                    return 1f;
                } else return 0f;
            case AXIS:
                if (direction == AxisDirection.NEGATIVE) {
                    if (event.getEventIdNegative() == -1) return 0;
                    return axisData[event.getEventIdNegative()].getValue();
                }
                if (direction == AxisDirection.POSITIVE) {
                    if (event.getEventIdPositive() == -1) {
                        System.out.println("Is -1");
                        return 0;
                    }
                    return axisData[event.getEventIdPositive()].getValue();
                }
                if (direction == AxisDirection.NONE) {
                    if (event.getEventId() == -1) {
                        return 0;
                    }
                    return axisData[event.getEventId()].getValue();
                }
                if (direction == AxisDirection.BOTH) {
                    if (event.getEventIdPositive() == -1) {
                        return 0;
                    }
                    if (event.getEventIdNegative() == -1) {
                        return 0;
                    }
                    return axisData[event.getEventIdPositive()].getValue() + axisData[event.getEventIdNegative()].getValue();
                }
            case POV:
                if (controller.getPov(event.getEventId()) == event.getDirection()) {
                    return 1f;
                } else return 0f;
            default:
                System.out.println("Didn't find a value in ControllerData, getValueAsAxis");
                return 0;
        }
    }

    public float getValueAsAxis(ControllerInput input) {
        return getValueAsAxis(getInputType(input), AxisDirection.POSITIVE);
    }

    public float getValueAsAxis(ControllerInput input, AxisDirection direction) {
        return getValueAsAxis(getInputType(input), direction);
    }

    public ControllerEvent getInputType(ControllerInput input) {
        switch (input) {
            case A:
                return type.getBtnA();
            case B:
                return type.getBtnB();
            case X:
                return type.getBtnX();
            case Y:
                return type.getBtnY();
            case ARROW_UP:
                return type.getArrowUp();
            case ARROW_RIGHT:
                return type.getArrowRight();
            case ARROW_DOWN:
                return type.getArrowDown();
            case ARROW_LEFT:
                return type.getArrowLeft();
            case RB:
                return type.getBtnRB();
            case RT:
                return type.getBtnRT();
            case LB:
                return type.getBtnLB();
            case LT:
                return type.getBtnLT();
            case HOME:
                return type.getBtnHome();
            case BACK:
                return type.getBtnBack();
            case START:
                return type.getBtnStart();
            case STICK_LEFT_HORIZONTAL:
                return type.getStickLeftHorizontal();
            case STICK_LEFT_VERTICAL:
                return type.getStickLeftVertical();
            case STICK_LEFT_BUTTON:
                return type.getBtnStickLeft();
            case STICK_RIGHT_VERTICAL:
                return type.getStickRightVertical();
            case STICK_RIGHT_HORIZONTAL:
                return type.getStickRightHorizontal();
            case STICK_RIGHT_BUTTON:
                return type.getBtnStickRight();

        }
        return null;
    }
}
