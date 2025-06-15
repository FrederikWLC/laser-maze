package model;

import java.util.List;
import java.util.stream.Stream;

public class CellBlockerToken extends Token {
    public CellBlockerToken() {
        super();
    }

    @Override
    public List<PositionDirection> interact(PositionDirection currentBeamPositionDirection, List<PositionDirection> beamPath, Board board) {
        // A Cell Blocker allows the beam to pass through its tile without changing its direction.
        beamPath = Stream.concat(beamPath.stream(), Stream.of(currentBeamPositionDirection)).toList();
        return LaserEngine.travel(currentBeamPositionDirection, beamPath, board);
    }

    @Override
    public boolean isTouchRequired() {
        return false;
    }
}

