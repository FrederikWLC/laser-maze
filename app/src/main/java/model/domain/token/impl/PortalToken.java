package model.domain.token.impl;

import model.domain.board.Direction;
import model.domain.board.PositionTurn;
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

    public List<PositionTurn> spawn(LaserEngine laserEngine, boolean throughBluePortal, List<PositionTurn> beamPath) {
        // when spawn(..) is called from the twin, the beam exits through the equivalent portal color it entered.
        Direction currentBeamOutDirection = throughBluePortal ? this.getBluePortalDirection() : this.getRedPortalDirection();
        // The beam exits the portal in the direction of the portal's exit, and skips the portal's own tile.
        PositionTurn currentBeamPositionTurn = new PositionTurn(this.getPosition(), currentBeamOutDirection, currentBeamOutDirection);
        // The beam continues its path from the portal's exit position and direction.
        return laserEngine.travelFrom(currentBeamPositionTurn, beamPath);
    }

    @Override
    public List<PositionTurn> interact(LaserEngine laserEngine,PositionTurn currentBeamPositionTurn, List<PositionTurn> beamPath) {
        // A Portal token only spawns a beam through its twin if the beam is facing the opening of the portal.
        // this can be determined by checking if the beam's direction is parallel to the portal's direction.
        // and if the portal has a twin.
        boolean throughBluePortal = currentBeamPositionTurn.getOut().opposite() == getBluePortalDirection();;
        boolean throughRedPortal = currentBeamPositionTurn.getOut().opposite() == getBluePortalDirection().opposite();
        if ((throughBluePortal || throughRedPortal) && this.getTwin() != null && this.getTwin().isPlaced()) {
            return this.getTwin().spawn(laserEngine, throughBluePortal, beamPath);
        } else {
            return beamPath;
        }
    }
}