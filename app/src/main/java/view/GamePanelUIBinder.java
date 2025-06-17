package view;

import java.awt.event.ActionListener;
import java.util.function.IntConsumer;

public class GamePanelUIBinder {

    private final GamePanel gamePanel;

    public GamePanelUIBinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void bindEvents(
            Runnable onSinglePlayerClick,
            Runnable onMultiplayerClick,
            Runnable onQuitClick,
            ActionListener onFireLaserClick,
            IntConsumer onLevelSelectClick
    ) {
        gamePanel.setOnSinglePlayerClick(onSinglePlayerClick);
        gamePanel.setOnMultiplayerClick(onMultiplayerClick);
        gamePanel.setOnQuitClick(onQuitClick);
        gamePanel.setOnFireLaserClick(onFireLaserClick);
        gamePanel.setOnLevelSelectClick(onLevelSelectClick);
    }
}
