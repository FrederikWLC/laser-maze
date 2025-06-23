package controller;

import model.domain.board.*;
import model.domain.engine.BoardEngine;
import model.domain.engine.LevelEngine;
import model.domain.level.Level;
import model.domain.token.base.ILaserToken;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.Token;

import java.util.List;

public class LevelController {
    LevelEngine levelEngine;
    BoardEngine boardEngine;

    public LevelController(Level level) {
        this.levelEngine = new LevelEngine(level);
        this.boardEngine = new BoardEngine();
    }

    public List<PositionTurn> getCurrentLaserPath() {
       return levelEngine.getLaserEngine().getLastBeamPath();
    }

    public void triggerLaser(boolean isActive) {
        try {
            levelEngine.getLevel().getLaserToken().trigger(isActive);
            levelEngine.getLaserEngine().fire();
        } catch (Exception e) {
            System.out.println("Failed to trigger laser: " + e.getMessage());
            throw e;
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
                    levelEngine.getLaserEngine().refreshBeamPath();
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
        Tile tile = levelEngine.getLevel().getBoard().getTile(pos.getX(), pos.getY());
        return tile != null ? tile.getToken() : null;
    }


    public LevelEngine getLevelEngine() {
        return levelEngine;
    }

}
