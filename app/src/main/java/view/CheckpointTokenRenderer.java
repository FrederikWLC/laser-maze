package view;

import model.domain.token.base.ITurnableToken;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import model.domain.board.Direction;
import java.awt.Color;


public class CheckpointTokenRenderer extends TurnableTokenRenderer {
    private final Map<String, BufferedImage> tokenImages;

    public CheckpointTokenRenderer(Map<String, BufferedImage> tokenImages) {
        this.tokenImages = tokenImages;
    }

    @Override
    public void render(Graphics2D g2d, ITurnableToken token, int x, int y, int size) {
        if (!(token instanceof ITurnableToken turnable)) {
            g2d.setColor(Color.MAGENTA);
            g2d.fillRect(x, y, size, size);
            return;
        }

        Direction direction = turnable.getDirection();

        String key = "YellowBridge-" + getDirString(direction) + ".png";
        BufferedImage img = tokenImages.get(key);
        if (img != null) {
            g2d.drawImage(img, x, y, size, size, null);
        } else {
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(x, y, size, size); // fallback
        }
        if (token.isTurnable() && token.getDirection() != null) {
            BufferedImage overlay = tokenImages.get("turnable.png");
            if (overlay != null) {
                int overlaySize = size / 4;
                int overlayX = x + size - overlaySize - 5;
                int overlayY = y + 5;
                g2d.drawImage(overlay, overlayX, overlayY, overlaySize, overlaySize, null);
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
            case UP,DOWN -> "HORIZONTAL_BRIDGE";
            case LEFT,RIGHT -> "VERTICAL_BRIDGE";
            case null -> "default_BRIDGE";
        };
    }

}
