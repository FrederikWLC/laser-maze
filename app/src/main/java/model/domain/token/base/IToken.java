package model.domain.token.base;

import model.domain.board.Position;
import model.domain.board.PositionDirection;
import model.domain.board.Board;
import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;

import java.util.List;

public interface IToken {

    void setPosition(Position position);

    Position getPosition();

    boolean isPlaced();

    List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentPositionTurn, List<PositionTurn> beamPath);

    boolean isTouchRequired();

    boolean isMovable();

    boolean isTurnable();


}
