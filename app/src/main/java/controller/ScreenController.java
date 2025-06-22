package controller;

import view.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.IntConsumer;

public class ScreenController {
    private final GamePanel gamePanel;
    private final SoundManager soundManager = new SoundManager();

    private DisplayManager titleScreen;
    private DisplayManager levelSelectScreen;

    public ScreenController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setupScreens(IntConsumer onLevelSelectClick) {
        GamePanelUIBinder binder = new GamePanelUIBinder(gamePanel);
        binder.bindAll(
                () -> {
                    soundManager.stopBackground();
                    cleanupGameUI();
                    gamePanel.switchToScreen(levelSelectScreen);
                },
                () -> {}, // Multiplayer
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
