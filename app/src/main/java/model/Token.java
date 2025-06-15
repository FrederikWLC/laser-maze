package model;

import java.util.List;

public abstract class Token implements IToken {
    protected Position position;

    protected Token() {}

    public Position getPosition() {
        return position;
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

    public List<PositionDirection> interact(PositionDirection currentPositionDirection, List<PositionDirection> beamPath, Board board) {
        return beamPath; // Default implementation returns the beam path unchanged, as beam hits token and stops
    }
}
