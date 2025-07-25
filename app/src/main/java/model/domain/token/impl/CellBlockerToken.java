package model.domain.token.impl;

import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;
import model.domain.token.base.Token;
import java.util.List;

public class CellBlockerToken extends Token {
    public CellBlockerToken() {
        super();
    }

    @Override
    public List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentPositionTurn, List<PositionTurn> beamPath) {
        // A Cell Blocker allows the beam to pass through its tile without changing its direction.
        beamPath = laserEngine.getBeamPathHelper().addToBeamPath(beamPath,currentPositionTurn);
        // and continue the beam's travel
        return laserEngine.travelFrom(currentPositionTurn, beamPath);
    }

    @Override
    public boolean isTouchRequired() {
        return false;
    }
}

