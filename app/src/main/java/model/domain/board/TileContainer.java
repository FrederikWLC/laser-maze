package model.domain.board;

import model.domain.token.base.Token;

public abstract class TileContainer {
    protected int width;
    protected int height;
    protected Tile[][] tiles;

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            throw new IndexOutOfBoundsException(
                    "Tile coordinates out of bounds: (" + x + ", " + y + ")");
        }
        return tiles[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public void addToken(Token token) {
    }


}
