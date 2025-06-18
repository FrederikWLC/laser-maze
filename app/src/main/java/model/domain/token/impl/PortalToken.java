package model.domain.token.impl;

import model.domain.board.Board;
import model.domain.board.Direction;
import model.domain.board.PositionDirection;
import model.domain.engine.LaserEngine;
import model.domain.token.base.MutableTwinToken;
import model.domain.token.base.IPortalToken;

import java.util.List;

public class PortalToken extends MutableTwinToken implements IPortalToken {

    public PortalToken() {
        super();
    }

    @Override
    public PortalToken getTwin() {
        return (PortalToken) super.getTwin();
    }

    public Direction getBluePortalDirection() {
        return this.getDirection(); // The blue portal's direction is the same as the portal's direction
    }

    public Direction getRedPortalDirection() {
        return getBluePortalDirection().opposite();
    }

    public List<PositionDirection> spawn(boolean throughBluePortal, List<PositionDirection> beamPath, Board board) {
        // when spawn(..) is called from the twin, the beam exits through the equivalent portal color it entered.
        Direction currentBeamDirection = throughBluePortal ? this.getBluePortalDirection() : this.getRedPortalDirection();
        // The beam exits the portal in the direction of the portal's exit, and skips the portal's own tile.
        PositionDirection currentBeamPositionDirection = new PositionDirection(this.getPosition(), currentBeamDirection);
        // The beam continues its path from the portal's exit position and direction.
        return LaserEngine.travel(currentBeamPositionDirection, beamPath, board);
    }

    @Override
    public List<PositionDirection> interact(PositionDirection currentBeamPositionDirection, List<PositionDirection> beamPath, Board board) {
        // A Portal token only spawns a beam through its twin if the beam is facing the opening of the portal.
        // this can be determined by checking if the beam's direction is parallel to the portal's direction.
        // and if the portal has a twin.
        boolean throughBluePortal = currentBeamPositionDirection.getDirection().opposite() == getBluePortalDirection();;
        boolean throughRedPortal = currentBeamPositionDirection.getDirection().opposite() == getBluePortalDirection().opposite();
        if ((throughBluePortal || throughRedPortal) && this.getTwin() != null && this.getTwin().isPlaced()) {
            return this.getTwin().spawn(throughBluePortal, beamPath, board);
        } else {
            return beamPath;
        }
    }
}