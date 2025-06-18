package view;

import model.domain.board.Direction;
import model.domain.token.ITurnableToken;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public class LaserTokenRenderer extends TurnableTokenRenderer {
    private final Map<String, BufferedImage> tokenImages;

    public LaserTokenRenderer(Map<String, BufferedImage> tokenImages) {
        this.tokenImages = tokenImages;
    }

    @Override
    public void render(Graphics2D g2d, ITurnableToken token, int x, int y, int size) {
        Direction dir = token.getDirection();
        String key = "RedLaser-GENERATOR_ON_" + getDirString(dir) + ".png";

        BufferedImage img = tokenImages.get(key);

        if (img != null) {
            g2d.drawImage(img, x, y, size, size, null);
        } else {
            g2d.setColor(java.awt.Color.MAGENTA);
            g2d.fillRect(x, y, size, size); // fallback
        }
    }
}
