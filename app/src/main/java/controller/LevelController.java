package controller;

import model.domain.board.Inventory;
import model.domain.board.PositionDirection;
import model.domain.board.PositionTurn;
import model.domain.board.TileContainer;
import model.domain.board.builder.InventoryBuilder;

import model.domain.level.Level;
import model.persistence.storage.DefaultLevelLoader;
import model.persistence.storage.LevelIOHandler;
import model.persistence.storage.LevelSaver;
import model.persistence.storage.SavedLevelLoader;

import view.GamePanel;
import view.RenderableTile;
import view.GamePanelUIBinder;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.engine.LevelEngine;


public class LevelController {
    private final GamePanel gamePanel;
    private final ScreenController screenController;
    private final SoundManager soundManager = new SoundManager();
    private final DefaultLevelLoader defaultLevelLoader = new DefaultLevelLoader();
    private final SavedLevelLoader savedLevelLoader = new SavedLevelLoader();
    private final LevelSaver levelSaver = new LevelSaver();
    private final LevelIOHandler levelIOHandler = new LevelIOHandler(defaultLevelLoader,savedLevelLoader,levelSaver);
    private Level currentLevel;
    private LevelEngine levelEngine = new LevelEngine();


    public LevelController(GamePanel gamePanel, ScreenController screenController) {
        this.gamePanel = gamePanel;
        this.screenController = screenController;
    }

    public void loadLevel(int levelNumber) {
        System.out.println("Loading level " + levelNumber);
        Level level = levelIOHandler.load(levelNumber);
        if (level == null) {
            System.err.println("Failed to load level " + levelNumber);
            return;
        }
        setCurrentLevel(level);
        System.out.println("Loaded level: " + getCurrentLevel());
        reloadLevelUI();
    }

    public void reloadLevelUI() {

        gamePanel.resetBoardUI();
        gamePanel.clearMouseListeners();
        gamePanel.createControlButtons();
        gamePanel.showBoardUI();

        soundManager.stopBackground(); // ensure old track doesn't stack
        soundManager.play(SoundManager.Sound.BACKGROUND, true);

        Inventory inventory = InventoryBuilder.buildInventory(getCurrentLevel().getRequiredTokens());
        getCurrentLevel().setInventory(inventory);

        RenderableTileFactory tileFactory = new RenderableTileFactory();

        gamePanel.setInventory(inventory);
        List<RenderableTile> inventoryTiles = tileFactory.convertBoardToRenderableTiles(inventory);
        gamePanel.setInventoryTilesToRender(inventoryTiles);


        List<RenderableTile> tiles = tileFactory.convertBoardToRenderableTiles(getCurrentLevel().getBoard());
        gamePanel.setTilesToRender(tiles);

        GameController gameController = new GameController(getCurrentLevel());

        if (!gamePanel.hasFireLaserButton()) {
            gamePanel.createFireLaserButton();
        }

        GamePanelUIBinder binder = new GamePanelUIBinder(gamePanel);

        binder.bindAll(
                null, null, null, null,
                e -> {
                    soundManager.play(SoundManager.Sound.LASER, false);
                    System.out.println("Fire Laser button clicked!");
                    gameController.triggerLaser(true);
                    List<PositionTurn> path = gameController.getCurrentLaserPath();

                    System.out.println("Laser path size: " + path.size());
                    gamePanel.setLaserPath(path);

                    gamePanel.getControlPanel().boardRenderer.setLaserPath(path);
                    gamePanel.getControlPanel().boardRenderer.repaint();

                    List<RenderableTile> updated = tileFactory.convertBoardToRenderableTiles(getCurrentLevel().getBoard());
                    gamePanel.setTilesToRender(updated);
                    gamePanel.getControlPanel().boardRenderer.setTilesToRender(updated);
                    gamePanel.repaint();
                    gamePanel.getControlPanel().boardRenderer.repaint();

                    if (levelEngine.updateAndCheckLevelCompletionState(getCurrentLevel())) {
                        gamePanel.showLevelComplete();
                    }

                },
                null
        );

        TokenDragController dragController = new TokenDragController();
        InputHandler inputHandler = new InputHandler(
                gameController,
                gamePanel,
                tileFactory,
                soundManager,
                inventory,
                dragController
        );
        gamePanel.addMouseListener(inputHandler);
        gamePanel.addMouseMotionListener(inputHandler);

        screenController.showBoardScreen(tiles);
        gamePanel.repaint();

        //control buttons
        JButton restartButton = gamePanel.getRestartButton();
        JButton exitButton = gamePanel.getExitButton();
        JButton saveExitButton = gamePanel.getSaveAndExitButton();

        restartButton.setVisible(true);
        exitButton.setVisible(true);
        saveExitButton.setVisible(true);

        saveExitButton.addActionListener(e -> {
            saveLevel();
            exitLevel();
        });

        restartButton.addActionListener(e -> {
            restartLevel();
        });

        ActionListener exitAction = e -> {
            exitLevel();
        };

        exitButton.addActionListener(exitAction);
    }

    public void saveLevel() {
        System.out.println("Saving...");
        levelIOHandler.save(getCurrentLevel());
    }

    public void exitLevel() {
        System.out.println("Exiting...");
        setCurrentLevel(null);
        System.out.println("Loaded level: " + getCurrentLevel());
        soundManager.stopBackground();
        gamePanel.resetBoardUI();
        screenController.showTitleScreen();
    }

    public void restartLevel() {
        Level level = levelIOHandler.restart(getCurrentLevel());
        setCurrentLevel(level);
        reloadLevelUI();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level level) {
        this.currentLevel = level;
    }
}
