package view;

import java.awt.Graphics2D;
import java.awt.Color;


public class LevelSelectScreenManager implements DisplayManager {

    private final GamePanel panel;

    public LevelSelectScreenManager(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void show() {
        panel.getSinglePlayerButton().setVisible(false);
        panel.getMultiplayerButton().setVisible(false);
        panel.getQuitGameButton().setVisible(false);

        panel.getLevelScrollPane().setVisible(true);
        panel.getBackButton().setVisible(true);

        panel.repaint();
    }


    @Override
    public void draw(Graphics2D g) {
        panel.getDrawableManager().drawAll(g);

    }
}
