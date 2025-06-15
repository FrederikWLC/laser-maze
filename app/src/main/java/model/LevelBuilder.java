package model;

import java.util.List;

public final class LevelBuilder {
    private final int id;
    private int width = 5; // default board width
    private int height = 5; // default board height
    private List<Token> preplacedTokens = List.of();
    private List<Token> requiredTokens = List.of();
    private int requiredTargetNumber;

    public LevelBuilder(int id) { this.id = id; }

    public LevelBuilder withBoardDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public LevelBuilder withPreplaced(List<Token> tokens) {
        this.preplacedTokens = tokens; return this;
    }
    public LevelBuilder withRequired(List<Token> tokens) {
        this.requiredTokens = tokens; return this;
    }
    public LevelBuilder withRequiredTargetNumber(int n) {
        this.requiredTargetNumber = n; return this;
    }

    public Level build() {
        Board board = new BoardBuilder().
                withDimensions(width,height).
                withPreplacedTokens(preplacedTokens).build();
        Level lvl = new Level(id,board);
        lvl.setPreplacedTokens(preplacedTokens);
        lvl.setRequiredTokens(requiredTokens);
        lvl.setRequiredTargetNumber(requiredTargetNumber);
        return lvl;

    }
}