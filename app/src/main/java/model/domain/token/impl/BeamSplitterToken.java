package model.domain.token.impl;
import model.domain.board.Direction;
import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;
import model.domain.token.base.MirrorToken;
import model.domain.token.base.MutableToken;
import java.util.List;

public class BeamSplitterToken extends MirrorToken {
    public BeamSplitterToken() {
        super();
    }

    @Override
    public List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentBeamPositionTurn, List<PositionTurn> beamPath) {
        // A Beam Splitter splits the beam into two paths, one forward and one reflected 90 degrees depending on direction.

        // compute the reflected turn
        Direction reflectedOut =
                getReflectionMap().get(currentBeamPositionTurn.getOut());
        PositionTurn reflectedTurn =
                currentBeamPositionTurn.withOutwardsDirection(reflectedOut);

        // run each beam with its own context
        List<PositionTurn> forwardPath   =
                laserEngine.travelFrom(currentBeamPositionTurn,
                        List.of(currentBeamPositionTurn));

        List<PositionTurn> reflectedPath =
                laserEngine.travelFrom(reflectedTurn,
                        List.of(reflectedTurn));

        // merge original history + forward and reflected paths
        return laserEngine.getBeamPathHelper()
                .addAllBeamPaths(List.of(beamPath,
                        forwardPath,
                        reflectedPath));
    }
}

