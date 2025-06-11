package model;


import java.util.ArrayList;
import java.util.List;

public class LaserToken extends Token {

    boolean isActive = false;

    public LaserToken(Position position, Direction direction) {
        super(position,direction);
    }

    public boolean isActive() {
        return isActive;
    }

    public void trigger(boolean isActive) {
        this.isActive = isActive;
    }

}
