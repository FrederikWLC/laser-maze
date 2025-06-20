package controller;

import model.domain.board.Board;
import model.domain.board.PositionDirection;
import model.domain.board.TileContainer;
import model.domain.board.builder.InventoryBuilder;
import model.domain.level.Level;
import model.persistence.LevelLoader;
import model.domain.token.base.Token;
import model.domain.token.base.ITurnableToken;

import view.GamePanel;
import view.RenderableTile;
import view.GamePanelUIBinder;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;

public class LevelController {
    private final GamePanel gamePanel;
    private final ScreenController screenController;
    private final SoundManager soundManager = new SoundManager();
    private int currentLevelNumber;

    public LevelController(GamePanel gamePanel, ScreenController screenController) {
        this.gamePanel = gamePanel;
        this.screenController = screenController;
    }

    public void loadLevel(int levelNumber) {
        this.currentLevelNumber = levelNumber;


        gamePanel.resetBoardUI();
        gamePanel.clearMouseListeners();
        gamePanel.createControlButtons();
        gamePanel.showBoardUI();


        Level level = LevelLoader.load(levelNumber);
        Board board = level.getBoard();


        soundManager.stopBackground(); // ensure old track doesn't stack
        soundManager.play(SoundManager.Sound.BACKGROUND, true);

        TileContainer inventory = InventoryBuilder.buildInventory(level.getRequiredTokens());
        level.setInventory(inventory);

        RenderableTileFactory tileFactory = new RenderableTileFactory();

        gamePanel.setInventory(inventory);
        List<RenderableTile> inventoryTiles = tileFactory.convertBoardToRenderableTiles(inventory);
        gamePanel.setInventoryTilesToRender(inventoryTiles);


        List<RenderableTile> tiles = tileFactory.convertBoardToRenderableTiles(board);
        gamePanel.setTilesToRender(tiles);

        GameController gameController = new GameController(level);

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
                    List<PositionDirection> path = gameController.getCurrentLaserPath();

                    System.out.println("Laser path size: " + path.size());
                    gamePanel.setLaserPath(path);

                    gamePanel.getControlPanel().boardRenderer.setLaserPath(path);
                    gamePanel.getControlPanel().boardRenderer.repaint();

                    List<RenderableTile> updated = tileFactory.convertBoardToRenderableTiles(board);
                    gamePanel.setTilesToRender(updated);
                    gamePanel.getControlPanel().boardRenderer.setTilesToRender(updated);
                    gamePanel.repaint();
                    gamePanel.getControlPanel().boardRenderer.repaint();
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
        JButton restart = gamePanel.getRestartButton();
        JButton exit = gamePanel.getExitButton();
        JButton saveExit = gamePanel.getSaveAndExitButton();

        restart.setVisible(true);
        exit.setVisible(true);
        saveExit.setVisible(true);

        restart.addActionListener(e -> {
            System.out.println("Restart clicked");
            loadLevel(currentLevelNumber); // reload fresh
        });

        ActionListener exitAction = e -> {
            System.out.println("Exit or Save & Exit clicked");
            soundManager.stopBackground();
            gamePanel.resetBoardUI();
            screenController.showTitleScreen();
        };

        exit.addActionListener(exitAction);
        saveExit.addActionListener(exitAction);
    }
}
