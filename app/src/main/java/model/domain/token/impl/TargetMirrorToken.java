package model.domain.token.impl;
import model.domain.board.Direction;
import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;
import model.domain.token.base.MirrorToken;
import model.domain.token.base.ITargetToken;

import java.util.List;

public class TargetMirrorToken extends MirrorToken implements ITargetToken {
    boolean requiredTarget = false;

    public TargetMirrorToken() {
        super();
    }

    public void setRequiredTarget(boolean requiredTarget) {
        this.requiredTarget = requiredTarget;
    }

    public boolean isRequiredTarget() {
        return requiredTarget;
    }

    @Override
    public List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentBeamPositionTurn, List<PositionTurn> beamPath) {
        // A Target Mirror reflects the beam only on two of its four sides, depending on the direction of the beam.

        // reflective face, only if the beam has same direction as target token
        // or as the "clockwisely" rotated direction of the target token
        if (currentBeamPositionTurn.getOut() == getDirection() || currentBeamPositionTurn.getOut() == getDirection().rotateClockwise()) {
            Direction out     = getReflectionMap().get(currentBeamPositionTurn.getOut());
            PositionTurn next = currentBeamPositionTurn.withOutwardsDirection(out);

            List<PositionTurn> updated =
                    laserEngine.getBeamPathHelper().addToBeamPath(beamPath, next);

            return laserEngine.travelFrom(next, updated);
        }

        // target face only if direction of beam is opposite of target direction (stop)
        if (currentBeamPositionTurn.getOut() == getDirection().opposite()) {
            laserEngine.addTargetLit(this);
            return beamPath;          // beam stops after lighting
        }

        // non-target, non-reflective face otherwise (stop)
        return beamPath;
    }

}

