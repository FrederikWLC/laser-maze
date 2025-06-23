package model.domain.token.impl;

import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;
import model.domain.board.PositionDirection;
import model.domain.board.Board;
import model.domain.token.base.MutableToken;
import model.domain.token.base.ICheckpointToken;

import java.util.List;
import java.util.stream.Stream;

public class CheckpointToken extends MutableToken implements ICheckpointToken {
    public CheckpointToken() {
        super();
    }

    @Override
    public List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentBeamPositionTurn, List<PositionTurn> beamPath) {
        // A Checkpoint allows the beam to pass through its tile without changing its direction
        // only if the beam is facing the opening of the checkpoint.
        if (isChecked(currentBeamPositionTurn)) {
            laserEngine.addCheckpointChecked(this);
            // Add the current beam position to the beam path
            beamPath = beamPathHelper.addToBeamPath(beamPath, currentBeamPositionTurn);
            return laserEngine.travelFrom(currentBeamPositionTurn,beamPath);
        }
        return beamPath; // If the beam does not pass through the checkpoint, return the current beam path
    }

    public boolean isChecked(PositionTurn beamPositionTurn) {
        // A Checkpoint is penetrated if the beam goes through the checkpoint parallel to its opening.
        return beamPositionTurn.getPosition().equals(this.getPosition())
                && beamPositionTurn.getIn().isParallel(this.getDirection())
                && beamPositionTurn.getOut().isParallel(this.getDirection());
    }
}

