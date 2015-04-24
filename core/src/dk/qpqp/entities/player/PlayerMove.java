package dk.qpqp.entities.player;

import dk.qpqp.utils.ControllerData;

/**
 * Created by Viktor on 24/04/2015.
 */
public class PlayerMove {

    private Player player;
    private float speed = 7;

    public PlayerMove(Player player) {
        this.player = player;
    }

    protected float update(float dt, ControllerData controllerData) {
        float moveAmount = 0;
        if (controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue() > 0.1 && !player.isBlocking() && !player.isHitting()) {
            player.setDx(speed * controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue() * dt * 1000);
            player.setFacingRight(true);
            moveAmount = Math.abs(controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue());
        }

        if (controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdNegative()].getValue() < -0.1 && !player.isBlocking() && !player.isHitting()) {
            player.setDx(speed * controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdNegative()].getValue() * dt * 1000);
            player.setFacingRight(false);
            moveAmount = Math.abs(controllerData.getAxisData()[controllerData.getType().getStickLeftVertical().getEventIdPositive()].getValue());
        }

        return moveAmount;
    }
}
