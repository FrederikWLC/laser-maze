package model.domain.board;

import model.domain.token.base.Token;

public class Tile {
    private final int x;
    private final int y;
    private Token token = null;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isEmpty() {
        return token == null;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Token getToken() { return token; }

    public void setToken(Token token) { this.token = token; }
}
