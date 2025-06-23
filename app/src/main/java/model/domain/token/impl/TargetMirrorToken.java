package model.domain.token.impl;
import model.domain.board.Direction;
import model.domain.board.PositionTurn;
import model.domain.engine.LaserEngine;
import model.domain.token.base.MutableToken;
import model.domain.token.base.ITargetToken;

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
    public List<PositionTurn> interact(LaserEngine laserEngine, PositionTurn currentBeamPositionTurn, List<PositionTurn> beamPath) {
        // A Target Mirror reflects the beam only on two of its four sides, depending on the direction of the beam.
        boolean isBeamStopped = false;
        switch (this.getDirection()) {
            case UP -> {
                switch (currentBeamPositionTurn.getOut()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.LEFT);
                    case DOWN -> { // Beam lights up target
                        laserEngine.addTargetLit(this);
                        isBeamStopped = true;
                    }
                    case LEFT -> { // Beam hits non-mirror non-target part
                        isBeamStopped = true;
                    }
                    case RIGHT ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.DOWN);
                }
            }
            case DOWN -> {
                switch (currentBeamPositionTurn.getOut()) { // where beam then gets reflected depending on the direction
                    case UP -> { // Beam lights up target
                        laserEngine.addTargetLit(this);
                        isBeamStopped = true;
                    }
                    case DOWN ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.RIGHT);
                    case LEFT ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.UP);
                    case RIGHT -> { // Beam hits non-mirror non-target part
                        isBeamStopped = true;
                    }
                }
            }
            case LEFT -> {
                switch (currentBeamPositionTurn.getOut()) { // where beam then gets reflected depending on the direction
                    case UP ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.RIGHT);
                    case DOWN -> { // Beam hits non-mirror non-target part
                        isBeamStopped = true;
                    }
                    case LEFT ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.DOWN);
                    case RIGHT -> { // Beam lights up target
                        laserEngine.addTargetLit(this);
                        isBeamStopped = true;
                    }
                }
            }

            case RIGHT -> {
                switch (currentBeamPositionTurn.getOut()) { // where beam then gets reflected depending on the direction
                    case UP -> { // Beam hits non-mirror non-target part
                        isBeamStopped = true;
                    }
                    case DOWN ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.LEFT);
                    case LEFT -> { // Beam hits target
                        laserEngine.addTargetLit(this);
                        isBeamStopped = true;
                    }
                    case RIGHT ->
                            currentBeamPositionTurn = currentBeamPositionTurn.withOutwardsDirection(Direction.UP);
                }
            }
        }
        if (isBeamStopped) {
            return beamPath;
        }
        return laserEngine.travelFrom(currentBeamPositionTurn, beamPath);
    }

}

