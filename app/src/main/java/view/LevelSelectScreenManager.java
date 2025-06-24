package view;

import java.awt.Graphics2D;

public class LevelSelectScreenManager implements DisplayManager {

    private final GamePanel panel;

    public LevelSelectScreenManager(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void show() {
        GameControlPanel controls = panel.getControlPanel();

        controls.singlePlayer.setVisible(false);
        controls.multiplayer.setVisible(false);
        controls.quitGame.setVisible(false);

        controls.levelScrollPane.setVisible(true);
        controls.backButton.setVisible(true);

        panel.repaint();
    }


    @Override
    public void draw(Graphics2D g) {
        panel.getDrawableManager().drawAll(g);

    }
}
