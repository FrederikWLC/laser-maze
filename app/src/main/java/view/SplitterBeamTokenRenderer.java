package view;

import model.domain.board.Direction;
import model.domain.token.ITurnableToken;

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
    }

    @Override
    public String getDirString(Direction dir) {
        return switch (dir) {
            case UP,DOWN -> "BACKSLASH";
            case LEFT,RIGHT -> "SLASH";
        };
    }
}
