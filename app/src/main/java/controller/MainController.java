package controller;

import view.GamePanel;
import view.util.TokenImageLoader;

import javax.swing.*;

public class MainController {
    private final GamePanel gamePanel;
    private final JFrame window;
    private final ScreenController screenController;
    private final LevelController levelController;

    public MainController() {
        this.window = new JFrame("Laser Maze");
        this.gamePanel = new GamePanel(new TokenImageLoader());

        this.screenController = new ScreenController(gamePanel, this);
        this.levelController = new LevelController(gamePanel, screenController);
    }

    public void startGame() {
        SwingUtilities.invokeLater(() -> {
            setupWindow();
            screenController.setupScreens(this::loadLevel);
            screenController.showTitleScreen();
        });
    }

    private void setupWindow() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void loadLevel(int levelNumber) {
        levelController.loadLevel(levelNumber);
    }
}
