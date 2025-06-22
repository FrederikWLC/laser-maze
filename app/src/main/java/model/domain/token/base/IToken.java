package model.domain.token.base;

import model.domain.board.Position;
import model.domain.board.PositionDirection;
import model.domain.board.Board;
import model.domain.engine.LaserEngine;

import java.util.List;

public interface IToken {

    void setPosition(Position position);

    Position getPosition();

    boolean isPlaced();

    List<PositionDirection> interact(LaserEngine laserEngine, PositionDirection currentBeamPositionDirection, List<PositionDirection> beamPath, Board board);

    boolean isTouched (PositionDirection beamPositionDirection);

    boolean isTouchRequired();

    boolean isMovable();

    boolean isTurnable();


}
