package controller;

import model.domain.board.Board;
import model.domain.board.PositionDirection;
import model.domain.level.Level;
import model.domain.token.ITurnableToken;
import model.domain.token.Token;
import model.persistence.LevelLoader;
import view.GamePanel;
import view.RenderableTile;

import java.util.List;

public class LevelController {
    private final GamePanel gamePanel;
    private final ScreenController screenController;

    public LevelController(GamePanel gamePanel, ScreenController screenController) {
        this.gamePanel = gamePanel;
        this.screenController = screenController;
    }

    public void loadLevel(int levelNumber) {
        Level level = LevelLoader.load(levelNumber);
        Board board = level.getBoard();

        RenderableTileFactory tileFactory = new RenderableTileFactory(); // ✅ create factory
        List<RenderableTile> tiles = tileFactory.convertBoardToRenderableTiles(board);
        gamePanel.setTilesToRender(tiles);

        GameController gameController = new GameController(level);

        gamePanel.setOnFireLaserClick(e -> {
            gameController.triggerLaser(true);
            List<PositionDirection> path = gameController.getCurrentLaserPath();
            gamePanel.setLaserPath(path);
        });

        // ✅ PASS factory to InputHandler, not MainController
        InputHandler inputHandler = new InputHandler(gameController, gamePanel, tileFactory);
        gamePanel.addMouseListener(inputHandler);
        gamePanel.addMouseMotionListener(inputHandler);

        screenController.showBoardScreen(tiles);
        gamePanel.repaint();
    }
}
