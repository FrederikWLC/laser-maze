package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TitleScreenManager implements DisplayManager {

    private final GamePanel panel;
    private final BufferedImage background;

    public TitleScreenManager(GamePanel panel, BufferedImage background) {
        this.panel = panel;
        this.background = background;
    }

    @Override
    public void show() {
        panel.clearDrawables();
        panel.addDrawable(new BackgroundRenderer(background));
        panel.addDrawable(new TitleRenderer("LASER MAZE", 160));

        panel.setMainMenuVisible(true);
        panel.setLevelSelectVisible(false);
        panel.setBackButtonVisible(false);
    }

    @Override
    public void draw(Graphics2D g) {
        for (Drawable d : panel.getDrawables()) {
            d.draw(g);
        }
    }



}
