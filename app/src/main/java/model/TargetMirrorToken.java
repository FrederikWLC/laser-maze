package model;


import java.util.List;

public class TargetMirrorToken extends MutableToken implements ITargetToken {
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
    public List<PositionDirection> interact(PositionDirection currentBeamPositionDirection, List<PositionDirection> beamPath, Board board) {
        // A Target Mirror reflects the beam only on two of its four sides, depending on the direction of the beam.
        switch (this.getDirection()) {
            case UP -> {
                switch (currentBeamPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.LEFT);
                    case DOWN -> {
                        return beamPath;
                    } // Beam hits target
                    case LEFT -> {
                        return beamPath;
                    } // Beam hits non-mirror non-target part
                    case RIGHT ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.DOWN);
                }
            }
            case DOWN -> {
                switch (currentBeamPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                    case UP -> {
                        return beamPath;
                    } // Beam hits target
                    case DOWN ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.RIGHT);
                    case LEFT ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.UP);
                    case RIGHT -> {
                        return beamPath;
                    } // Beam hits non-mirror non-target part
                }
            }
            case LEFT -> {
                switch (currentBeamPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.RIGHT);
                    case DOWN -> {
                        return beamPath;
                    } // Beam hits non-mirror non-target part
                    case LEFT ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.DOWN);
                    case RIGHT -> {
                        return beamPath;
                    } // Beam hits target
                }
            }

            case RIGHT -> {
                switch (currentBeamPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                    case UP -> {
                        return beamPath;
                    } // Beam hits non-mirror non-target part
                    case DOWN ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.LEFT);
                    case LEFT -> {
                        return beamPath;
                    } // Beam hits target
                    case RIGHT ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.UP);
                }
            }
        }
        return LaserEngine.travel(currentBeamPositionDirection, beamPath, board);
    }

    public boolean isHit(PositionDirection beamPositionDirection) {
        // A Target Mirror is hit if the beam is facing the target side of the mirror.
        PositionDirection targetPositionDirection = new PositionDirection(this.position, this.direction);
        return targetPositionDirection.increment().opposite().equals(beamPositionDirection);
    }
}

