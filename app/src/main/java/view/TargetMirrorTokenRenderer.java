package view;

import model.domain.board.Direction;
import model.domain.token.base.ITurnableToken;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.awt.Composite;
import model.domain.token.base.ITargetToken;

public class TargetMirrorTokenRenderer extends TurnableTokenRenderer {
    private final Map<String, BufferedImage> tokenImages;

    public TargetMirrorTokenRenderer(Map<String, BufferedImage> tokenImages) {
        this.tokenImages = tokenImages;
    }

    @Override
    public void render(Graphics2D g2d, ITurnableToken token, int x, int y, int size) {

        Direction dir = token.getDirection();

        String key;
        if (token instanceof ITargetToken targetToken && targetToken.isRequiredTarget()) {
            key = "PurpleTarget-TARGET_ON_" + getDirString(dir) + "-M.png";
        } else {
            key = "PurpleTarget-TARGET_ON_" + getDirString(dir) + ".png";
        }

        BufferedImage img = tokenImages.get(key);

        if (img != null) {
            g2d.drawImage(img, x, y, size, size, null);
        } else {
            g2d.setColor(java.awt.Color.MAGENTA);
            g2d.fillRect(x, y, size, size); // fallback
        }

        if (token.isTurnable() && token.getDirection() != null) {
            BufferedImage overlay = tokenImages.get("turnable.png");
            if (overlay != null) {
                int overlaySize = size / 4;  // scale down to 1/3 of the token size
                int overlayX = x + size - overlaySize - 5; // 5px padding from right
                int overlayY = y + 5; // 5px padding from top
                g2d.drawImage(overlay, overlayX, overlayY, overlaySize, overlaySize, null);
            } else {
                g2d.setColor(Color.MAGENTA);
                g2d.fillRect(x + size - 20, y + 4, 16, 16); // overlay box
            }
        }
        if (token.isMovable() && token.getDirection() != null) {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f)); // 40% transparency
            g2d.setColor(Color.BLACK); // or any dark color you'd like
            g2d.fillRect(x, y, size, size);
            g2d.setComposite(originalComposite); // Reset to original
        }


    }
}
