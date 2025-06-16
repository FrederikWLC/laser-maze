package controller;

import model.*;
import view.GamePanel;

import java.util.List;

import javax.swing.*;

import model.Board;

import model.Token;

import java.util.ArrayList;
import view.RenderableTile;

import java.util.Map;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import view.TokenRenderer;
import view.LaserTokenRenderer;
import view.TargetMirrorTokenRenderer;
import view.DoubleMirrorTokenRenderer;
import view.CellBlockerTokenRenderer;
import view.SplitterBeamTokenRenderer;
import view.CheckpointTokenRenderer;
import view.DisplayManager;
import view.TitleScreenManager;
import view.LevelSelectScreenManager;
import view.BoardScreenManager;
import view.ITurnableTokenRenderer;




public class MainController {
    private GamePanel gamePanel;
    private Board board;
    private GameController gameController;

    private enum ScreenState { MENU, LEVEL_SELECT, BOARD }
    private ScreenState currentScreen = ScreenState.MENU;
    private int currentLevel = -1;


    public void startGame() {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Laser Maze");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

            gamePanel = new GamePanel();

            gamePanel.setQuitGameAction(e -> System.exit(0));
            gamePanel.setLevelSelectAction(this::loadLevel);

            Map<String, BufferedImage> tokenImages = loadTokenImages();

            Map<String, ITurnableTokenRenderer> turnableRenderers = new HashMap<>();
            Map<String, TokenRenderer> staticRenderers = new HashMap<>();

            BufferedImage bgImage = null;
            try {
                bgImage = ImageIO.read(getClass().getResource("/background/thelasermaze.jpeg"));
            } catch (Exception ex) {
                System.err.println("Could not load title screen background image.");
            }


            DisplayManager titleScreen = new TitleScreenManager(gamePanel, bgImage);
            DisplayManager levelSelectScreen = new LevelSelectScreenManager(gamePanel);

            gamePanel.setSinglePlayerAction(() -> {
                gamePanel.switchToScreen(levelSelectScreen);
            });


            gamePanel.switchToScreen(titleScreen);




            turnableRenderers.put("LaserToken", new LaserTokenRenderer(tokenImages));
            turnableRenderers.put("TargetMirrorToken", new TargetMirrorTokenRenderer(tokenImages));
            turnableRenderers.put("DoubleMirrorToken", new DoubleMirrorTokenRenderer(tokenImages));
            staticRenderers.put("CellBlockerToken", new CellBlockerTokenRenderer(tokenImages));
            turnableRenderers.put("BeamSplitterToken", new SplitterBeamTokenRenderer(tokenImages));
            turnableRenderers.put("CheckpointToken", new CheckpointTokenRenderer(tokenImages));

            gamePanel.setTurnableTokenRenderers(turnableRenderers);
            gamePanel.setTokenImages(tokenImages);


            window.add(gamePanel);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);



        });
    }
    private Map<String, BufferedImage> loadTokenImages() {
        String[] filenames = {
                "RedLaser-GENERATOR_ON_NORTH.png",
                "RedLaser-GENERATOR_ON_EAST.png",
                "RedLaser-GENERATOR_ON_SOUTH.png",
                "RedLaser-GENERATOR_ON_WEST.png",

                "PurpleTarget-TARGET_ON_NORTH.png",
                "PurpleTarget-TARGET_ON_EAST.png",
                "PurpleTarget-TARGET_ON_SOUTH.png",
                "PurpleTarget-TARGET_ON_WEST.png",

                "GreenMirror-BACKSLASH_MIRROR.png",
                "GreenMirror-SLASH_MIRROR.png",

                "YellowBridge-HORIZONTAL_BRIDGE.png",
                "YellowBridge-VERTICAL_BRIDGE.png",

                "BlueMirror-BACKSLASH_MIRROR.png",
                "BlueMirror-SLASH_MIRROR.png",

                "WhiteObstacle-NONE-Dark.png",
                "EmptyCell.png",

        };

        Map<String, BufferedImage> images = new HashMap<>();
        for (String name : filenames) {
            try {
                BufferedImage img = ImageIO.read(getClass().getResource("/tokens/" + name));
                images.put(name, img);
            } catch (Exception e) {
                System.err.println("Failed to load: " + name);
            }
        }


        return images;

    }


   // public void loadLevel(int levelNumber) {
  //      Level level =LevelLoader.load(levelNumber);// or dynamic size per level
   //     Board board = level.getBoard();
   //     //gamePanel.setBoard(board); obsolete
  //      gamePanel.showBoard();
  //  }
   public void loadLevel(int levelNumber) {
       Level level = LevelLoader.load(levelNumber);
       Board board = level.getBoard();

       // Convert board model to renderable tiles
       List<RenderableTile> renderableTiles = convertBoardToRenderableTiles(board);

       // Pass tiles to GamePanel for rendering
       gamePanel.setTilesToRender(renderableTiles);
       gamePanel.setTokenImages(gamePanel.getTokenImages()); // Assuming images already loaded

       // Create and switch to the board screen
       DisplayManager boardScreen = new BoardScreenManager(gamePanel, renderableTiles, gamePanel.getTokenImages());
       gamePanel.switchToScreen(boardScreen);

       // Setup game controller with loaded level
       this.gameController = new GameController(level);

       List<RenderableTile> initialTiles = convertBoardToRenderableTiles(board);
       gamePanel.setTilesToRender(initialTiles);


       // Show board UI components and repaint
       gamePanel.showBoard();

       // Setup Fire Laser button action with the new controller
       setupFireLaserAction();

       // Add input listeners for token interaction
       InputHandler inputHandler = new InputHandler(gameController, gamePanel, this);
       gamePanel.addMouseListener(inputHandler);
       gamePanel.addMouseMotionListener(inputHandler);



       gamePanel.repaint();
   }

    public List<RenderableTile> convertBoardToRenderableTiles(Board board) {
        List<RenderableTile> renderables = new ArrayList<>();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Tile tile = board.getTile(x, y);
                if (!tile.isEmpty()) {
                    Token token = tile.getToken();
                    String tokenType = token.getClass().getSimpleName();
                    Direction direction = token instanceof ITurnableToken turnable ? turnable.getDirection() : null;
                    renderables.add(new RenderableTile(x, y, tokenType, direction));
                }
            }
        }
        return renderables;
    }
    private void setupFireLaserAction() {
        gamePanel.setFireLaserAction(e -> {
            System.out.println("Fire Laser button clicked!");
            gameController.triggerLaser(true);
            List<PositionDirection> path = gameController.getCurrentLaserPath();
            System.out.println("Laser path:");
            for (PositionDirection pd : path) {
                System.out.println(" -> " + pd);
            }
            gamePanel.setLaserPath(path);
        });
    }





}
