package view;

import model.CellBlockerToken;
import model.IToken;
import model.Token;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public class CellBlockerTokenRenderer implements TokenRenderer {
    private final Map<String, BufferedImage> tokenImages;

    public CellBlockerTokenRenderer(Map<String, BufferedImage> tokenImages) {
        this.tokenImages = tokenImages;
    }

    @Override
    public void render(Graphics2D g2d, Token token, int x, int y, int size) {

        BufferedImage img = tokenImages.get("WhiteObstacle-NONE-Dark.png");
        if (img != null) {
            g2d.drawImage(img, x, y, size, size, null);
        } else {
            g2d.setColor(java.awt.Color.MAGENTA);
            g2d.fillRect(x, y, size, size); // fallback
        }
    }
}
