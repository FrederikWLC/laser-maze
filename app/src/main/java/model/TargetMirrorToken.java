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
    public List<PositionDirection> interact(PositionDirection currentPositionDirection, List<PositionDirection> beamPath, Board board) {
        // A Target Mirror reflects the beam only on two of its four sides, depending on the direction of the beam.
        switch (this.getDirection()) {
            case UP -> {
                switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentPositionDirection = currentPositionDirection.withDirection(Direction.LEFT);
                    case DOWN -> {
                        return beamPath;
                    } // Beam hits target
                    case LEFT -> {
                        return beamPath;
                    } // Beam hits non-mirror non-target part
                    case RIGHT ->
                            currentPositionDirection = currentPositionDirection.withDirection(Direction.DOWN);
                }
            }
            case DOWN -> {
                switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                    case UP -> {
                        return beamPath;
                    } // Beam hits target
                    case DOWN ->
                            currentPositionDirection = currentPositionDirection.withDirection(Direction.RIGHT);
                    case LEFT ->
                            currentPositionDirection = currentPositionDirection.withDirection(Direction.UP);
                    case RIGHT -> {
                        return beamPath;
                    } // Beam hits non-mirror non-target part
                }
            }
            case LEFT -> {
                switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentPositionDirection = currentPositionDirection.withDirection(Direction.RIGHT);
                    case DOWN -> {
                        return beamPath;
                    } // Beam hits non-mirror non-target part
                    case LEFT ->
                            currentPositionDirection = currentPositionDirection.withDirection(Direction.DOWN);
                    case RIGHT -> {
                        return beamPath;
                    } // Beam hits target
                }
            }

            case RIGHT -> {
                switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                    case UP -> {
                        return beamPath;
                    } // Beam hits non-mirror non-target part
                    case DOWN ->
                            currentPositionDirection = currentPositionDirection.withDirection(Direction.LEFT);
                    case LEFT -> {
                        return beamPath;
                    } // Beam hits target
                    case RIGHT ->
                            currentPositionDirection = currentPositionDirection.withDirection(Direction.UP);
                }
            }
        }
        return LaserEngine.travel(currentPositionDirection, beamPath, board);
    }

}

