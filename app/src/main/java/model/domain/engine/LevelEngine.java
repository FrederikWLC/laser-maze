package model.domain.engine;


import model.domain.token.base.ICheckpointToken;
import model.domain.token.base.ITargetToken;
import model.domain.token.base.Token;
import model.domain.level.Level;

import java.util.List;

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

    public boolean updateAndCheckLevelCompletionState() {
        if (getLaserEngine().getLastBeamPath().isEmpty()) { // No laser beam path
            level.setComplete(false); // Set level as incomplete
            level.setCurrentTargetNumber(0); // Reset current target number
            return false; // Return false as level is not complete
        }
        else { // There is a laser beam path
            // Get and set current target number
            int currentTargetNumber = laserEngine.getTargetLitNumber();
            level.setCurrentTargetNumber(currentTargetNumber);
            // Check if level is complete
            boolean isComplete =
                    level.isRequiredTargetNumberSatisfied() & // Required target number must be satisfied
                    areAllTouchRequiredTokensHit() & // All touch required tokens must be touched by the beam
                    areAllRequiredTargetsHit() & // All required targets must be hit
                    areAllCheckpointsChecked(); // All checkpoints must be checked
            level.setComplete(isComplete); // Set level completion state
            return isComplete; // return completion state
            }
        }

    public List<Token> getTouchRequiredTokens() {
        return level.getTokens().stream()
                .filter(Token::isTouchRequired)
                .toList();
    }

    public boolean areAllTouchRequiredTokensHit() {
        return laserEngine.getTouchRequiredTokensHit().size() == getTouchRequiredTokens().size();
    }

    public boolean areAllCheckpointsChecked() {
        int totalCheckpoints = level.getTokens().stream()
                .filter(ICheckpointToken.class::isInstance)
                .toList().size();
        return laserEngine.getCheckpointsChecked().size() == totalCheckpoints;
    }

    public boolean areAllRequiredTargetsHit() {
        return laserEngine.getRequiredTargetsLit().size() == level.getTokens().stream()
                .filter(ITargetToken.class::isInstance)
                .map(ITargetToken.class::cast)
                .filter(ITargetToken::isRequiredTarget)
                .toList().size();
    }
}
