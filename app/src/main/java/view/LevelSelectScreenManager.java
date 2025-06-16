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
        panel.clearDrawables();  // Clear background/title from previous screen


        panel.addDrawable(new BackgroundRenderer(Color.BLUE));


        panel.setMainMenuVisible(false);
        panel.setLevelSelectVisible(true);
        panel.setBackButtonVisible(true);
    }

    @Override
    public void draw(Graphics2D g) {
        for (Drawable d : panel.getDrawables()) {
            d.draw(g);
        }
    }
}
