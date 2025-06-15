package model;


import java.util.ArrayList;
import java.util.List;

public class LaserToken extends MutableToken implements ILaserToken {

    boolean isActive = false;

    public LaserToken() {
        super();
    }

    public boolean isActive() {
        return isActive;
    }

    public void trigger(boolean isActive) {
        this.isActive = isActive;
    }

}
