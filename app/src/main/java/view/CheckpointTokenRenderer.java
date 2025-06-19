package view;

import model.domain.token.base.ITurnableToken;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import model.domain.board.Direction;


public class CheckpointTokenRenderer extends TurnableTokenRenderer {
    private final Map<String, BufferedImage> tokenImages;

    public CheckpointTokenRenderer(Map<String, BufferedImage> tokenImages) {
        this.tokenImages = tokenImages;
    }

    @Override
    public void render(Graphics2D g2d, ITurnableToken token, int x, int y, int tileSize) {
        if (!(token instanceof ITurnableToken turnable)) {
            // Fallback if direction is missing or invalid
            g2d.setColor(Color.MAGENTA);
            g2d.fillRect(x, y, tileSize, tileSize);
            return;
        }

        Direction direction = turnable.getDirection();

        String key = "YellowBridge-" + getDirString(direction) + ".png";
        BufferedImage img = tokenImages.get(key);
        if (img != null) {
            g2d.drawImage(img, x, y, tileSize, tileSize, null);
        } else {
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(x, y, tileSize, tileSize); // fallback
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
