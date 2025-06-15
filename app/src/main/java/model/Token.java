package model;

public abstract class Token implements IToken {
    protected Position position;

    protected Token() {}

    public Position getPosition() {
        return position;
    }

    public boolean isMovable() {
        return false;
    }

    public boolean isTurnable() {
        return false;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
