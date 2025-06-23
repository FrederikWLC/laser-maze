package model.domain.token.impl;

import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;
import model.domain.board.Direction;
import model.domain.token.base.MutableToken;

import java.util.List;

public class DoubleMirrorToken extends MutableToken {
    public DoubleMirrorToken() {
        super();
    }

    @Override
    public List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentBeamPositionTurn, List<PositionTurn> beamPath) {
        // A Double Mirror reflects the beam 90 degrees depending on the direction it is facing.
        switch (this.getDirection()) {
            case UP,
                 DOWN -> { // Here facing down or up means the mirror spans top left to bottom right (like a backslash)
                switch (currentBeamPositionTurn.getOut()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.LEFT);
                    case DOWN ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.RIGHT);
                    case LEFT ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.UP);
                    case RIGHT ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.DOWN);
                }
            }
            case LEFT,
                 RIGHT -> { // Facing right or left means the mirror spans bottom left to top right (like a slash)
                switch (currentBeamPositionTurn.getOut()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.RIGHT);
                    case DOWN ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.LEFT);
                    case LEFT ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.DOWN);
                    case RIGHT ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.UP);
                }
            }
        }
        // Add the turn within the mirror to the beam path
        beamPath = laserEngine.getBeamPathHelper().addToBeamPath(beamPath, currentBeamPositionTurn);

        // Continue the beam's travel from the current position turn
        return laserEngine.travelFrom(currentBeamPositionTurn, beamPath);
    }

}
