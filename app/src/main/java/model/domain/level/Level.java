package model.domain.level;

import model.domain.token.Token;
import model.domain.board.Board;
import model.domain.token.LaserToken;

import java.util.List;
import java.util.Optional;

public class Level {
    int id;
    boolean complete = false;
    int requiredTargetNumber;
    int currentTargetNumber = 0;
    Board board;
    List<Token> tokens;

    public Level(int id, Board board) {
        this.id = id;
        this.board = board;
    }

    public int getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<Token> getPlacedTokens() {
        return getTokens().stream()
                .filter(token -> token.isPlaced())
                .toList();
    }

    public boolean isRequiredTargetNumberSatisfied() {
        return getRemainingTargetCount() == 0;
    }

    public int getRemainingTargetCount() {
        return Math.max(0,getRequiredTargetNumber() - getCurrentTargetNumber());
    }

    public boolean areAllTokensPlaced() {
        return getTokens().stream()
                .allMatch(Token::isPlaced);
    }

    public Optional<LaserToken> getActiveLaser() {
        return getTriggerableLaser().filter(LaserToken::isActive);
    }

    public Optional<LaserToken> getTriggerableLaser() {
        return getPlacedTokens().stream()
                .filter(LaserToken.class::isInstance)
                .map(LaserToken.class::cast)
                .filter(LaserToken::isTriggerable)
                .findFirst();
    }


    public void setComplete(boolean complete) {
        this.complete= complete;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getRequiredTokens() {
        return getTokens().stream()
                .filter(token -> !token.isPlaced())
                .toList();
    }

    public void setRequiredTargetNumber(int requiredTargetNumber) {
        this.requiredTargetNumber = requiredTargetNumber;
    }
    public int getRequiredTargetNumber() {
        return requiredTargetNumber;
    }

    public void setCurrentTargetNumber(int currentTargetNumber) {
        this.currentTargetNumber = currentTargetNumber;
    }
    public int getCurrentTargetNumber() {
        return currentTargetNumber;
    }

}
