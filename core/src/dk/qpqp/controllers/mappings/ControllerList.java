package dk.qpqp.controllers.mappings;

/**
 * Created by viktorstrate on 20/04/15.
 */
public enum ControllerList {
    PS3(new PS3()), PS4(new PS4()), XBOX(new XBOX());

    private ControllerInterface controllerInterface;

    ControllerList(ControllerInterface controllerInterface) {
        this.controllerInterface = controllerInterface;
    }

    public ControllerInterface getController() {
        return controllerInterface;
    }
}
