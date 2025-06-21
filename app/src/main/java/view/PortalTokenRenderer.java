// view/PortalTokenRenderer.java
package view;

import model.domain.board.Direction;
import model.domain.token.base.ITurnableToken;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class PortalTokenRenderer extends TurnableTokenRenderer {
    private final Map<String, BufferedImage> tokenImages;

    public PortalTokenRenderer(Map<String, BufferedImage> tokenImages) {
        this.tokenImages = tokenImages;
    }

    @Override
    public void render(Graphics2D g2d, ITurnableToken token, int x, int y, int size) {
        Direction dir = token.getDirection();
        String key = "PortalToken-" + getDirString(dir) + ".png";

        BufferedImage img = tokenImages.get(key);
        if (img != null) {
            g2d.drawImage(img, x, y, size, size, null);
        } else {
            // Fallback: cyan circle
            g2d.setColor(Color.CYAN);
            g2d.fillOval(x, y, size, size);
        }

        // Optional overlay for turnable or movable
        if (token.isTurnable() && dir != null) {
            BufferedImage overlay = tokenImages.get("turnable.png");
            if (overlay != null) {
                int overlaySize = size / 4;
                int overlayX = x + size - overlaySize - 5;
                int overlayY = y + 5;
                g2d.drawImage(overlay, overlayX, overlayY, overlaySize, overlaySize, null);
            }
        }

        if (token.isMovable()) {
            Composite original = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x, y, size, size);
            g2d.setComposite(original);
        }
    }
}
