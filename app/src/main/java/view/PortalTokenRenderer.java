package view;

import model.domain.board.Direction;
import model.domain.token.base.ITurnableToken;
import model.domain.token.impl.PortalToken;

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
        if (token instanceof PortalToken portal) {
            String imageKey = "PortalToken-" + getDirString(portal.getDirection()) + ".png";
            BufferedImage img = tokenImages.get(imageKey);
            if (img != null) {
                g2d.drawImage(img, x, y, size, size, null);
            } else {
                g2d.setColor(Color.MAGENTA);
                g2d.fillRect(x, y, size, size);
            }
        }
    }

    @Override
    public String getDirString(Direction dir) {
        if (dir == null) return "default";
        return dir.toString();
    }
}
