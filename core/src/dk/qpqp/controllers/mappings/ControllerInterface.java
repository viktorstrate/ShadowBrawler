package dk.qpqp.controllers.mappings;

/**
 * Created by viktorstrate on 20/04/15.
 */
public abstract interface ControllerInterface {

    public ControllerEvent getBtnA();

    public ControllerEvent getBtnB();

    public ControllerEvent getBtnX();

    public ControllerEvent getBtnY();

    public ControllerEvent getArrowUp();

    public ControllerEvent getArrowLeft();

    public ControllerEvent getArrowDown();

    public ControllerEvent getArrowRight();

    public ControllerEvent getBtnRB();

    public ControllerEvent getBtnRT();

    public ControllerEvent getBtnLB();

    public ControllerEvent getBtnLT();

    public ControllerEvent getBtnHome();

    public ControllerEvent getBtnStart();

    public ControllerEvent getBtnBack();

    public ControllerEvent getStickRightVertical();

    public ControllerEvent getStickRightHorizontal();

    public ControllerEvent getStickLeftVertical();

    public ControllerEvent getStickLeftHorizontal();

    public ControllerEvent getBtnStickLeft();

    public ControllerEvent getBtnStickRight();

    public String getId();
}
