package model.domain.engine;

import model.domain.board.Position;
import model.domain.board.PositionDirection;
import model.domain.token.Token;
import model.domain.level.Level;
import model.domain.token.LaserToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LevelEngine {

    public static List<PositionDirection> fireLaserToken(Level level) {
        Optional<LaserToken> optionalLaserToken = level.getTriggerableLaser();
        if (optionalLaserToken.isEmpty()) {
            throw new IllegalStateException("No triggerable laser token found to fire.");
        }
        LaserToken laserToken = optionalLaserToken.get();
        return LaserEngine.fire(laserToken, level.getBoard());
    }

    public static void triggerLaserToken(Level level, boolean isActive) {
        Optional<LaserToken> optionalLaserToken = level.getTriggerableLaser();
        if (optionalLaserToken.isEmpty()) {
            throw new IllegalStateException("No triggerable laser token found to trigger.");
        }
        LaserToken laserToken = optionalLaserToken.get();
        laserToken.trigger(isActive);
    }

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
