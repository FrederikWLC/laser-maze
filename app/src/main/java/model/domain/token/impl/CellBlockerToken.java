package model.domain.token.impl;

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
    public List<PositionDirection> interact(LaserEngine laserEngine, PositionDirection currentBeamPositionDirection, List<PositionDirection> beamPath, Board board) {
        // A Cell Blocker allows the beam to pass through its tile without changing its direction.
        beamPath = Stream.concat(beamPath.stream(), Stream.of(currentBeamPositionDirection)).toList();
        return laserEngine.travel(currentBeamPositionDirection, beamPath, board);
    }

    @Override
    public boolean isTouchRequired() {
        return false;
    }
}

