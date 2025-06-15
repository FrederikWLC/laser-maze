package controller;

import model.*;

import java.util.List;

public class GameController {
    private final Board board;
    private final LaserEngine laserEngine = new LaserEngine();
    private final BoardEngine boardEngine = new BoardEngine();

    public GameController(Board board) {
        this.board = board;
    }

    public List<PositionDirection> fireLaser() {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Token token = board.getTile(x, y).getToken();
                if (token instanceof LaserToken laser) {
                    laser.trigger(true);
                    return laserEngine.fire(laser, board);
                }
            }
        }
        return List.of();
    }

    public boolean rotateToken(Position pos, Direction direction) {
        Tile tile = board.getTile(pos.getX(), pos.getY());
        if (tile != null && !tile.isEmpty() && tile.getToken().isTurnable()) {
            boardEngine.turnToken((ITurnableToken) tile.getToken(), direction);
            return true;
        }
        return false;
    }



    public Token getTokenAt(Position pos) {
        Tile tile = board.getTile(pos.getX(), pos.getY());
        return tile != null ? tile.getToken() : null;
    }


    public Board getBoard() {
        return board;
    }
}
