package controller;

import view.GamePanel;
import view.util.TokenImageLoader;

import javax.swing.*;

import java.util.Map;
import java.util.HashMap;

import view.TokenRenderer;
import view.ITurnableTokenRenderer;
import view.LaserTokenRenderer;
import view.TargetMirrorTokenRenderer;
import view.DoubleMirrorTokenRenderer;
import view.CellBlockerTokenRenderer;
import view.SplitterBeamTokenRenderer;
import view.CheckpointTokenRenderer;
import java.awt.image.BufferedImage;
import view.GameControlPanel;



public class MainController {
    private final GamePanel gamePanel;
    private final JFrame window;
    private final ScreenController screenController;
    private final LevelController levelController;
    private GameControlPanel controlPanel;


    public MainController() {
        this.window = new JFrame("Laser Maze");

        this.controlPanel = new GameControlPanel();
        this.gamePanel = new GamePanel(new TokenImageLoader(), controlPanel);

        registerRenderers();

        this.screenController = new ScreenController(gamePanel, this);
        this.levelController = new LevelController(gamePanel, screenController);
    }
    private void registerRenderers(){
        TokenImageLoader loader = new TokenImageLoader();
        Map<String, BufferedImage> tokenImages = loader.loadTokenImages();


        Map<String, TokenRenderer> staticRenderers = new HashMap<>();
        Map<String, ITurnableTokenRenderer> turnableRenderers = new HashMap<>();

        turnableRenderers.put("LaserToken", new LaserTokenRenderer(tokenImages));
        turnableRenderers.put("TargetMirrorToken", new TargetMirrorTokenRenderer(tokenImages));
        turnableRenderers.put("DoubleMirrorToken", new DoubleMirrorTokenRenderer(tokenImages));
        staticRenderers.put("CellBlockerToken", new CellBlockerTokenRenderer(tokenImages));
        turnableRenderers.put("BeamSplitterToken", new SplitterBeamTokenRenderer(tokenImages));
        turnableRenderers.put("CheckpointToken", new CheckpointTokenRenderer(tokenImages));

        gamePanel.setStaticRenderers(staticRenderers);
        gamePanel.setTurnableRenderers(turnableRenderers);
        gamePanel.setTokenImages(tokenImages);




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
