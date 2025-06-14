package model;

import java.util.List;

public class Level {
    int id;
    List<Token> preplacedTokens;
    List<Token> requiredTokens;
    boolean completed = false;
    int requiredTargetNumber;
    Board board;

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

    public List<Token> getPreplacedTokens() {
        return preplacedTokens;
    }

    public List<Token> getRequiredTokens() {
        return requiredTokens;
    }

    public int getRequiredTargetNumber() {
        return requiredTargetNumber;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setPreplacedTokens(List<Token> preplacedTokens) {
        this.preplacedTokens = preplacedTokens;
    }

    public void setRequiredTokens(List<Token> requiredTokens) {
        this.requiredTokens = requiredTokens;
    }

    public void setRequiredTargetNumber(int requiredTargetNumber) {
        this.requiredTargetNumber = requiredTargetNumber;
    }

}

