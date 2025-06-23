// view/BoardRendererPanel.java
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import model.domain.board.Direction;
import model.domain.board.PositionDirection;
import model.domain.board.PositionTurn;
import model.domain.token.base.Token;
import model.domain.token.base.ITurnableToken;
import java.util.HashMap;
import view.dto.ViewOnlyToken;


public class BoardRendererPanel extends JPanel {

    private List<RenderableTile> tilesToRender;
    private List<PositionTurn> laserPath;

    private Map<String, BufferedImage> tokenImages;
    private Map<String, TokenRenderer> staticRenderers;
    private Map<String, ITurnableTokenRenderer> turnableRenderers;

    private List<RenderableTile> inventoryTilesToRender;
    private final int inventoryStartX = 100;
    private final int inventoryStartY = 520; // below board
    private final int inventoryTileSize = 60;

    private Point dragMousePosition = null;
    private Token currentlyDraggedToken = null;


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

    public void setInventoryTilesToRender(List<RenderableTile> tiles) {
        this.inventoryTilesToRender = tiles;
        repaint();
    }

    public void setLaserPath(List<PositionTurn> path) {
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

    //token dragging methods
    public void setDragMousePosition(Point p) {
        this.dragMousePosition = p;
        repaint();
    }

    public void setCurrentlyDraggedToken(Token token) {
        this.currentlyDraggedToken = token;
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
                    boolean isTurnable = match.isTurnable();
                    boolean isMovable = match.isMovable();
                    boolean isRequiredTarget = match.isRequiredTarget();
                    drawToken(g2d, match.getTokenType(), match.getDirection(), isTurnable, isMovable, isRequiredTarget, x, y, tileSize);
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

        // Draw inventory tokens
        for (int i = 0; i < 5; i++) {
            int x = inventoryStartX + i * (inventoryTileSize + 10);
            int y = inventoryStartY;

            RenderableTile tile = findMatchingInventoryTile(i);
            if (tile != null) {
                boolean isTurnable = tile.isTurnable();
                boolean isMovable = tile.isMovable();
                boolean isRequiredTarget = tile.isRequiredTarget();
                drawToken(g2d, tile.getTokenType(), tile.getDirection(), isTurnable, isMovable, isRequiredTarget, x, y, inventoryTileSize);
            } else {
                BufferedImage emptyTile = tokenImages.get("EmptyCell.png");
                if (emptyTile != null) {
                    g2d.drawImage(emptyTile, x, y, inventoryTileSize, inventoryTileSize, null);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillRect(x, y, inventoryTileSize, inventoryTileSize);
                }
            }

            g2d.setColor(Color.DARK_GRAY);
            g2d.drawRect(x, y, inventoryTileSize, inventoryTileSize);
        }

        //Draw dragged token
        if (dragMousePosition != null && currentlyDraggedToken != null) {
            String type = currentlyDraggedToken.getClass().getSimpleName();
            Direction dir = currentlyDraggedToken instanceof ITurnableToken turnable ? turnable.getDirection() : null;
            boolean isTurnable = currentlyDraggedToken instanceof ITurnableToken turnable && turnable.isTurnable();
            boolean isMovable = currentlyDraggedToken instanceof ITurnableToken turnable && turnable.isMovable();
            boolean isRequiredTarget = currentlyDraggedToken instanceof model.domain.token.base.ITargetToken t && t.isRequiredTarget();




            int dragTileSize = 40; // smaller image
            int x = dragMousePosition.x - dragTileSize / 2;
            int y = dragMousePosition.y - dragTileSize / 2;

            drawToken(g2d, type, dir, isTurnable, isMovable, isRequiredTarget, x, y, dragTileSize);
        }

        if (laserPath != null && !laserPath.isEmpty()) {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            for (PositionDirection pd : laserPath) {
                int x = 100 + pd.getPosition().getX() * tileSize;
                int y = 100 + pd.getPosition().getY() * tileSize;

                Direction dir = pd.getDirection();

                if (dir == Direction.UP || dir == Direction.DOWN) {
                    // Full vertical line down the middle
                    g2d.drawLine(x + tileSize / 2, y, x + tileSize / 2, y + tileSize);
                } else if (dir == Direction.LEFT || dir == Direction.RIGHT) {
                    // Full horizontal line across the middle
                    g2d.drawLine(x, y + tileSize / 2, x + tileSize, y + tileSize / 2);
                }
            }

            g2d.setStroke(new BasicStroke(1));
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
    private RenderableTile findMatchingInventoryTile(int x) {
        if (inventoryTilesToRender == null) return null;
        for (RenderableTile tile : inventoryTilesToRender) {
            if (tile.getX() == x && tile.getY() == 0) {
                return tile;
            }
        }
        return null;
    }

    private void drawToken(Graphics2D g2d, String tokenType, Direction direction, boolean isTurnable, boolean isMovable, boolean isRequiredTarget, int x, int y, int tileSize) {
        TokenRenderer staticRenderer = staticRenderers.get(tokenType);
        ITurnableTokenRenderer turnableRenderer = turnableRenderers.get(tokenType);


        if (turnableRenderer != null) {
            ITurnableToken dummy = new ViewOnlyToken(direction, isTurnable, isMovable, isRequiredTarget);
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
