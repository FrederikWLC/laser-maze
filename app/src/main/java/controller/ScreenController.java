package controller;

import model.domain.level.Level;
import view.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntConsumer;

public class ScreenController {
    private final GamePanel gamePanel;
    private final SoundManager soundManager = new SoundManager();
    private final LevelController levelController;

    private DisplayManager titleScreen;
    private DisplayManager singlePlayerLevelSelectScreen;
    private DisplayManager multiplayerLevelSelectScreen;

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
                    setupSingleplayerLevelButtons(); // <-- fix added here
                    gamePanel.switchToScreen(singlePlayerLevelSelectScreen);
                },
                () -> {
                    soundManager.stopBackground();
                    cleanupGameUI();
                    setupMultiplayerLevelButtons();
                    gamePanel.switchToScreen(multiplayerLevelSelectScreen);
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

        singlePlayerLevelSelectScreen = new LevelSelectScreenManager(gamePanel);
        multiplayerLevelSelectScreen = new LevelSelectScreenManager(gamePanel); // no need for separate creation method
    }


    private DisplayManager createMultiplayerLevelSelectScreen() {
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

                levelController.loadMultiplayerLevel(levelNum, 2); // 2-player multiplayer
            });
            levelListPanel.add(button);
        }

        levelListPanel.revalidate();
        levelListPanel.repaint();

        return new LevelSelectScreenManager(gamePanel);
    }

    private void setupSingleplayerLevelButtons() {
        JPanel levelListPanel = gamePanel.getControlPanel().levelListPanel;
        levelListPanel.removeAll();

        List<Level> levels = levelController.getAllLevels()
                .stream()
                .sorted(Comparator.comparingInt(Level::getId)) // sort by ID
                .toList();
        for (Level level : levels) {
            int levelId = level.getId();
            JButton button = new JButton("Level " + levelId);
            button.addActionListener(e -> {
                gamePanel.resetBoardUI();
                gamePanel.clearMouseListeners();
                gamePanel.clearLaserPath();
                gamePanel.repaint();

                levelController.loadLevel(levelId); // singleplayer
            });
            levelListPanel.add(button);
        }

        levelListPanel.revalidate();
        levelListPanel.repaint();
    }


    private void setupMultiplayerLevelButtons() {
        JPanel levelListPanel = gamePanel.getControlPanel().levelListPanel;
        levelListPanel.removeAll();

        List<Level> levels = levelController.getAllLevels()
                .stream()
                .sorted(Comparator.comparingInt(Level::getId)) // sort by ID
                .toList();
        for (Level level : levels) {
            int levelId = level.getId();
            JButton button = new JButton("Level " + levelId);
            button.addActionListener(e -> {
                gamePanel.resetBoardUI();
                gamePanel.clearMouseListeners();
                gamePanel.clearLaserPath();
                gamePanel.repaint();

                levelController.loadMultiplayerLevel(levelId, 2); // Start multiplayer
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
