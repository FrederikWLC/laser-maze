package model;

import java.util.ArrayList;
import java.util.List;

public class LaserEngine {

    public List<PositionDirection> fire (LaserToken laser, Board board) {
        if (!laser.isActive()) {
            return List.of();
        }

        List<PositionDirection> beamPath = new ArrayList<>();
        PositionDirection currentPositionDirection = new PositionDirection(laser.getPosition(), laser.getDirection());

        return travel(currentPositionDirection, beamPath, board);
    }

    private List<PositionDirection> travel (PositionDirection currentPositionDirection, List<PositionDirection> beamPath, Board board) {
        while (true) {
            // Move in the current direction
            currentPositionDirection = currentPositionDirection.increment();

            // Get current tile based on the new position
            Tile tile = board.getTile(currentPositionDirection.getPosition().getX(), currentPositionDirection.getPosition().getY());

            if (tile == null || beamPath.contains(currentPositionDirection)) {
                break; // Stop if we go out of bounds, or if we revisit a position-direction
            }

            if (!tile.isEmpty()) {
                beamPath = interact(currentPositionDirection,beamPath, board, tile.getToken());
                break; // Stop if we go out of bounds
            }

            beamPath.add(currentPositionDirection);

        }

        return beamPath;
    }

    private List<PositionDirection> interact(PositionDirection currentPositionDirection, List<PositionDirection> beamPath, Board board, Token token) {
        switch (token) {
            case CellBlockerToken cellBlocker -> {
                beamPath.add(currentPositionDirection);
                return travel(currentPositionDirection, beamPath, board);
            }
            case DoubleMirrorToken doubleMirror -> {
                switch (doubleMirror.getDirection()) {
                    case UP, DOWN -> { // Here facing down or up means the mirror spans top left to bottom right (like a backslash)
                        switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                            case UP -> currentPositionDirection = currentPositionDirection.withDirection(Direction.LEFT);
                            case DOWN -> currentPositionDirection = currentPositionDirection.withDirection(Direction.RIGHT);
                            case LEFT -> currentPositionDirection = currentPositionDirection.withDirection(Direction.UP);
                            case RIGHT -> currentPositionDirection = currentPositionDirection.withDirection(Direction.DOWN);
                        }
                    }
                    case LEFT, RIGHT -> { // Facing right or left means the mirror spans bottom left to top right (like a slash)
                        switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                            case UP -> currentPositionDirection = currentPositionDirection.withDirection(Direction.RIGHT);
                            case DOWN -> currentPositionDirection = currentPositionDirection.withDirection(Direction.LEFT);
                            case LEFT -> currentPositionDirection = currentPositionDirection.withDirection(Direction.DOWN);
                            case RIGHT -> currentPositionDirection = currentPositionDirection.withDirection(Direction.UP);
                        }
                    }
                }
                beamPath.add(currentPositionDirection);
                return travel(currentPositionDirection, beamPath, board);
            }
            case TargetMirrorToken targetMirror -> {
                switch (targetMirror.getDirection()) {
                    case UP -> {
                        switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                            case UP ->  currentPositionDirection = currentPositionDirection.withDirection(Direction.LEFT);
                            case DOWN -> {beamPath.add(currentPositionDirection); return beamPath;} // Beam hits target
                            case LEFT -> {return beamPath;} // Beam hits non-mirror non-target part
                            case RIGHT -> currentPositionDirection = currentPositionDirection.withDirection(Direction.DOWN);
                        }
                    }
                    case DOWN -> {
                        switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                            case UP -> {beamPath.add(currentPositionDirection); return beamPath;} // Beam hits target
                            case DOWN -> currentPositionDirection = currentPositionDirection.withDirection(Direction.RIGHT);
                            case LEFT -> currentPositionDirection = currentPositionDirection.withDirection(Direction.UP);
                            case RIGHT -> {return beamPath;} // Beam hits non-mirror non-target part
                        }
                    }
                    case LEFT -> {
                        switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                            case UP -> currentPositionDirection = currentPositionDirection.withDirection(Direction.RIGHT);
                            case DOWN -> {return beamPath;} // Beam hits non-mirror non-target part
                            case LEFT ->  currentPositionDirection = currentPositionDirection.withDirection(Direction.DOWN);
                            case RIGHT -> {beamPath.add(currentPositionDirection); return beamPath;} // Beam hits target
                        }
                    }

                    case RIGHT -> {
                        switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                            case UP -> {return beamPath;} // Beam hits non-mirror non-target part
                            case DOWN -> currentPositionDirection = currentPositionDirection.withDirection(Direction.LEFT);
                            case LEFT -> {beamPath.add(currentPositionDirection); return beamPath;} // Beam hits target
                            case RIGHT -> currentPositionDirection = currentPositionDirection.withDirection(Direction.UP);
                        }
                    }
                }
                beamPath.add(currentPositionDirection);
                return travel(currentPositionDirection, beamPath, board);
            }

            default -> {
                // Handle other token types if necessary
                return beamPath;
            }
        }
    }
}
