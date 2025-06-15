package model;

import java.util.List;
import java.util.stream.Stream;

public class CheckpointToken extends MutableToken implements ICheckpointToken {
    public CheckpointToken() {
        super();
    }

    @Override
    public List<PositionDirection> interact(PositionDirection currentBeamPositionDirection, List<PositionDirection> beamPath, Board board) {
        // A Checkpoint allows the beam to pass through its tile without changing its direction
        // only if the beam is facing the opening of the checkpoint.
        switch (this.getDirection()) {
            case UP, DOWN -> { // Here facing up or down means the opening is vertical
                switch (currentBeamPositionDirection.getDirection()) { // if the beam passes or not depending on the direction
                    case UP, DOWN -> { // Beam passes through the opening
                        beamPath = Stream.concat(beamPath.stream(), Stream.of(currentBeamPositionDirection)).toList();
                        return LaserEngine.travel(currentBeamPositionDirection, beamPath, board);
                    }
                    case LEFT, RIGHT -> {
                        return beamPath;
                    } // Beam hits non-opening side
                }
            }
            case LEFT, RIGHT -> { // Here facing left or right means the opening is horizontal
                switch (currentBeamPositionDirection.getDirection()) { // if the beam passes or not depending on the direction
                    case UP, DOWN -> {
                        return beamPath;
                    } // Beam hits non-opening side
                    case LEFT, RIGHT -> { // Beam passes through the opening
                        beamPath = Stream.concat(beamPath.stream(), Stream.of(currentBeamPositionDirection)).toList();
                        return LaserEngine.travel(currentBeamPositionDirection, beamPath, board);
                    }
                }
            }
        }
        return beamPath; // If the beam does not pass through the checkpoint, return the current beam path
    }

    public boolean isPenetrated(PositionDirection beamPositionDirection) {
        // A Checkpoint is penetrated if the beam goes through the checkpoint parallel to its opening.
        return beamPositionDirection.getPosition().equals(this.getPosition())
                && getDirection().isParallel(this.getDirection());
    }
}

