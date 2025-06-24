package view;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundRenderer implements Drawable {

    private final BufferedImage image;
    private final Color fallbackColor;

    public BackgroundRenderer(BufferedImage image) {
        this.image = image;
        this.fallbackColor = Color.WHITE;
    }
    // Overloaded constructor for solid color backgrounds
    public BackgroundRenderer(Color fallbackColor) {
        this.image = null;
        this.fallbackColor = fallbackColor;
    }

    @Override
    public void draw(Graphics2D g) {
        if (image != null) {
            g.drawImage(image, 0, 0, 800, 600, null);
        } else {
            g.setColor(fallbackColor);
            g.fillRect(0, 0, 800, 600);
        }
    }
}
