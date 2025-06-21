package view;

import model.domain.board.Direction;
import model.domain.token.base.Token;



public class RenderableTile {
    private final int x;
    private final int y;
    private final String tokenType;
    private final Direction direction;
    private boolean isTurnable;
    private boolean isMovable;
    private final Token token;
    private final boolean isRequiredTarget;

    public RenderableTile(int x, int y, String tokenType, Direction direction, Token token, boolean isTurnable, boolean isMovable, boolean isRequiredTarget) {
        this.x = x;
        this.y = y;
        this.tokenType = tokenType;
        this.direction = direction;
        this.token = token;
        this.isTurnable = isTurnable;
        this.isMovable = isMovable;
        this.isRequiredTarget = isRequiredTarget;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getTokenType() { return tokenType; }
    public Direction getDirection() { return direction; }



    public boolean isTurnable() {
        return isTurnable;
    }
    public boolean isMovable() {
        return isMovable;
    }

    public void setTurnable(boolean isTurnable) {
        this.isTurnable = isTurnable;
    }
    public Token getToken() { return token; }

    public boolean isRequiredTarget() {
        return isRequiredTarget;
    }

}

