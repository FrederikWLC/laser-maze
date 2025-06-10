package model;

public class Tile {
    private final int x;
    private final int y;
    private Object token = null; // placeholder until Token class is ready

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isEmpty() {
        return token == null;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
