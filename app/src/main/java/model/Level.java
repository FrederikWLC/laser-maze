package model;

import java.util.List;

public class Level {
    int id;
    boolean completed = false;
    int requiredTargetNumber;
    Board board;
    List<Token> tokens;
    List<Token> requiredTokens;

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

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setRequiredTokens(List<Token> requiredTokens) {
        this.requiredTokens = requiredTokens;
    }

    public void setRequiredTargetNumber(int requiredTargetNumber) {
        this.requiredTargetNumber = requiredTargetNumber;
    }

}

