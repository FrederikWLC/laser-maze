package model;

public interface ITurnableToken extends IToken {

    void setTurnable(boolean turnable);

    void setDirection(Direction direction);

    Direction getDirection();

    boolean isTurned();
}
