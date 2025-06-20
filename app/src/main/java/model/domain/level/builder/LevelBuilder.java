package model.domain.level.builder;

import model.domain.level.Level;
import model.domain.token.base.Token;
import model.domain.board.Board;
import model.domain.board.builder.BoardBuilder;

import java.util.List;
import java.util.stream.Stream;

public final class LevelBuilder {
    private final int id;
    private int width = 5; // default board width
    private int height = 5; // default board height
    private List<Token> tokens = List.of();
    private int requiredTargetNumber = 1;

    public LevelBuilder(int id) { this.id = id; }

    public LevelBuilder withBoardDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public LevelBuilder withTokens(List<Token> tokens) {
        this.tokens = tokens; return this;
    }
    public LevelBuilder withRequiredTargetNumber(int n) {
        this.requiredTargetNumber = n; return this;
    }

    public Level build() {
        List<Token> preplacedTokens = tokens.stream()
                .filter(Token::isPlaced)
                .toList();
        Board board = new BoardBuilder().
                withDimensions(width,height).
                withPreplacedTokens(preplacedTokens).build();
        Level lvl = new Level(id,board);
        lvl.setTokens(tokens);
        lvl.setRequiredTargetNumber(requiredTargetNumber);
        return lvl;

    }
}