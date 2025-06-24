package controller;

import model.domain.board.Inventory;
import model.domain.board.PositionDirection;
import model.domain.board.builder.InventoryBuilder;

import model.domain.level.Level;
import model.domain.multiplayer.Multiplayer;
import model.persistence.serializer.LevelSerializer;
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
    private ScreenController screenController;
    private final SoundManager soundManager = new SoundManager();
    private final DefaultLevelLoader defaultLevelLoader = new DefaultLevelLoader();
    private final SavedLevelLoader savedLevelLoader = new SavedLevelLoader();
    private final LevelSaver levelSaver = new LevelSaver();
    private final LevelIOHandler levelIOHandler = new LevelIOHandler(defaultLevelLoader, savedLevelLoader, levelSaver);
    private Level currentLevel;
    protected Level originalLevelData;
    private final LevelEngine levelEngine = new LevelEngine();
    private MultiplayerController multiplayerController;

    public LevelController(GamePanel gamePanel, ScreenController screenController) {
        this.gamePanel = gamePanel;
        this.screenController = screenController;
    }

    public void loadLevel(int levelNumber) {
        Level level = levelIOHandler.load(levelNumber);
        if (level == null) {
            System.err.println("Failed to load level " + levelNumber);
            return;
        }
        setCurrentLevel(level);
        reloadLevelUI();
    }

    public void loadMultiplayerLevel(int levelNumber, int playerCount) {
        Level defaultLevel = levelIOHandler.load(levelNumber);
        this.originalLevelData = cloneLevel(defaultLevel); // pristine copy

        Multiplayer multiplayer = new Multiplayer(defaultLevel, playerCount);
        this.multiplayerController = new MultiplayerController(multiplayer, this, gamePanel);

        multiplayerController.startGame();
    }

    public Level cloneLevel(Level level) {
        Level copy = new LevelSerializer().clone(level);
        copy.setComplete(false); // Ensure fresh clone
        return copy;
    }

    public void reloadLevelUI() {
        gamePanel.resetBoardUI();
        gamePanel.clearMouseListeners();
        gamePanel.createControlButtons();
        gamePanel.showBoardUI();

        soundManager.stopBackground();
        soundManager.play(SoundManager.Sound.BACKGROUND, true);

        Inventory inventory = InventoryBuilder.buildInventory(getCurrentLevel().getRequiredTokens());
        getCurrentLevel().setInventory(inventory);

        RenderableTileFactory tileFactory = new RenderableTileFactory();

        gamePanel.setInventory(inventory);
        gamePanel.setInventoryTilesToRender(tileFactory.convertBoardToRenderableTiles(inventory));
        gamePanel.setTilesToRender(tileFactory.convertBoardToRenderableTiles(getCurrentLevel().getBoard()));

        GameController gameController = new GameController(getCurrentLevel());

        if (!gamePanel.hasFireLaserButton()) {
            gamePanel.createFireLaserButton();
        }

        new GamePanelUIBinder(gamePanel).bindAll(
                null, null, null, null,
                e -> {
                    soundManager.play(SoundManager.Sound.LASER, false);
                    gameController.triggerLaser(true);

                    List<PositionDirection> path = gameController.getCurrentLaserPath();
                    gamePanel.setLaserPath(path);
                    gamePanel.getControlPanel().boardRenderer.setLaserPath(path);
                    gamePanel.getControlPanel().boardRenderer.repaint();

                    List<RenderableTile> updated = tileFactory.convertBoardToRenderableTiles(getCurrentLevel().getBoard());
                    gamePanel.setTilesToRender(updated);
                    gamePanel.getControlPanel().boardRenderer.setTilesToRender(updated);
                    gamePanel.repaint();

                    if (levelEngine.updateAndCheckLevelCompletionState(getCurrentLevel())) {
                        getCurrentLevel().setComplete(true);
                        if (multiplayerController != null) {
                            multiplayerController.onLevelComplete();
                        } else {
                            gamePanel.showLevelComplete();
                        }
                    }
                },
                null
        );

        InputHandler inputHandler = new InputHandler(
                gameController,
                gamePanel,
                tileFactory,
                soundManager,
                inventory,
                new TokenDragController()
        );
        gamePanel.addMouseListener(inputHandler);
        gamePanel.addMouseMotionListener(inputHandler);

        List<RenderableTile> tiles = tileFactory.convertBoardToRenderableTiles(getCurrentLevel().getBoard());
        gamePanel.setTilesToRender(tiles);
        screenController.showBoardScreen(tiles);

        gamePanel.repaint();

        // Buttons
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
        restartButton.addActionListener(e -> restartLevel());
        exitButton.addActionListener(e -> exitLevel());
    }

    public void saveLevel() {
        levelIOHandler.save(getCurrentLevel());
    }

    public void exitLevel() {
        setCurrentLevel(null);
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

    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    public List<Level> getAllLevels() {
        return levelIOHandler.loadAll();
    }
}
