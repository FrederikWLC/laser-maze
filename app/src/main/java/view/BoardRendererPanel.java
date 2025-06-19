// view/BoardRendererPanel.java
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import model.domain.board.Direction;
import model.domain.board.PositionDirection;
import model.domain.token.base.Token;
import model.domain.token.base.ITurnableToken;
import java.util.HashMap;
import view.dto.ViewOnlyToken;


public class BoardRendererPanel extends JPanel {

    private List<RenderableTile> tilesToRender;
    private List<PositionDirection> laserPath;

    private Map<String, BufferedImage> tokenImages;
    private Map<String, TokenRenderer> staticRenderers;
    private Map<String, ITurnableTokenRenderer> turnableRenderers;

    private int visibleLaserSegments = 0;


    public BoardRendererPanel() {

        setOpaque(false); // We want background to show through if needed
        staticRenderers = new HashMap<>();
        turnableRenderers = new HashMap<>();
        tokenImages = new HashMap<>();
    }

    // Setters
    public void setTilesToRender(List<RenderableTile> tiles) {
        this.tilesToRender = tiles;
        repaint();
    }

    public void setLaserPath(List<PositionDirection> path) {
        this.laserPath = path;
        repaint();
    }

    public void setTokenImages(Map<String, BufferedImage> images) {

        this.tokenImages = images;
    }

    public void setStaticRenderers(Map<String, TokenRenderer> renderers) {

        this.staticRenderers = renderers;

    }

    public void setTurnableRenderers(Map<String, ITurnableTokenRenderer> renderers) {
        this.turnableRenderers = renderers;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (tilesToRender == null || tokenImages == null) return;

        int tileSize = 80;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                int x = 100 + col * tileSize;
                int y = 100 + row * tileSize;

                RenderableTile match = findMatchingTile(col, row);
                if (match != null) {
                    drawToken(g2d, match.getTokenType(), match.getDirection(), x, y, tileSize);
                } else {
                    BufferedImage emptyTile = tokenImages.get("EmptyCell.png");

                    if (emptyTile != null) {
                        g2d.drawImage(emptyTile, x, y, tileSize, tileSize, null);
                    } else {
                        g2d.setColor(Color.LIGHT_GRAY);
                        g2d.fillRect(x, y, tileSize, tileSize);
                    }
                }

                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, tileSize, tileSize);
            }

        }

        // Draw laser path
        if (laserPath != null && !laserPath.isEmpty()) {
            for (PositionDirection pd : laserPath) {
                int x = 100 + pd.getPosition().getX() * tileSize;
                int y = 100 + pd.getPosition().getY() * tileSize;

                Direction dir = pd.getDirection(); // Assuming this exists
                boolean isVertical = (dir == Direction.UP || dir == Direction.DOWN);
                String imageKey = "LASER-BEAM-" + (isVertical ? "VERTICAL" : "HORIZONTAL") + ".png";

                BufferedImage beamImage = tokenImages.get(imageKey);
                if (beamImage != null) {
                    g2d.drawImage(beamImage, x, y, tileSize, tileSize, null);
                } else {
                    // fallback in case image is missing
                    g2d.setColor(Color.RED);
                    g2d.fillRect(x + 30, y + 30, 20, 20);
                }
            }
        }


        g2d.dispose();
    }

    private RenderableTile findMatchingTile(int x, int y) {
        if (tilesToRender == null) return null;
        for (RenderableTile tile : tilesToRender) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }

    private void drawToken(Graphics2D g2d, String tokenType, Direction direction, int x, int y, int tileSize) {
        TokenRenderer staticRenderer = staticRenderers.get(tokenType);
        ITurnableTokenRenderer turnableRenderer = turnableRenderers.get(tokenType);


        if (turnableRenderer != null) {
            ITurnableToken dummy = new ViewOnlyToken(direction);
            turnableRenderer.render(g2d, dummy, x, y, tileSize);
        } else if (staticRenderer != null) {
            Token dummy = new Token() {};
            staticRenderer.render(g2d, dummy, x, y, tileSize);
        } else {
            g2d.setColor(Color.MAGENTA);
            g2d.drawString("?", x + tileSize / 2 - 4, y + tileSize / 2 + 4);
        }


    }
}
