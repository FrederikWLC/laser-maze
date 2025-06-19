package view;

import java.awt.event.ActionListener;
import java.util.function.IntConsumer;

import view.GamePanel;

public class GamePanelUIBinder {
    private final GamePanel gamePanel;

    public GamePanelUIBinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void bindAll(
            Runnable onSinglePlayerClick,
            Runnable onMultiplayerClick,
            Runnable onQuitClick,
            Runnable onBackClick,
            ActionListener onFireLaserClick,
            IntConsumer onLevelSelectClick
    ) {
        GameControlPanel controls = gamePanel.getControlPanel();

        if (onSinglePlayerClick != null) {
            controls.singlePlayer.addActionListener(e -> onSinglePlayerClick.run());
        }
        if (onMultiplayerClick != null) {
            controls.multiplayer.addActionListener(e -> onMultiplayerClick.run());
        }
        if (onQuitClick != null) {
            controls.quitGame.addActionListener(e -> onQuitClick.run());
        }
        if (onBackClick != null) {
            controls.backButton.addActionListener(e -> onBackClick.run());
        }

        if (onFireLaserClick != null) {
            gamePanel.setFireLaserListener(onFireLaserClick);
            if (gamePanel.hasFireLaserButton()) {
                gamePanel.setFireLaserListener(onFireLaserClick);
            }
        }

        if (onLevelSelectClick != null) {
            var view = controls.levelScrollPane.getViewport().getView();
            if (view instanceof java.awt.Container container) {
                for (var comp : container.getComponents()) {
                    if (comp instanceof javax.swing.JButton button) {
                        String text = button.getText();
                        int levelNumber = Integer.parseInt(text.replace("Level ", ""));
                        button.addActionListener(e -> onLevelSelectClick.accept(levelNumber));
                    }
                }
            }
        }
    }


}
