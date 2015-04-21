package dk.qpqp.controllers.mappings;

/**
 * Created by viktorstrate on 20/04/15.
 */
public class PS4 implements ControllerInterface {
    @Override
    public ControllerEvent getBtnA() {
        return new ControllerEvent().setButton(1);
    }

    @Override
    public ControllerEvent getBtnB() {
        return new ControllerEvent().setButton(2);
    }

    @Override
    public ControllerEvent getBtnX() {
        return new ControllerEvent().setButton(0);
    }

    @Override
    public ControllerEvent getBtnY() {
        return new ControllerEvent().setButton(3);
    }

    @Override
    public ControllerEvent getArrowUp() {
        return null;
    }

    @Override
    public ControllerEvent getArrowLeft() {
        return null;
    }

    @Override
    public ControllerEvent getArrowDown() {
        return null;
    }

    @Override
    public ControllerEvent getArrowRight() {
        return null;
    }

    @Override
    public ControllerEvent getBtnRB() {
        return new ControllerEvent().setButton(5);
    }

    @Override
    public ControllerEvent getBtnRT() {
        return new ControllerEvent().setButton(7);
    }

    @Override
    public ControllerEvent getBtnLB() {
        return new ControllerEvent().setButton(4);
    }

    @Override
    public ControllerEvent getBtnLT() {
        return new ControllerEvent().setButton(5);
    }

    @Override
    public ControllerEvent getBtnHome() {
        return new ControllerEvent().setButton(12);
    }

    @Override
    public ControllerEvent getBtnStart() {
        return new ControllerEvent().setButton(9);
    }

    @Override
    public ControllerEvent getBtnBack() {
        return new ControllerEvent().setButton(8);
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
        return new ControllerEvent().setAxis(0, 0);
    }

    @Override
    public ControllerEvent getStickLeftHorizontal() {
        return new ControllerEvent().setAxis(1, 1);
    }

    @Override
    public ControllerEvent getBtnStickLeft() {
        return new ControllerEvent().setButton(10);
    }

    @Override
    public ControllerEvent getBtnStickRight() {
        return new ControllerEvent().setButton(11);
    }


    @Override
    public String getId() {
        return "Sony Computer Entertainment Wireless Controller";
    }
}
