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

import javax.swing.*;
import java.util.List;

public class GameController {
    LevelEngine levelEngine = new LevelEngine();
    BoardEngine boardEngine = new BoardEngine();
    private Level level;
    private List<PositionDirection> beamPath = List.of();

    public GameController(Level level) {
        this.level = level;
    }

    public List<PositionDirection> getCurrentLaserPath() {
       return beamPath;
    }

    public void updateCurrentLaserPath() {
        beamPath = levelEngine.fireLaserToken(level);
    }

    public void triggerLaser(boolean isActive) {
        try {
            levelEngine.triggerLaserToken(level, isActive);
            updateCurrentLaserPath();
        } catch (Exception e) {
            System.out.println("Failed to trigger laser: " + e.getMessage());
        }
    }

    public void rotateTokenClockwise(ITurnableToken token) {
        if (token == null) {System.out.println("No token to rotate"); return;}
        Direction current = token.getDirection();
        if (current == null) {
            rotateToken((Token) token,Direction.UP);
        } else {
            rotateToken((Token) token,current.rotateClockwise());
        }
    }

    public void rotateToken(Token token, Direction direction) {
        if (token instanceof ILaserToken laserToken && token instanceof ITurnableToken turnableToken) {
            if (turnableToken.isTurned()) {
                try {
                    laserToken.trigger(false); // turn off the laser before making changes to the board layout
                    updateCurrentLaserPath();
                } catch (IllegalStateException e) {
                    System.out.println("Skipping laser trigger before rotation: " + e.getMessage());
                }
            }
        }
        try {
            boardEngine.turnToken((ITurnableToken) token, direction);
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
