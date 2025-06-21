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
        GamePanelUIBinder binder = new GamePanelUIBinder(gamePanel);
        binder.bindAll(
                () -> gamePanel.switchToScreen(levelSelectScreen), // single player
                () -> {}, // multiplayer (if unused for now)
                () -> System.exit(0), // quit
                () -> gamePanel.switchToScreen(titleScreen), // back
                null, // fireLaser (set later in LevelController)
                onLevelSelectClick
        );


        try {
            BufferedImage bg = ImageIO.read(getClass().getResource("/textures/background/thelasermaze.jpeg"));
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

    public void bindFireLaserListener(java.awt.event.ActionListener fireLaserListener) {
        gamePanel.setFireLaserListener(fireLaserListener);
    }

}
