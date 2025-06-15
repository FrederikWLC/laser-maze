package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LevelEngine {

    public static void placeRequiredToken(Level level, Token token, Position position) {
        List<Token> tokens = new ArrayList<>(level.getRequiredTokens()); // create mutable copy
        tokens.remove(token);
        BoardEngine.placeToken(level.getBoard(), token, position);
        level.setRequiredTokens(tokens);
    }

    public static boolean updateAndCheckLevelCompletionState(Level level) {
        Optional<LaserToken> optionalActiveLaser = level.getActiveLaser();
        if (optionalActiveLaser.isEmpty()) { // No active laser found
            level.setComplete(false); // Set level as incomplete
            level.setCurrentTargetNumber(0); // Reset current target number
            return false; // Return false as level is not complete
        }
        else { // Active laser found
            LaserToken activeLaser = optionalActiveLaser.get();
            // Fire laser, get beam path
            List<PositionDirection> beamPath = LaserEngine.fire(activeLaser, level.getBoard());
            // Get and set current target number
            int currentTargetNumber = LaserEngine.getTargetHitNumber(beamPath, level.getTokens());
            level.setCurrentTargetNumber(currentTargetNumber);
            // Check if level is complete
            boolean isComplete =
                    level.isRequiredTargetNumberSatisfied() & // Required target number must be satisfied
                    LaserEngine.areAllTouchRequiredTokensTouched(beamPath, level.getTokens()) & // All touch required tokens must be touched by the beam
                    LaserEngine.areAllRequiredTargetsHit(beamPath,level.getTokens()) & // All required targets must be hit
                    LaserEngine.areAllCheckpointsPenetrated(beamPath,level.getTokens()); // All checkpoints must be penetrated
            level.setComplete(isComplete); // Set level completion state
            return isComplete; // return completion state
            }
        }
}
