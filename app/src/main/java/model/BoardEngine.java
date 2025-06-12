package model;

public class BoardEngine {

    // For
    public void placeToken(Token token, Position position, Board board) {
        Tile tile = board.getTile(position.getX(), position.getY());
        if (tile.isEmpty()) {
            tile.setToken(token);
            token.setPosition(position);
        }
    }

    public void moveToken(Token token, Position position, Board board) {
        if (token.isMutable()) {
            placeToken(token, position, board);
        }
    }

    public void turnToken(Token token, Direction direction) {
        if (token.isMutable()) {
            token.setDirection(direction);
        }
    }
}
