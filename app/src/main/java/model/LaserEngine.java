package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LaserEngine {

    public static List<PositionDirection> fire(LaserToken laser, Board board) {
        if (!laser.isActive()) {
            return List.of();
        }

        List<PositionDirection> beamPath = new ArrayList<>();
        PositionDirection currentPositionDirection = new PositionDirection(laser.getPosition(), laser.getDirection());

        return travel(currentPositionDirection, beamPath, board);
    }

    private static List<PositionDirection> travel(PositionDirection currentPositionDirection, List<PositionDirection> beamPath, Board board) {
        while (true) {
            // Move in the current direction
            currentPositionDirection = currentPositionDirection.increment();

            // Get current tile based on the new position
            Tile tile;
            try {
                tile = board.getTile(currentPositionDirection.getPosition().getX(), currentPositionDirection.getPosition().getY());
            } catch (IndexOutOfBoundsException e) {
                // If we go out of bounds, we stop the beam
                break;
            }

            if (beamPath.contains(currentPositionDirection)) {
                break; // Stop if we revisit a position-direction
            }

            if (!tile.isEmpty()) {
                beamPath = interact(tile.getToken(),currentPositionDirection, beamPath, board);
                break; // Stop if we go out of bounds
            }

            beamPath.add(currentPositionDirection);

        }

        return beamPath;
    }

    private static List<PositionDirection> interact(Token token, PositionDirection currentPositionDirection, List<PositionDirection> beamPath,Board board) {
        switch (token) {
            case CellBlockerToken cellBlocker -> {
                beamPath.add(currentPositionDirection);
                return travel(currentPositionDirection, beamPath, board);
            }
            case DoubleMirrorToken doubleMirror -> {
                switch (doubleMirror.getDirection()) {
                    case UP,
                         DOWN -> { // Here facing down or up means the mirror spans top left to bottom right (like a backslash)
                        switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                            case UP ->
                                    currentPositionDirection = currentPositionDirection.withDirection(Direction.LEFT);
                            case DOWN ->
                                    currentPositionDirection = currentPositionDirection.withDirection(Direction.RIGHT);
                            case LEFT ->
                                    currentPositionDirection = currentPositionDirection.withDirection(Direction.UP);
                            case RIGHT ->
                                    currentPositionDirection = currentPositionDirection.withDirection(Direction.DOWN);
                        }
                    }
                    case LEFT,
                         RIGHT -> { // Facing right or left means the mirror spans bottom left to top right (like a slash)
                        switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                            case UP ->
                                    currentPositionDirection = currentPositionDirection.withDirection(Direction.RIGHT);
                            case DOWN ->
                                    currentPositionDirection = currentPositionDirection.withDirection(Direction.LEFT);
                            case LEFT ->
                                    currentPositionDirection = currentPositionDirection.withDirection(Direction.DOWN);
                            case RIGHT ->
                                    currentPositionDirection = currentPositionDirection.withDirection(Direction.UP);
                        }
                    }
                }
                return travel(currentPositionDirection, beamPath, board);
            }
            case TargetMirrorToken targetMirror -> {
                switch (targetMirror.getDirection()) {
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
                return travel(currentPositionDirection, beamPath, board);
            }
            case BeamSplitterToken beamSplitter -> {
                PositionDirection currentPositionDirectionOfReflectedBeam = currentPositionDirection; // This will be the position direction of the beam reflected by the beam splitter
                switch (beamSplitter.getDirection()) {
                    case UP,
                         DOWN -> { // Here facing down or up means the mirror spans top left to bottom right (like a backslash)
                        switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                            case UP ->
                                    currentPositionDirectionOfReflectedBeam = currentPositionDirection.withDirection(Direction.LEFT);
                            case DOWN ->
                                    currentPositionDirectionOfReflectedBeam = currentPositionDirection.withDirection(Direction.RIGHT);
                            case LEFT ->
                                    currentPositionDirectionOfReflectedBeam = currentPositionDirection.withDirection(Direction.UP);
                            case RIGHT ->
                                    currentPositionDirectionOfReflectedBeam = currentPositionDirection.withDirection(Direction.DOWN);
                        }
                    }
                    case LEFT,
                         RIGHT -> { // Facing right or left means the mirror spans bottom left to top right (like a slash)
                        switch (currentPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                            case UP ->
                                    currentPositionDirectionOfReflectedBeam = currentPositionDirection.withDirection(Direction.RIGHT);
                            case DOWN ->
                                    currentPositionDirectionOfReflectedBeam = currentPositionDirection.withDirection(Direction.LEFT);
                            case LEFT ->
                                    currentPositionDirectionOfReflectedBeam = currentPositionDirection.withDirection(Direction.DOWN);
                            case RIGHT ->
                                    currentPositionDirectionOfReflectedBeam = currentPositionDirection.withDirection(Direction.UP);
                        }
                    }
                }
                // Returns the union of the entire beam path of the forward beam and then the reflected beam path (without previous path history before this split)
                List<PositionDirection> emptyBeamPath = new ArrayList<>(); // Thus, the empty beam path
                return Stream.concat(travel(currentPositionDirection, beamPath, board).stream(), travel(currentPositionDirectionOfReflectedBeam, emptyBeamPath, board).stream()).toList();
            }
            case CheckpointToken checkpoint -> {
                switch (checkpoint.getDirection()) {
                    case UP, DOWN -> { // Here facing up or down means the opening is vertical
                        switch (currentPositionDirection.getDirection()) { // if the beam passes or not depending on the direction
                            case UP, DOWN -> { // Beam passes through the opening
                                beamPath.add(currentPositionDirection);
                                return travel(currentPositionDirection, beamPath, board);
                            }
                            case LEFT, RIGHT -> {
                                return beamPath;
                            } // Beam hits non-opening side
                        }
                    }
                    case LEFT, RIGHT -> { // Here facing left or right means the opening is horizontal
                        switch (currentPositionDirection.getDirection()) { // if the beam passes or not depending on the direction
                            case UP, DOWN -> {
                                return beamPath;
                            } // Beam hits non-opening side
                            case LEFT, RIGHT -> { // Beam passes through the opening
                                beamPath.add(currentPositionDirection);
                                return travel(currentPositionDirection, beamPath, board);
                            }
                        }
                    }
                }
                return beamPath; // If the beam does not pass through the checkpoint, return the current beam path
            }

            default -> {
                // Handle other token types if necessary
                return beamPath;
            }
        }
    }
}
