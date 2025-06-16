package view;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;


public class EmptyTileRenderer implements Drawable {
    private final BufferedImage emptyTile;

    public EmptyTileRenderer(BufferedImage emptyTile) {
        this.emptyTile = emptyTile;
    }

    @Override
    public void draw(Graphics2D g2d) {
        int tileSize = 80;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                int x = 100 + col * tileSize;
                int y = 100 + row * tileSize;

                if (emptyTile != null) {
                    g2d.drawImage(emptyTile, x, y, tileSize, tileSize, null);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillRect(x, y, tileSize, tileSize);
                }

                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, tileSize, tileSize);
            }
        }
    }
}

