package dk.qpqp.utils;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by viktorstrate on 20/04/15.
 */
public class ControllerListener implements com.badlogic.gdx.controllers.ControllerListener {

    private HashMap<Controller, ControllerData> controllers;
    private ArrayList<Controller> controllerList;

    public ControllerListener(){

        System.out.println(Controllers.getControllers().size+" controllers connected");

        controllers = new HashMap<Controller, ControllerData>();
        controllerList = new ArrayList<Controller>();
        init();
    }

    private void init(){
        for(Controller controller: Controllers.getControllers()){
            controllers.put(controller, new ControllerData(4, 17, controller));
            controllerList.add(controller);
        }
    }

    @Override
    public void connected(Controller controller) {
        System.out.println(controller.getName()+" connected!");
        controllers.put(controller, new ControllerData(4, 17, controller));
        controllerList.add(controller);
    }

    @Override
    public void disconnected(Controller controller) {
        System.out.println(controller.getName()+" disconnected!");
        controllers.remove(controller);
        controllerList.remove(controller);
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        System.out.println(controller.getName()+", pressed button: "+buttonCode);
        controllers.get(controller).getButtonData(buttonCode).setPressed(true);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        controllers.get(controller).getButtonData(buttonCode).setPressed(false);
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        if(value>0.05 || value<-0.05) {
            //System.out.println(controller.getName() + " moved axis: " + axisCode + " new value: " + value);
            controllers.get(controller).getAxisData()[axisCode].setValue(value);
        }
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }

    public HashMap<Controller, ControllerData> getControllers() {
        return controllers;
    }

    public ArrayList<Controller> getControllerList() {
        return controllerList;
    }
}
