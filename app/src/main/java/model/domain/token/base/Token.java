package model.domain.token.base;
import model.domain.board.Position;
import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;
import model.domain.engine.util.BeamPathHelper;

import java.util.List;

public abstract class Token implements IToken {
    protected Position position;
    protected BeamPathHelper beamPathHelper;

    protected Token() {
    }


    public boolean isMovable() {
        return false;
    }

    public boolean isTurnable() {
        return false;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isPlaced() {
        return position != null; // Token is placed if it has a position
    }

    public List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentPositionTurn, List<PositionTurn> beamPath) {
        // return the beam path by default, this will stop the beam
        return beamPath;
    }

    public boolean isTouchRequired() {
        return true; // Default implementation, can be overridden by subclasses
    }

}
