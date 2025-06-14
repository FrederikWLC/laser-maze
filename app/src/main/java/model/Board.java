package model;

import java.util.Optional;

public class Board {
    private final int height;
    private final int width;
    private final Tile[][]  tiles;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = new Tile(x, y);
            }
        }
    }

    public Tile getTile(int x, int y) {
        // bounds-check first
        if (x < 0 || y < 0
                || x >= width
                || y >= height) {   // assumes a rectangular board
        throw new IndexOutOfBoundsException(
                "Tile coordinates out of bounds: (" + x + ", " + y + ")");
        }
        return tiles[x][y];                  // safe access
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
    
}
