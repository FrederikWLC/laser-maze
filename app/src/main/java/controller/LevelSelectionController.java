package controller;

import model.domain.board.Inventory;
import model.domain.board.builder.InventoryBuilder;

import model.domain.level.Level;
import model.persistence.storage.DefaultLevelLoader;
import model.persistence.storage.LevelIOHandler;
import model.persistence.storage.LevelSaver;
import model.persistence.storage.SavedLevelLoader;

import model.domain.multiplayer.Multiplayer;
import view.GamePanel;
import view.RenderableTile;
import view.GamePanelUIBinder;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;


public class LevelSelectionController {
    private final GamePanel gamePanel;
    private LevelController levelController;
    private MultiplayerController multiplayerController;
    private ScreenController screenController;
    private final SoundManager soundManager = new SoundManager();
    private final LevelIOHandler levelIOHandler;

    public LevelSelectionController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        DefaultLevelLoader defaultLevelLoader = new DefaultLevelLoader();
        SavedLevelLoader savedLevelLoader = new SavedLevelLoader();
        LevelSaver levelSaver = new LevelSaver();
        levelIOHandler = new LevelIOHandler(defaultLevelLoader,savedLevelLoader,levelSaver);
    }

    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    public void loadLevelById(int id) {
        System.out.println("Loading level " + id);
        Level level = levelIOHandler.load(id);
        if (level == null) {
            System.err.println("Failed to load level " + id);
            return;
        }
        setCurrentLevel(level);
        System.out.println("Loaded level: " + levelController.getLevelEngine().getLevel());
        reloadLevelUI();
    }

    public void loadMultiplayerLevelById(int id, int playerCount) {
        Level defaultLevel = levelIOHandler.getDefaultLevelLoader().load(id);

        Multiplayer multiplayer = new Multiplayer(defaultLevel, playerCount);
        this.multiplayerController = new MultiplayerController(multiplayer,this,gamePanel);

        multiplayerController.startGame();
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

        if (!gamePanel.hasFireLaserButton()) {
            gamePanel.createFireLaserButton();
        }

        GamePanelUIBinder binder = new GamePanelUIBinder(gamePanel);

        binder.bindAll(
                null, null, null, null,
                e -> {
                    soundManager.play(SoundManager.Sound.LASER, false);
                    System.out.println("Fire Laser button clicked!");
                    levelController.triggerLaser(true);

                    System.out.println("Laser path size: " + levelController.getCurrentLaserPath().size());
                    gamePanel.setLaserPath(levelController.getCurrentLaserPath());

                    gamePanel.getControlPanel().boardRenderer.setLaserPath(levelController.getCurrentLaserPath());
                    gamePanel.getControlPanel().boardRenderer.repaint();

                    List<RenderableTile> updated = tileFactory.convertBoardToRenderableTiles(getCurrentLevel().getBoard());
                    gamePanel.setTilesToRender(updated);
                    gamePanel.getControlPanel().boardRenderer.setTilesToRender(updated);
                    gamePanel.repaint();
                    gamePanel.getControlPanel().boardRenderer.repaint();

                    boolean levelCompleted = levelController.getLevelEngine().updateAndCheckLevelCompletionState();
                    System.out.println("Level completed: " + levelCompleted);
                    if (levelCompleted) {
                        if (multiplayerController != null) {
                            gamePanel.showLevelComplete();
                            multiplayerController.onLevelComplete();
                        } else {
                            gamePanel.showLevelComplete();
                        }
                    }

                },
                null
        );

        TokenDragController dragController = new TokenDragController();
        InputHandler inputHandler = new InputHandler(
                levelController,
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

    public LevelController getLevelController() {
        return levelController;
    }

    public LevelIOHandler getLevelIOHandler() {
        return levelIOHandler;
    }

    public Level getCurrentLevel() {
        return levelController.getLevelEngine().getLevel();
    }

    public void setCurrentLevel(Level level) {
        levelController = new LevelController(level);
    }

}
