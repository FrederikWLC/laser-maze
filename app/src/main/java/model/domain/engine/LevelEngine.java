package model.domain.engine;

import model.domain.board.Board;
import model.domain.board.Position;
import model.domain.board.PositionDirection;
import model.domain.token.base.Token;
import model.domain.level.Level;
import model.domain.token.impl.LaserToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LevelEngine {

    Level level;
    private LaserEngine laserEngine;

    public LevelEngine(Level level) {
        this.level = level;
        this.laserEngine = new LaserEngine(level.getLaserToken(),level.getBoard());
    }

    public LaserEngine getLaserEngine() {
        return laserEngine;
    }

    public Level getLevel() {
        return level;
    }

    public void triggerLaserToken(boolean isActive) {
        Optional<LaserToken> optionalLaserToken = level.getTriggerableLaser();
        if (optionalLaserToken.isEmpty()) {
            throw new IllegalStateException("No triggerable laser token found to trigger.");
        }
        LaserToken laserToken = optionalLaserToken.get();
        laserToken.trigger(isActive);
    }

    public boolean updateAndCheckLevelCompletionState() {
        Optional<LaserToken> optionalActiveLaser = level.getActiveLaser();
        if (optionalActiveLaser.isEmpty()) { // No active laser found
            level.setComplete(false); // Set level as incomplete
            level.setCurrentTargetNumber(0); // Reset current target number
            return false; // Return false as level is not complete
        }
        else { // Active laser found
            LaserToken activeLaser = optionalActiveLaser.get();
            // Fire laser, get beam path
            laserEngine.fire();
            // Get and set current target number
            int currentTargetNumber = laserEngine.getTargetLitNumber();
            level.setCurrentTargetNumber(currentTargetNumber);
            // Check if level is complete
            boolean isComplete =
                    level.isRequiredTargetNumberSatisfied() & // Required target number must be satisfied
                    laserEngine.areAllTouchRequiredTokensTouched(beamPath, level.getTokens()) & // All touch required tokens must be touched by the beam
                    laserEngine.areAllRequiredTargetsHit(beamPath,level.getTokens()) & // All required targets must be hit
                    laserEngine.areAllCheckpointsPenetrated(beamPath,level.getTokens()); // All checkpoints must be penetrated
            level.setComplete(isComplete); // Set level completion state
            return isComplete; // return completion state
            }
        }
}
