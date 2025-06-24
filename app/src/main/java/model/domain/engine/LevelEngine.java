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
            // Get and set current target number
            int currentTargetNumber = laserEngine.getTargetLitNumber();
            level.setCurrentTargetNumber(currentTargetNumber);
            // Check if level is complete
            System.out.println("Is required target number satisfied? "+level.isRequiredTargetNumberSatisfied());
            System.out.println("Current target number is: "+level.getCurrentTargetNumber());
            System.out.println("Are all touch required tokens hit? "+areAllTouchRequiredTokensHit());
            System.out.println("Are all required targets hit? "+areAllRequiredTargetsHit());
            System.out.println("Are all checkpoints checked? "+areAllCheckpointsChecked());
            boolean isComplete =
                    level.isRequiredTargetNumberSatisfied() &
                    areAllTouchRequiredTokensHit() &
                    areAllRequiredTargetsHit() &
                    areAllCheckpointsChecked();
            level.setComplete(isComplete);
            return isComplete;
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
