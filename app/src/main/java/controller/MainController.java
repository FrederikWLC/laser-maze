package controller;

import view.GamePanel;
import view.GameControlPanel;
import view.util.TokenImageLoader;

import javax.swing.*;

public class MainController {
    private final GamePanel gamePanel;
    private final JFrame window;
    private final ScreenController screenController;
    private final LevelController levelController;

    public MainController() {
        this.window = new JFrame("Laser Maze");

        GameControlPanel controlPanel = new GameControlPanel();
        this.gamePanel = new GamePanel(new TokenImageLoader(), controlPanel);

        controller.RendererRegistrar.registerRenderers(gamePanel);
        UIInitializer.setupWindow(window, gamePanel);

        this.screenController = new ScreenController(gamePanel);
        this.levelController = new LevelController(gamePanel, screenController);
    }

    public void startGame() {
        SwingUtilities.invokeLater(() -> {
            screenController.setupScreens(this::loadLevel);
            screenController.showTitleScreen();
        });
    }

    public void loadLevel(int levelNumber) {
        levelController.loadLevel(levelNumber);
    }
}
