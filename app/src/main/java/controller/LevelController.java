package controller;

import model.domain.board.Board;
import model.domain.board.PositionDirection;
import model.domain.level.Level;
import model.persistence.storage.DefaultLevelLoader;
import model.persistence.storage.LevelIOHandler;
import model.persistence.storage.LevelSaver;
import model.persistence.storage.SavedLevelLoader;
import view.GamePanel;
import view.RenderableTile;

import java.util.List;
import view.GamePanelUIBinder;


public class LevelController {
    private final GamePanel gamePanel;
    private final ScreenController screenController;
    private final SoundManager soundManager = new SoundManager();
    private final DefaultLevelLoader defaultLevelLoader = new DefaultLevelLoader();
    private final SavedLevelLoader savedLevelLoader = new SavedLevelLoader();
    private final LevelSaver levelSaver = new LevelSaver();
    private final LevelIOHandler levelIOHandler = new LevelIOHandler(defaultLevelLoader,savedLevelLoader,levelSaver);

    public LevelController(GamePanel gamePanel, ScreenController screenController) {
        this.gamePanel = gamePanel;
        this.screenController = screenController;
    }

    public void loadLevel(int levelNumber) {
        Level level = levelIOHandler.load(levelNumber);
        Board board = level.getBoard();
        soundManager.play(SoundManager.Sound.BACKGROUND, true);


        RenderableTileFactory tileFactory = new RenderableTileFactory();
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


                    List<RenderableTile> updatedTiles = tileFactory.convertBoardToRenderableTiles(board);
                    gamePanel.setTilesToRender(updatedTiles);
                    gamePanel.getControlPanel().boardRenderer.setTilesToRender(updatedTiles);
                    gamePanel.repaint();
                    gamePanel.getControlPanel().boardRenderer.repaint();
                },
                null
        );


        InputHandler inputHandler = new InputHandler(gameController, gamePanel, tileFactory, soundManager);
        gamePanel.addMouseListener(inputHandler);
        gamePanel.addMouseMotionListener(inputHandler);

        screenController.showBoardScreen(tiles);
        gamePanel.repaint();
    }
}
