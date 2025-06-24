package view;

import model.domain.board.Direction;
import model.domain.token.base.ITurnableToken;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class SplitterBeamTokenRenderer extends TurnableTokenRenderer {
    private final Map<String, BufferedImage> tokenImages;

    public SplitterBeamTokenRenderer(Map<String, BufferedImage> tokenImages) {
        this.tokenImages = tokenImages;
    }

    @Override
    public void render(Graphics2D g2d, ITurnableToken token, int x, int y, int size) {
        Direction dir = token.getDirection();
        BufferedImage img = tokenImages.get("GreenMirror-"+getDirString(dir)+"_MIRROR.png");
        if (img != null) {
            g2d.drawImage(img, x, y, size, size, null);
        }
        if (token.isTurnable() && token.getDirection() != null) {
            BufferedImage overlay = tokenImages.get("turnable.png");
            if (overlay != null) {
                int overlaySize = size / 4;
                int overlayX = x + size - overlaySize - 5;
                int overlayY = y + 5;
                g2d.drawImage(overlay, overlayX, overlayY, overlaySize, overlaySize, null);
            } else {
                g2d.setColor(Color.MAGENTA);
                g2d.fillRect(x + size - 20, y + 4, 16, 16);
            }
        }
        if (token.isMovable() && token.getDirection() != null) {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x, y, size, size);
            g2d.setComposite(originalComposite);
        }
    }

    @Override
    public String getDirString(Direction dir) {
        return switch (dir) {
            case UP,DOWN -> "BACKSLASH";
            case LEFT,RIGHT -> "SLASH";
            case null -> "default";
        };
    }
}
