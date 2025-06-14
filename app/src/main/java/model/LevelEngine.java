package model;

import java.util.ArrayList;
import java.util.List;

public class LevelEngine {

    public static void setRequiredTokens(Level level, List<Token> requiredTokens) {
        level.setRequiredTokens(requiredTokens);
    }

    public static void setPreplacedTokens(Level level, List<Token> preplacedTokens) {
        BoardEngine.setPreplacedTokens(level.getBoard(), preplacedTokens);
        level.setPreplacedTokens(preplacedTokens);
    }

    public static void setRequiredTargetNumber(Level level, int requiredTargetNumber) {
        level.setRequiredTargetNumber(requiredTargetNumber);
    }

    public static void placeRequiredToken(Level level, Token token, Position position) {
        List<Token> tokens = new ArrayList<>(level.getRequiredTokens()); // create mutable copy
        tokens.remove(token);
        BoardEngine.placeToken(level.getBoard(), token, position);
        level.setRequiredTokens(tokens);
    }

    public static void updateCompletionState(Level level) {
        boolean isCompleted = checkLevelCompleted(level);
        level.setCompleted(isCompleted);
    }
    private static boolean checkLevelCompleted(Level level) {
        // to be completed, all required tokens must be present
        // and all targets must be satisfied
        return false;
    }

}
