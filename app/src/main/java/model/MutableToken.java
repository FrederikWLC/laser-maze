package model;

public abstract class MutableToken extends Token implements IMutableToken  {
    protected Direction direction;
    protected boolean movable;
    protected boolean turnable;

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setTurnable(boolean turnable) {
        this.turnable = turnable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isMovable() {
        return movable;
    }

    public boolean isTurnable() {
        return turnable;
    }

    public boolean isTurned() {
        return direction != null; // Token is turned if it has a direction
    }

}
