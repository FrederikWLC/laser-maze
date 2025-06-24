package controller;

import view.GamePanel;
import view.GameControlPanel;
import view.util.TokenImageLoader;

import javax.swing.*;

public class MainController {
    private final GamePanel gamePanel;
    private final JFrame window;
    private final ScreenController screenController;
    private final LevelSelectionController levelSelectionController;

    public MainController() {
        this.window = new JFrame("Laser Maze");

        GameControlPanel controlPanel = new GameControlPanel();
        this.gamePanel = new GamePanel(new TokenImageLoader(), controlPanel);

        controller.RendererRegistrar.registerRenderers(gamePanel);
        UIInitializer.setupWindow(window, gamePanel);

        // BIG DIP VIOLATION: BOTH DEPEND ON EACH OTHER, WTF IS THIS..... MAN... WASTE
        // BUT WE HAVE TO GO WITH IT FOR NOW
        // TOO LIITLE TIME TO FIX THIS PROPERLY
        this.levelSelectionController = new LevelSelectionController(gamePanel);
        this.screenController = new ScreenController(gamePanel, levelSelectionController);
        this.levelSelectionController.setScreenController(screenController);
    }

    public void startGame() {
        SwingUtilities.invokeLater(() -> {
            screenController.setupScreens(this::loadLevel);
            screenController.showTitleScreen();
        });
    }

    public void loadLevel(int id) {
        levelSelectionController.loadLevelById(id);
    }
}
