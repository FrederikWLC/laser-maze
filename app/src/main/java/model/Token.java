package model;

public abstract class Token {
    protected Position position;
    protected Direction direction;
    protected boolean mutable = true;

    protected Token(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isMutable() {return mutable;}

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
