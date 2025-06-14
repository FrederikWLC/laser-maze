package model;

public interface ITurnableToken extends IToken {

    Direction getDirection();

    void setDirection(Direction direction);

    void setTurnable(boolean turnable);
}
