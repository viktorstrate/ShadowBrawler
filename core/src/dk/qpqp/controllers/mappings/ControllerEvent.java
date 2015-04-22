package dk.qpqp.controllers.mappings;

import com.badlogic.gdx.controllers.PovDirection;

/**
 * Created by Viktor on 21/04/2015.
 */
public class ControllerEvent {
    private ControllerEventType type;
    private int eventId;
    private int eventIdPositive;
    private int eventIdNegative;
    private PovDirection direction;

    public ControllerEvent() {
        type = null;
        eventIdPositive = -1;
        eventIdNegative = -1;
        eventId = -1;
        direction = null;
    }


    public ControllerEvent setButton(int eventId) {
        this.eventId = eventId;
        this.type = ControllerEventType.BUTTON;
        return this;
    }

    public ControllerEvent setAxis(int positiveEventId, int negativeEventId) {
        this.eventIdPositive = positiveEventId;
        this.eventIdNegative = negativeEventId;

        if (eventIdNegative == -1) this.eventId = positiveEventId;
        if (eventIdPositive == -1) this.eventId = negativeEventId;

        this.type = ControllerEventType.AXIS;
        return this;
    }

    public ControllerEvent setPov(int eventId, PovDirection direction) {
        this.eventId = eventId;
        this.direction = direction;
        return this;
    }

    public ControllerEventType getType() {
        return type;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setDirection(PovDirection direction) {
        this.direction = direction;
    }

    public PovDirection getDirection() {
        return direction;
    }

    public int getEventIdPositive() {
        return eventIdPositive;
    }

    public void setEventIdPositive(int eventIdPositive) {
        this.eventIdPositive = eventIdPositive;
    }

    public int getEventIdNegative() {
        return eventIdNegative;
    }

    public void setEventIdNegative(int eventIdNegative) {
        this.eventIdNegative = eventIdNegative;
    }
}
