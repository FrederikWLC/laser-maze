package model.domain.token.base;

import model.domain.board.Board;
import model.domain.board.Direction;
import model.domain.board.PositionDirection;
import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;

import java.util.List;

public interface IPortalToken extends IMutableTwinToken {

    List<PositionTurn> spawn(LaserEngine laserEngine, boolean throughBluePortal, List<PositionTurn> beamPath);
    Direction getBluePortalDirection();
    Direction getRedPortalDirection();

}
