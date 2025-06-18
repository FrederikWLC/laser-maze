package model.domain.token.impl;

import model.domain.board.Board;
import model.domain.board.Direction;
import model.domain.board.PositionDirection;
import model.domain.engine.LaserEngine;
import model.domain.token.base.MutableTwinToken;
import model.domain.token.base.IPortalToken;

import java.util.List;

public class PortalToken extends MutableTwinToken implements IPortalToken {
    PortalToken twin;

    public PortalToken() {
        super();
    }

    public void setTwin(PortalToken twin) {
        this.twin = twin;
    }

    public PortalToken getTwin() {
        return twin;
    }

    public Direction getBluePortalDirection() {
        return this.getDirection(); // The blue portal's direction is the same as the portal's direction
    }

    public Direction getRedPortalDirection() {
        return getBluePortalDirection().opposite();
    }

    public List<PositionDirection> spawn(boolean throughBluePortal, List<PositionDirection> beamPath, Board board) {
        // when spawn(..) is called from the twin, the beam exits through the equivalent portal color it entered.
        Direction currentBeamDirection = throughBluePortal ? getBluePortalDirection() : getRedPortalDirection();
        // The beam exits the portal in the direction of the portal's exit, and skips the portal's own tile (thus the increment).
        PositionDirection currentBeamPositionDirection = new PositionDirection(getPosition(), currentBeamDirection).increment();
        // The beam continues its path from the portal's exit position and direction.
        return LaserEngine.travel(currentBeamPositionDirection, beamPath, board);
    }

    @Override
    public List<PositionDirection> interact(PositionDirection currentBeamPositionDirection, List<PositionDirection> beamPath, Board board) {
        // A Portal token only spawns a beam through its twin if the beam is facing the opening of the portal.
        // this is determined by checking if the beam's direction is parallel to the portal's direction.
        // and if the portal has a twin.
        if (getDirection().isParallel(currentBeamPositionDirection.getDirection()) && getTwin() != null) {
            boolean throughBluePortal = currentBeamPositionDirection.getDirection().opposite() == getBluePortalDirection();
            return getTwin().spawn(throughBluePortal, beamPath, board);
        } else {
            return beamPath;
        }
    }
}