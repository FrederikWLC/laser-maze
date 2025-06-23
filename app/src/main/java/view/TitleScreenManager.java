package view;

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

        GameControlPanel controls = panel.getControlPanel();
        controls.singlePlayer.setVisible(true);
        controls.multiplayer.setVisible(true);
        controls.quitGame.setVisible(true);

        controls.levelScrollPane.setVisible(false);
        controls.backButton.setVisible(false);

        if (panel.hasFireLaserButton()) {
            panel.getFireLaserButton().setVisible(false);
        }


        panel.repaint();
    }


    @Override
    public void draw(Graphics2D g) {
        panel.getDrawableManager().drawAll(g);

    }



}
