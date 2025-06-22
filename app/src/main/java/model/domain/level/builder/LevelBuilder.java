package model.domain.level.builder;

import model.domain.engine.TokenInitializerEngine;
import model.domain.level.Level;
import model.domain.token.base.Token;
import model.domain.board.Board;
import model.domain.board.builder.BoardBuilder;
import java.util.List;

public final class LevelBuilder {
    TokenInitializerEngine tokenInitializerEngine = new TokenInitializerEngine(); // Violation of OPEN/CLOSED PRINCIPLE, but whatever
    private Level level;
    private BoardBuilder boardBuilder;

    public LevelBuilder(int id) {
        this.level = new Level(id);
        this.boardBuilder = new BoardBuilder();
    }

    public LevelBuilder withBoardDimensions(int width, int height) {
        this.boardBuilder = this.boardBuilder
                .withDimensions(width, height);
        return this;
    }

    public LevelBuilder withTokens(List<Token> tokens) {
        this.level.setTokens(tokens); return this;
    }

    public LevelBuilder withRequiredTargetNumber(int n) {
        this.level.setRequiredTargetNumber(n); return this;
    }

    public LevelBuilder withCurrentTargetNumber(int n) {
        this.level.setCurrentTargetNumber(n); return this;
    }

    public LevelBuilder withComplete(boolean complete) {
        this.level.setComplete(complete); return this;
    }

    public Level build() {
        // Get all preplaced tokens from the level
        List<Token> preplacedTokens = level.getTokens().stream()
                .filter(Token::isPlaced)
                .toList();
        // Build the board with preplaced tokens
        Board board = boardBuilder
                .withPreplacedTokens(preplacedTokens)
                .build();
        level.setBoard(board);
        // Run extra initialization logic
        tokenInitializerEngine.init(level.getTokens()); // keeps SRP mostly intact
        return level;
    }
}