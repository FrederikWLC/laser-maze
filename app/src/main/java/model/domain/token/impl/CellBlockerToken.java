package model.domain.token.impl;

import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;
import model.domain.board.PositionDirection;
import model.domain.board.Board;
import model.domain.token.base.Token;

import java.util.List;
import java.util.stream.Stream;

public class CellBlockerToken extends Token {
    public CellBlockerToken() {
        super();
    }

    @Override
    public List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentPositionTurn, List<PositionTurn> beamPath) {
        // A Cell Blocker allows the beam to pass through its tile without changing its direction.
        beamPath = beamPathHelper.addToBeamPath(beamPath,currentPositionTurn);
        // and continue the beam's travel
        return laserEngine.travelFrom(currentPositionTurn, beamPath);
    }

    @Override
    public boolean isTouchRequired() {
        return false;
    }
}

