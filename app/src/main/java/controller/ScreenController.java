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
    private final LevelSelectionController levelSelectionController;

    private DisplayManager titleScreen;
    private DisplayManager singlePlayerLevelSelectScreen;
    private DisplayManager multiplayerLevelSelectScreen;

    public ScreenController(GamePanel gamePanel, LevelSelectionController levelSelectionController) {
        this.gamePanel = gamePanel;
        this.levelSelectionController = levelSelectionController;
    }

    public void setupScreens(IntConsumer onLevelSelectClick) {
        GamePanelUIBinder binder = new GamePanelUIBinder(gamePanel);
        binder.bindAll(
                () -> {
                    soundManager.stopBackground();
                    cleanupGameUI();
                    setupSingleplayerLevelButtons();
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
        multiplayerLevelSelectScreen = new LevelSelectScreenManager(gamePanel);
    }

    private void setupSingleplayerLevelButtons() {
        JPanel levelListPanel = gamePanel.getControlPanel().levelListPanel;
        levelListPanel.removeAll();

        List<Integer> ids = levelSelectionController
                .getLevelIOHandler()
                .getDefaultLevelLoader()
                .getAllAvailableLevelIds()
                .stream()
                .sorted()
                .toList();
        for (Integer id : ids) {
            JButton button = new JButton("Level " + id);
            button.addActionListener(e -> {
                gamePanel.resetBoardUI();
                gamePanel.clearMouseListeners();
                gamePanel.clearLaserPath();
                gamePanel.repaint();
                levelSelectionController.loadLevelById(id);
            });
            levelListPanel.add(button);
        }

        levelListPanel.revalidate();
        levelListPanel.repaint();
    }


    private void setupMultiplayerLevelButtons() {
        JPanel levelListPanel = gamePanel.getControlPanel().levelListPanel;
        levelListPanel.removeAll();

        List<Integer> ids = levelSelectionController
                .getLevelIOHandler()
                .getDefaultLevelLoader()
                .getAllAvailableLevelIds()
                .stream()
                .sorted()
                .toList();
        for (Integer id : ids) {
            JButton button = new JButton("Level " + id);
            button.addActionListener(e -> {
                gamePanel.resetBoardUI();
                gamePanel.clearMouseListeners();
                gamePanel.clearLaserPath();
                gamePanel.repaint();
                levelSelectionController.loadMultiplayerLevelById(id,2);
            });
            levelListPanel.add(button);
        }

        levelListPanel.revalidate();
        levelListPanel.repaint();
    }



    public void showTitleScreen() {
        soundManager.stopBackground();
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

    private void cleanupGameUI() {
        gamePanel.clearLaserPath();
        gamePanel.clearGameplayButtons();
        gamePanel.clearMouseListeners();
        gamePanel.resetBoardUI();
    }



}
