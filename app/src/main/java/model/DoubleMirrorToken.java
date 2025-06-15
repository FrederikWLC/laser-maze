package model;

import java.util.List;

public class DoubleMirrorToken extends MutableToken {
    public DoubleMirrorToken() {
        super();
    }

    @Override
    public List<PositionDirection> interact(PositionDirection currentPositionDirection, List<PositionDirection> beamPath, Board board) {
        // A Double Mirror reflects the beam 90 degrees depending on the direction it is facing.
        switch (this.getDirection()) {
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
        return LaserEngine.travel(currentPositionDirection, beamPath, board);
    }

}
