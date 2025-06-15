package model;

import java.util.List;

public class BoardEngine {


    // For
    public static void placeToken(Board board, Token token, Position position) {
        Tile tile = board.getTile(position.getX(), position.getY());
        if (tile.isEmpty()) {
            tile.setToken(token);
            token.setPosition(position);
        }
    }

    public static void setPreplacedTokens(Board board, List<Token> preplacedTokens) {
        for (int i = 0; i < preplacedTokens.size(); i++) {
            Token token = preplacedTokens.get(i);
            placeToken(board, token, token.getPosition());
        }
    }

    public static void moveToken(Board board, Token token, Position position) {
        if (token.isMovable()) {
            placeToken(board, token, position);
        }
        else {
            throw new IllegalArgumentException("Token is not movable");
        }
    }

    public static void turnToken(ITurnableToken token, Direction direction) {
        if (token.isTurnable()) {
            token.setDirection(direction);
        }
        else {
            throw new IllegalArgumentException("Token is not turnable");
        }
    }
}
