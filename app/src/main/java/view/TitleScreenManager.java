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

        panel.getSinglePlayerButton().setVisible(true);
        panel.getMultiplayerButton().setVisible(true);
        panel.getQuitGameButton().setVisible(true);

        panel.getLevelScrollPane().setVisible(false);
        panel.getBackButton().setVisible(false);

        panel.repaint();
    }


    @Override
    public void draw(Graphics2D g) {
        panel.getDrawableManager().drawAll(g);

    }



}
