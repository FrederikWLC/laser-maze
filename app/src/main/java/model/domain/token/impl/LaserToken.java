package model.domain.token.impl;


import model.domain.token.base.MutableToken;
import model.domain.token.base.ILaserToken;
import model.domain.token.base.IMutableToken;

public class LaserToken extends MutableToken implements ILaserToken, IMutableToken {

    boolean isActive = false;

    public LaserToken() {
        super();
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isTriggerable() {
        return isPlaced() & isTurned();
    }

    public void trigger(boolean isActive) {
        if (!isTriggerable()) {
            if (!isPlaced())
                throw new IllegalStateException("Laser can only be triggered when placed.");
            {
            }
            if (!isTurned()) {
                throw new IllegalStateException("Laser can only be triggered when turned.");
            }
        }
        this.isActive = isActive;
    }

    @Override
    public boolean isTouchRequired() {
        return false; // Default implementation, can be overridden by subclasses
    }

}
