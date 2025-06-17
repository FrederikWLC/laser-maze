package model.domain.token;

import model.domain.engine.LaserEngine;
import model.domain.board.PositionDirection;
import model.domain.board.Board;
import model.domain.board.Direction;

import java.util.List;

public class DoubleMirrorToken extends MutableToken {
    public DoubleMirrorToken() {
        super();
    }

    @Override
    public List<PositionDirection> interact(PositionDirection currentBeamPositionDirection, List<PositionDirection> beamPath, Board board) {
        // A Double Mirror reflects the beam 90 degrees depending on the direction it is facing.
        switch (this.getDirection()) {
            case UP,
                 DOWN -> { // Here facing down or up means the mirror spans top left to bottom right (like a backslash)
                switch (currentBeamPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.LEFT);
                    case DOWN ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.RIGHT);
                    case LEFT ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.UP);
                    case RIGHT ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.DOWN);
                }
            }
            case LEFT,
                 RIGHT -> { // Facing right or left means the mirror spans bottom left to top right (like a slash)
                switch (currentBeamPositionDirection.getDirection()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.RIGHT);
                    case DOWN ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.LEFT);
                    case LEFT ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.DOWN);
                    case RIGHT ->
                            currentBeamPositionDirection = currentBeamPositionDirection.withDirection(Direction.UP);
                }
            }
        }
        return LaserEngine.travel(currentBeamPositionDirection, beamPath, board);
    }

}
