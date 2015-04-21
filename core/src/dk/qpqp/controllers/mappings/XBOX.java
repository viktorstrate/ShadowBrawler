package dk.qpqp.controllers.mappings;

import com.badlogic.gdx.controllers.PovDirection;

/**
 * Created by Viktor on 21/04/2015.
 */
public class XBOX implements ControllerInterface {
    @Override
    public ControllerEvent getBtnA() {
        return new ControllerEvent().setButton(0);
    }

    @Override
    public ControllerEvent getBtnB() {
        return new ControllerEvent().setButton(1);
    }

    @Override
    public ControllerEvent getBtnX() {
        return new ControllerEvent().setButton(2);
    }

    @Override
    public ControllerEvent getBtnY() {
        return new ControllerEvent().setButton(3);
    }

    @Override
    public ControllerEvent getArrowUp() {
        return new ControllerEvent().setPov(0, PovDirection.north);
    }

    @Override
    public ControllerEvent getArrowLeft() {
        return new ControllerEvent().setPov(0, PovDirection.west);
    }

    @Override
    public ControllerEvent getArrowDown() {
        return new ControllerEvent().setPov(0, PovDirection.south);
    }

    @Override
    public ControllerEvent getArrowRight() {
        return new ControllerEvent().setPov(0, PovDirection.east);
    }

    @Override
    public ControllerEvent getBtnRB() {
        return new ControllerEvent().setButton(5);
    }

    @Override
    public ControllerEvent getBtnRT() {
        return new ControllerEvent().setAxis(-1, 4);
    }

    @Override
    public ControllerEvent getBtnLB() {
        return new ControllerEvent().setButton(4);
    }

    @Override
    public ControllerEvent getBtnLT() {
        return new ControllerEvent().setAxis(4, -1);
    }

    @Override
    public ControllerEvent getBtnHome() {
        return null;
    }

    @Override
    public ControllerEvent getBtnStart() {
        return new ControllerEvent().setButton(7);
    }

    @Override
    public ControllerEvent getBtnBack() {
        return new ControllerEvent().setButton(6);
    }

    @Override
    public ControllerEvent getStickRightVertical() {
        return new ControllerEvent().setAxis(2, 2);
    }

    @Override
    public ControllerEvent getStickRightHorizontal() {
        return new ControllerEvent().setAxis(3, 3);
    }

    @Override
    public ControllerEvent getStickLeftVertical() {
        return new ControllerEvent().setAxis(1, 1);
    }

    @Override
    public ControllerEvent getStickLeftHorizontal() {
        return new ControllerEvent().setAxis(0, 0);
    }

    @Override
    public ControllerEvent getBtnStickLeft() {
        return new ControllerEvent().setButton(8);
    }

    @Override
    public ControllerEvent getBtnStickRight() {
        return new ControllerEvent().setButton(9);
    }

    @Override
    public String getId() {
        return "Controller (Xbox 360 Wireless Receiver for Windows)";
    }
}
