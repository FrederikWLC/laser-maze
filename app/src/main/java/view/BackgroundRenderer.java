package view;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundRenderer implements Drawable {

    private final BufferedImage image;

    public BackgroundRenderer(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void draw(Graphics2D g) {
        if (image != null) {
            g.drawImage(image, 0, 0, 800, 600, null);
        } else {
            g.setColor(Color.WHITE); // fallback background
            g.fillRect(0, 0, 800, 600);
        }
    }
}
