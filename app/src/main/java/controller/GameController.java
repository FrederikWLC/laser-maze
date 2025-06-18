package controller;

import model.domain.board.Direction;
import model.domain.board.Position;
import model.domain.board.PositionDirection;
import model.domain.board.Tile;
import model.domain.engine.BoardEngine;
import model.domain.engine.LevelEngine;
import model.domain.level.Level;
import model.domain.token.base.ILaserToken;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.Token;

import java.util.List;

public class GameController {
    private Level level;
    private List<PositionDirection> beamPath = List.of();


    public GameController(Level level) {
        this.level = level;
    }

    public List<PositionDirection> getCurrentLaserPath() {
       return beamPath;
    }

    public void updateCurrentLaserPath() {
        beamPath = LevelEngine.fireLaserToken(level);
    }

    public void triggerLaser(boolean isActive) {
        try {
            LevelEngine.triggerLaserToken(level, isActive);
            updateCurrentLaserPath();
        } catch (Exception e) {
            System.out.println("Failed to trigger laser: " + e.getMessage());
        }
    }

    public void rotateTokenClockwise(Token token) {
        Direction current = ((ITurnableToken) token).getDirection();
        Direction next = current.rotateClockwise();
        rotateToken(token, next);
    }


    public void rotateToken(Token token, Direction direction) {
        if (token instanceof ILaserToken) {
            ((ILaserToken) token).trigger(false); // turn off laser before rotation
            updateCurrentLaserPath();
        }
        try {
            BoardEngine.turnToken((ITurnableToken) token, direction);
            System.out.println("Rotated token to " + direction);
        } catch (Exception e) {
            System.out.println("Rotation failed: " + e.getMessage());
        }

    }

    public Token getTokenAt(Position pos) {
        Tile tile = level.getBoard().getTile(pos.getX(), pos.getY());
        return tile != null ? tile.getToken() : null;
    }


    public Level getLevel() {
        return level;
    }
}
