package dk.qpqp.states;

import java.util.HashMap;

/**
 * Created by Viktor on 19/04/2015.
 */
public class GameStateManager {
    private States currentState;
    private HashMap<States, State> states;

    public enum States{
        Main, Game
    }

    public GameStateManager(States state) {
        states = new HashMap<States, State>();
        currentState = state;
    }

    public State getCurrentState() {
        return states.get(currentState);
    }

    public void setState(States currentState) {
        this.currentState = currentState;
    }

    public void loadState(State state, States key){
        states.put(key, state);
    }
}
