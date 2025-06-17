package view;

import model.domain.board.Direction;


public class RenderableTile {
    private final int x;
    private final int y;
    private final String tokenType;
    private final Direction direction;

    public RenderableTile(int x, int y, String tokenType, Direction direction) {
        this.x = x;
        this.y = y;
        this.tokenType = tokenType;
        this.direction = direction;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getTokenType() { return tokenType; }
    public Direction getDirection() { return direction; }
}

