package view;

import model.Direction;
import model.Token;

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

        panel.setMainMenuVisible(false);
        panel.setLevelSelectVisible(false);
        panel.setBackButtonVisible(false);

        panel.setTilesToRender(tiles);  // Tell GamePanel what to draw
    }

    @Override
    public void draw(Graphics2D g) {
        for (Drawable d : panel.getDrawables()) {
            d.draw(g);
        }

    }
}
