package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BeamSplitterToken extends MutableToken {
    public BeamSplitterToken() {
        super();
    }

    @Override
    public List<PositionDirection> interact(PositionDirection currentPositionDirection, List<PositionDirection> beamPath, Board board) {
        // A Beam Splitter splits the beam into two paths, one forward and one reflected 90 degrees depending on direction.
        PositionDirection currentPositionDirectionOfReflectedBeam = currentPositionDirection; // This will be the position direction of the beam reflected by the beam splitter
        switch (this.getDirection()) {
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
        return Stream.concat(LaserEngine.travel(currentPositionDirection, beamPath, board).stream(), LaserEngine.travel(currentPositionDirectionOfReflectedBeam, emptyBeamPath, board).stream()).toList();
    }
}

