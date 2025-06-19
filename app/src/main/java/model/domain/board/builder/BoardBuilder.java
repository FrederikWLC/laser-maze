package model.domain.board.builder;

import model.domain.board.Board;
import model.domain.engine.BoardEngine;
import model.domain.token.base.Token;

import java.util.List;

public class BoardBuilder {

    private int width = 5;
    private int height = 5;
    List<Token> preplacedTokens = List.of();

    public BoardBuilder() {}

    public BoardBuilder withDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public BoardBuilder withPreplacedTokens(List<Token> preplacedTokens) {
        this.preplacedTokens = preplacedTokens;
        return this;
    }

    public Board build() {
        Board board = new Board(width,height);
        BoardEngine.setPreplacedTokens(board, preplacedTokens);
        return board;
    }

}
