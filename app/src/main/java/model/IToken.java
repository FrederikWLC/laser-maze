package model;

public interface IToken {

    boolean isMovable();

    boolean isTurnable();

    Position getPosition();

    void setPosition(Position position);

}
