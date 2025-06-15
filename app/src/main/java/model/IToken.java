package model;

import java.util.List;

public interface IToken {

    void setPosition(Position position);

    Position getPosition();

    boolean isPlaced();

    List<PositionDirection> interact(PositionDirection currentBeamPositionDirection, List<PositionDirection> beamPath, Board board);

    boolean isTouched (PositionDirection beamPositionDirection);

    boolean isTouchRequired();

    boolean isMovable();

    boolean isTurnable();



}
