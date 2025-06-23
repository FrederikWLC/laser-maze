package controller;

import view.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.IntConsumer;

public class ScreenController {
    private final GamePanel gamePanel;
    private final SoundManager soundManager = new SoundManager();
    private final LevelController levelController;

    private DisplayManager titleScreen;
    private DisplayManager levelSelectScreen;

    public ScreenController(GamePanel gamePanel, LevelController levelController) {
        this.gamePanel = gamePanel;
        this.levelController = levelController;
    }

    public void setupScreens(IntConsumer onLevelSelectClick) {
        GamePanelUIBinder binder = new GamePanelUIBinder(gamePanel);
        binder.bindAll(
                () -> {
                    soundManager.stopBackground();
                    cleanupGameUI();
                    gamePanel.switchToScreen(levelSelectScreen);
                },
                () -> { // Multiplayer button
                    soundManager.stopBackground();
                    cleanupGameUI();
                    gamePanel.switchToScreen(levelSelectScreen); // reuse level select screen
                    setupMultiplayerLevelButtons(); // use a new method
                },
                () -> System.exit(0),
                () -> {
                    soundManager.stopBackground();
                    cleanupGameUI();
                    gamePanel.switchToScreen(titleScreen);
                },
                null,
                onLevelSelectClick
        );

        try {
            BufferedImage bg = ImageIO.read(getClass().getResource("/textures/background/thelasermaze.jpeg"));
            titleScreen = new TitleScreenManager(gamePanel, bg);
        } catch (Exception e) {
            System.err.println("Failed to load title screen background.");
        }

        levelSelectScreen = new LevelSelectScreenManager(gamePanel);
    }

    private void setupMultiplayerLevelButtons() {
        JPanel levelListPanel = gamePanel.getControlPanel().levelListPanel;
        levelListPanel.removeAll();

        for (int i = 1; i <= 10; i++) {
            int levelNum = i;
            JButton button = new JButton("Level " + levelNum);
            button.addActionListener(e -> {
                gamePanel.resetBoardUI();
                gamePanel.clearMouseListeners();
                gamePanel.clearLaserPath();
                gamePanel.repaint();

                levelController.loadMultiplayerLevel(levelNum, 2); // Two players
            });
            levelListPanel.add(button);
        }

        levelListPanel.revalidate();
        levelListPanel.repaint();
    }


    public void showTitleScreen() {
        soundManager.stopBackground(); // Ensure music stops
        cleanupGameUI();
        gamePanel.switchToScreen(titleScreen);
    }

    public void showBoardScreen(List<RenderableTile> tiles) {
        DisplayManager boardScreen = new BoardScreenManager(
                gamePanel,
                tiles,
                gamePanel.getTokenImages()
        );
        gamePanel.switchToScreen(boardScreen);
        gamePanel.showBoardUI();
    }

    public void bindFireLaserListener(java.awt.event.ActionListener fireLaserListener) {
        gamePanel.setFireLaserListener(fireLaserListener);
    }

    private void cleanupGameUI() {
        gamePanel.clearLaserPath();
        gamePanel.clearGameplayButtons();
        gamePanel.clearMouseListeners();
        gamePanel.resetBoardUI();
    }



}
