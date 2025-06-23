package model.domain.token.impl;
import model.domain.board.Direction;
import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;
import model.domain.token.base.MutableToken;
import java.util.List;

public class BeamSplitterToken extends MutableToken {
    public BeamSplitterToken() {
        super();
    }

    @Override
    public List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentBeamPositionTurn, List<PositionTurn> beamPath) {
        // A Beam Splitter splits the beam into two paths, one forward and one reflected 90 degrees depending on direction.
        PositionTurn currentPositionTurnOfReflectedBeam = currentBeamPositionTurn; // This will be the position turn of the beam reflected by the beam splitter
        switch (this.getDirection()) {
            case UP,
                 DOWN -> { // Here facing down or up means the mirror spans top left to bottom right (like a backslash)
                switch (currentBeamPositionTurn.getOut()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentPositionTurnOfReflectedBeam = currentBeamPositionTurn.withOutwardsDirection(Direction.LEFT);
                    case DOWN ->
                            currentPositionTurnOfReflectedBeam = currentBeamPositionTurn.withOutwardsDirection(Direction.RIGHT);
                    case LEFT ->
                            currentPositionTurnOfReflectedBeam = currentBeamPositionTurn.withOutwardsDirection(Direction.UP);
                    case RIGHT ->
                            currentPositionTurnOfReflectedBeam = currentBeamPositionTurn.withOutwardsDirection(Direction.DOWN);
                }
            }
            case LEFT,
                 RIGHT -> { // Facing right or left means the mirror spans bottom left to top right (like a slash)
                switch (currentBeamPositionTurn.getOut()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentPositionTurnOfReflectedBeam = currentBeamPositionTurn.withOutwardsDirection(Direction.RIGHT);
                    case DOWN ->
                            currentPositionTurnOfReflectedBeam = currentBeamPositionTurn.withOutwardsDirection(Direction.LEFT);
                    case LEFT ->
                            currentPositionTurnOfReflectedBeam = currentBeamPositionTurn.withOutwardsDirection(Direction.DOWN);
                    case RIGHT ->
                            currentPositionTurnOfReflectedBeam = currentBeamPositionTurn.withOutwardsDirection(Direction.UP);
                }
            }
        }
        // Returns the union of the entire beam path of the forward beam and then the reflected beam path (without previous path history before this split)
        // Thus, the empty beam path

        // Beam path context of the forward beam only includes the current beam position turn
        List<PositionTurn> beamPathContextForward = List.of(currentBeamPositionTurn);

        // Beam path context of the reflected beam only includes the reflected beam position turn
        List<PositionTurn> beamPathContextReflected = List.of(currentBeamPositionTurn); // Add the current beam position turn to the beam path history

        // Get the beam path of the forward beam and the reflected beam with their own respective contexts
        List<PositionTurn> forwardBeamPath = laserEngine.travelFrom(currentBeamPositionTurn, beamPathContextForward);
        List<PositionTurn> reflectedBeamPath = laserEngine.travelFrom(currentPositionTurnOfReflectedBeam, beamPathContextReflected);

        // Add them together with also the context of the original beam path
        return beamPathHelper.addAllBeamPaths(List.of(beamPath,forwardBeamPath,reflectedBeamPath));
    }
}

