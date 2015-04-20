package dk.qpqp.controllers.mappings;

/**
 * Created by viktorstrate on 20/04/15.
 */
public class PS4 implements ControllerInterface {
    @Override
    public int getBtnA() {
        return 1;
    }

    @Override
    public int getBtnB() {
        return 2;
    }

    @Override
    public int getBtnX() {
        return 0;
    }

    @Override
    public int getBtnY() {
        return 3;
    }

    @Override
    public int getArrowUp() {
        return -1;
    }

    @Override
    public int getArrowLeft() {
        return -1;
    }

    @Override
    public int getArrowDown() {
        return -1;
    }

    @Override
    public int getArrowRight() {
        return -1;
    }

    @Override
    public int getBtnRB() {
        return 5;
    }

    @Override
    public int getBtnRT() {
        return 7;
    }

    @Override
    public int getBtnLB() {
        return 4;
    }

    @Override
    public int getBtnLT() {
        return 6;
    }

    @Override
    public int getBtnHome() {
        return 12;
    }

    @Override
    public int getBtnStart() {
        return 9;
    }

    @Override
    public int getBtnBack() {
        return 8;
    }

    @Override
    public int getStickRightVertical() {
        return 2;
    }

    @Override
    public int getStickRightHorizontal() {
        return 3;
    }

    @Override
    public int getStickLeftVertical() {
        return 0;
    }

    @Override
    public int getStickLeftHorizontal() {
        return 1;
    }

    @Override
    public int getBtnStickLeft() {
        return 10;
    }

    @Override
    public int getBtnStickRight() {
        return 11;
    }

    @Override
    public String getId() {
        return "Sony Computer Entertainment Wireless Controller";
    }
}
