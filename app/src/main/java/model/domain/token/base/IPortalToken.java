package model.domain.token.base;

import model.domain.board.Board;
import model.domain.board.Direction;
import model.domain.board.PositionDirection;
import model.domain.engine.LaserEngine;

import java.util.List;

public interface IPortalToken extends IMutableTwinToken {

    List<PositionDirection> spawn(LaserEngine laserEngine, boolean throughBluePortal, List<PositionDirection> beamPath, Board board);
    Direction getBluePortalDirection();
    Direction getRedPortalDirection();

}
