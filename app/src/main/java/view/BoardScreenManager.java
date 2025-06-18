package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.awt.Color;


public class BoardScreenManager implements DisplayManager {

    private final GamePanel panel;
    private final List<RenderableTile> tiles;
    private final Map<String, BufferedImage> tokenImages;

    public BoardScreenManager(GamePanel panel, List<RenderableTile> tiles, Map<String, BufferedImage> tokenImages) {
        this.panel = panel;
        this.tiles = tiles;
        this.tokenImages = tokenImages;
    }

    @Override
    public void show() {
        panel.clearDrawables();
        panel.addDrawable(new BackgroundRenderer(Color.BLACK));
        panel.addDrawable(new EmptyTileRenderer(panel.getTokenImages().get("EmptyCell.png")));
        panel.setTilesToRender(tiles);
        panel.setTokenImages(tokenImages);

        if (!panel.hasFireLaserButton()) {
            panel.createFireLaserButton();
        }

        panel.getFireLaserButton().setVisible(true);
        panel.getFireLaserButton().setBackground(Color.ORANGE);

        panel.getSinglePlayerButton().setVisible(false);
        panel.getMultiplayerButton().setVisible(false);
        panel.getQuitGameButton().setVisible(false);
        panel.getLevelScrollPane().setVisible(false);
        panel.getBackButton().setVisible(false);

        panel.getBoardRenderer().setVisible(true);
        panel.repaint();
    }


    @Override
    public void draw(Graphics2D g) {
        panel.getDrawableManager().drawAll(g);
    }
}
