package controller;

import view.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.IntConsumer;

public class ScreenController {
    private final GamePanel gamePanel;
    private final MainController mainController;

    private DisplayManager titleScreen;
    private DisplayManager levelSelectScreen;

    public ScreenController(GamePanel gamePanel, MainController mainController) {
        this.gamePanel = gamePanel;
        this.mainController = mainController;
    }

    public void setupScreens(IntConsumer onLevelSelectClick) {
        gamePanel.setOnQuitClick(() -> System.exit(0));
        gamePanel.setOnLevelSelectClick(onLevelSelectClick);

        gamePanel.setOnSinglePlayerClick(() -> gamePanel.switchToScreen(levelSelectScreen));
        gamePanel.setOnBackClick(() -> gamePanel.switchToScreen(titleScreen));

        try {
            BufferedImage bg = ImageIO.read(getClass().getResource("/background/thelasermaze.jpeg"));
            titleScreen = new TitleScreenManager(gamePanel, bg);
        } catch (Exception e) {
            System.err.println("Failed to load title screen background.");
        }

        levelSelectScreen = new LevelSelectScreenManager(gamePanel);
    }

    public void showTitleScreen() {
        gamePanel.switchToScreen(titleScreen);
    }

    public void showBoardScreen(List<RenderableTile> tiles) {
        DisplayManager boardScreen = new BoardScreenManager(
                gamePanel,
                tiles,
                gamePanel.getTokenImages()
        );
        gamePanel.switchToScreen(boardScreen);
    }

    public MainController getMainController() {
        return mainController;
    }
}
