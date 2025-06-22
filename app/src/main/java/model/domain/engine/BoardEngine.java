package model.domain.engine;

import model.domain.board.Board;
import model.domain.board.Direction;
import model.domain.board.Position;
import model.domain.board.Tile;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.Token;

import java.util.List;

public class BoardEngine  {

    // For
    public void placeToken(Board board, Token token, Position position) {
        Tile tile = board.getTile(position.getX(), position.getY());
        if (tile.isEmpty()) {
            tile.setToken(token);
            token.setPosition(position);
        }
    }

    public void setPreplacedTokens(Board board, List<Token> preplacedTokens) {
        for (int i = 0; i < preplacedTokens.size(); i++) {
            Token token = preplacedTokens.get(i);
            placeToken(board, token, token.getPosition());
        }
    }

    public void moveToken(Board board, Token token, Position position) {
        if (token.isMovable()) {
            placeToken(board, token, position);
        }
        else {
            throw new IllegalArgumentException("Token is not movable");
        }
    }

    public void turnToken(ITurnableToken token, Direction direction) {
        if (token.isTurnable()) {
            token.setDirection(direction);
        }
        else {
            throw new IllegalArgumentException("Token is not turnable");
        }
    }
}
