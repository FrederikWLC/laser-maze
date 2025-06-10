package model;

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
                tiles[x][y] = new Tile(x, y); // you reintroduce this
            }
        }
    }
    public Tile getTile(int x, int y){
        return tiles[x][y];
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
    
}
