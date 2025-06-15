package model;

import java.util.List;

public interface IToken {

    boolean isMovable();

    boolean isTurnable();

    Position getPosition();

    void setPosition(Position position);

    List<PositionDirection> interact(PositionDirection currentPositionDirection, List<PositionDirection> beamPath, Board board);

}
