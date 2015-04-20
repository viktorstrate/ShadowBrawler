package dk.qpqp.controllers.mappings;

/**
 * Created by viktorstrate on 20/04/15.
 */
public class PS3 implements ControllerInterface {

    @Override
    public int getBtnA() {
        return 14;
    }

    @Override
    public int getBtnB() {
        return 13;
    }

    @Override
    public int getBtnX() {
        return 15;
    }

    @Override
    public int getBtnY() {
        return 12;
    }

    @Override
    public int getArrowUp() {
        return 4;
    }

    @Override
    public int getArrowLeft() {
        return 7;
    }

    @Override
    public int getArrowDown() {
        return 6;
    }

    @Override
    public int getArrowRight() {
        return 5;
    }

    @Override
    public int getBtnRB() {
        return 11;
    }

    @Override
    public int getBtnRT() {
        return 9;
    }

    @Override
    public int getBtnLB() {
        return 10;
    }

    @Override
    public int getBtnLT() {
        return 8;
    }

    @Override
    public int getBtnHome() {
        return 16;
    }

    @Override
    public int getBtnStart() {
        return 3;
    }

    @Override
    public int getBtnBack() {
        return 0;
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
        return 1;
    }

    @Override
    public int getBtnStickRight() {
        return 2;
    }

    @Override
    public String getId() {
        return "Sony PLAYSTATION(R)3 Controller";
    }
}
