package model.domain.level;

import model.domain.board.Inventory;
import model.domain.board.TileContainer;
import model.domain.token.base.Token;
import model.domain.board.Board;
import model.domain.token.impl.LaserToken;

import java.util.List;
import java.util.Optional;

public class Level {
    int id;
    boolean complete = false;
    int requiredTargetNumber;
    int currentTargetNumber = 0;
    Board board;
    List<Token> tokens = List.of();
    private TileContainer inventory;

    public Level(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public List<Token> getTokens() {
        return tokens;
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

    public LaserToken getLaserToken() {
        return getTokens().stream()
                .filter(LaserToken.class::isInstance)
                .map(LaserToken.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No LaserToken found in the level."));
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

    public void setInventory(TileContainer inventory) {
        this.inventory = inventory;
    }
    public TileContainer getInventory() {
        return inventory;
    }


}
