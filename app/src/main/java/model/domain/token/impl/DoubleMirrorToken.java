package model.domain.token.impl;

import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;
import model.domain.board.Direction;
import model.domain.token.base.MirrorToken;
import model.domain.token.base.MutableToken;

import java.util.List;

public class DoubleMirrorToken extends MirrorToken {
    public DoubleMirrorToken() {
        super();
    }

    @Override
    public List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentBeamPositionTurn, List<PositionTurn> beamPath) {
        // A Double Mirror reflects the beam 90 degrees depending on the direction it is facing.
        Direction reflectedOutDirection = getReflectionMap().get(currentBeamPositionTurn.getOut());
        PositionTurn reflectedTurn =
                currentBeamPositionTurn.withOutwardsDirection(reflectedOutDirection);

        // update path
        List<PositionTurn> updatedPath =
                laserEngine.getBeamPathHelper().addToBeamPath(beamPath, reflectedTurn);

        // Continue the beam's travel from the current position turn
        return laserEngine.travelFrom(reflectedTurn, updatedPath);
    }

}
