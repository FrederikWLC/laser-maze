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
    private final BoardRendererPanel boardRenderer;


    public BoardScreenManager(GamePanel panel, List<RenderableTile> tiles, Map<String, BufferedImage> tokenImages) {
        this.panel = panel;
        this.tiles = tiles;
        this.tokenImages = tokenImages;
        this.boardRenderer = panel.getControlPanel().boardRenderer;

    }

    @Override
    public void show() {
        panel.clearDrawables();
        panel.addDrawable(new BackgroundRenderer(Color.BLACK));

        boardRenderer.setTilesToRender(tiles);
        boardRenderer.setTokenImages(tokenImages);


        if (!panel.hasFireLaserButton()) {
            panel.createFireLaserButton();
        }


        panel.getFireLaserButton().setVisible(true);

        // Hide unrelated UI
        GameControlPanel controls = panel.getControlPanel();
        controls.singlePlayer.setVisible(false);
        controls.multiplayer.setVisible(false);
        controls.quitGame.setVisible(false);
        controls.levelScrollPane.setVisible(false);
        controls.backButton.setVisible(false);

        boardRenderer.setVisible(true);
        panel.repaint();
    }




    @Override
    public void draw(Graphics2D g) {
        panel.getDrawableManager().drawAll(g);
    }
}
