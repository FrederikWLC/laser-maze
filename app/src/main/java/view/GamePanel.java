package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.Map;
import java.util.HashMap;
import model.domain.board.Position;
import model.domain.token.ITurnableToken;

import model.domain.token.Token;
import model.domain.board.Direction;
import model.domain.board.PositionDirection;


public class GamePanel extends JPanel {

    private JButton quitGame;
    private JButton singlePlayer;
    private JButton multiplayer;
    private JButton backButton;
    private JButton fireLaserButton;


    private JScrollPane levelScrollPane;
    private JPanel levelListPanel;

    private List<RenderableTile> tilesToRender = new ArrayList<>();

    private final Map<String, TokenRenderer> tokenRenderers = new HashMap<>();

    private IntConsumer levelSelectAction;

    private DisplayManager currentScreen;

    private Runnable singlePlayerAction;

    private ActionListener fireLaserListener;






    private final List<Drawable> drawables = new ArrayList<>();

    private final Map<String, BufferedImage> tokenImages = new HashMap<>();

    private final Map<String, TokenRenderer> staticRenderers = new HashMap<>();
    private final Map<String, ITurnableTokenRenderer> turnableRenderers = new HashMap<>();



    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null); // Absolute positioning

        loadTokenImages();

        BufferedImage bgImage = loadBackgroundImage();
        drawables.add(new BackgroundRenderer(bgImage));
        drawables.add(new TitleRenderer("LASER MAZE", 160));

        setupTitleButtons();
        setupLevelSelectScreen();





    }

    private BufferedImage loadBackgroundImage() {
        try {
            return ImageIO.read(getClass().getResource("/background/thelasermaze.jpeg"));
        } catch (Exception e) {
            System.err.println("Could not load background image. Using fallback.");
            return null;
        }
    }

    private void setupTitleButtons() {
        int w = 160, h = 40, x = (800 - w) / 2;

        singlePlayer = new JButton("Single Player");
        multiplayer = new JButton("Multiplayer");
        quitGame = new JButton("Quit Game");

        singlePlayer.setBounds(x, 280, w, h);
        multiplayer.setBounds(x, 340, w, h);
        quitGame.setBounds(640, 560, w, h);

        // We'll delegate this to the controller
        singlePlayer.addActionListener(e -> {
            if (singlePlayerAction != null) {
                singlePlayerAction.run();
            }
        });

        add(singlePlayer);
        add(multiplayer);
        add(quitGame);
    }

    private void setupLevelSelectScreen() {
        levelListPanel = new JPanel();
        levelListPanel.setLayout(new GridLayout(0, 3, 10, 10));

        for (int i = 1; i <= 60; i++) {
            int levelNumber = i; // must be final or effectively final for lambda

            JButton levelButton = new JButton("Level " + levelNumber);

            // Notify the controller when clicked
            levelButton.addActionListener(e -> {
                if (levelSelectAction != null) {
                    levelSelectAction.accept(levelNumber);
                }
            });

            levelListPanel.add(levelButton);
        }

        levelScrollPane = new JScrollPane(levelListPanel);
        levelScrollPane.setBounds(150, 50, 500, 400);
        levelScrollPane.setVisible(false);
        add(levelScrollPane);

        backButton = new JButton("Back");
        backButton.setBounds(320, 470, 160, 40);
        backButton.setVisible(false);
        backButton.addActionListener(e -> showTitleScreen());
        add(backButton);
    }


    private void showLevelSelectScreen() {
        singlePlayer.setVisible(false);
        multiplayer.setVisible(false);
        quitGame.setVisible(false);

        levelScrollPane.setVisible(true);
        backButton.setVisible(true);
    }

    private void showTitleScreen() {
        // Clear all previous drawables (including board renderers)
        drawables.clear();

        // Re-add background and title
        drawables.add(new BackgroundRenderer(loadBackgroundImage()));
        drawables.add(new TitleRenderer("LASER MAZE", 160));

        // Restore title menu buttons
        singlePlayer.setVisible(true);
        multiplayer.setVisible(true);
        quitGame.setVisible(true);

        // Hide board-related UI
        levelScrollPane.setVisible(false);
        backButton.setVisible(false);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw background layers (title, etc.)
        if (currentScreen != null) {
            currentScreen.draw(g2d);
        }


        if (!tilesToRender.isEmpty()) {
            int tileSize = 80;

            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    int x = 100 + col * tileSize;
                    int y = 100 + row * tileSize;

                    RenderableTile match = findMatchingTile(col, row);

                    if (match != null) {
                        drawToken(g2d, match.getTokenType(), match.getDirection(), x, y, tileSize);
                    } else {
                        BufferedImage emptyTile = tokenImages.get("EmptyCell.png");
                        if (emptyTile != null) {
                            g2d.drawImage(emptyTile, x, y, tileSize, tileSize, null);
                        } else {
                            g2d.setColor(Color.LIGHT_GRAY);
                            g2d.fillRect(x, y, tileSize, tileSize);
                        }
                    }

                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y, tileSize, tileSize);
                }
            }




        }
        // Draw laser path if available
        if (laserPath != null && !laserPath.isEmpty()) {
            g2d.setColor(Color.RED);
            int tileSize = 80;
            for (PositionDirection pd : laserPath) {
                int x = 100 + pd.getPosition().getX() * tileSize;
                int y = 100 + pd.getPosition().getY() * tileSize;
                g2d.fillRect(x + 30, y + 30, 20, 20);
            }
        }


        g2d.dispose();

    }


    private void loadTokenImages() {
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

                "BlueMirror-BACKSLASH_MIRROR.png",
                "BlueMirror-SLASH_MIRROR.png",

                "YellowBridge-HORIZONTAL_BRIDGE.png",
                "YellowBridge-VERTICAL_BRIDGE.png",

                "WhiteObstacle-NONE-Dark.png",
                "EmptyCell.png"
                // Add more as needed
        };

        for (String name : filenames) {
            try {

                BufferedImage img = ImageIO.read(getClass().getResource("/tokens/" + name));
                tokenImages.put(name, img);
            } catch (Exception e) {
                System.err.println("Failed to load: " + name);
            }
        }
    }

    private void drawToken(Graphics2D g2d, String tokenType, Direction direction, int x, int y, int tileSize) {


        TokenRenderer staticRenderer = staticRenderers.get(tokenType);
        ITurnableTokenRenderer turnableRenderer = turnableRenderers.get(tokenType);

        if (turnableRenderer != null) {
            ITurnableToken dummy = new RenderToken(direction);
            turnableRenderer.render(g2d, dummy, x, y, tileSize);
        } else if (staticRenderer != null) {
            Token dummy = new StaticDummyToken();  // or any Token subclass
            staticRenderer.render(g2d, dummy, x, y, tileSize);
        } else {
            g2d.setColor(Color.MAGENTA);
            g2d.drawString("?", x + tileSize / 2 - 4, y + tileSize / 2 + 4);
        }

    }




    public Position screenToBoard(int pixelX, int pixelY) {
        int tileSize = ViewConfig.TILE_SIZE;
        int offsetX = ViewConfig.BOARD_OFFSET_X;
        int offsetY = ViewConfig.BOARD_OFFSET_Y;

        int col = (pixelX - offsetX) / tileSize;
        int row = (pixelY - offsetY) / tileSize;

        if (col < 0 || row < 0 || col >= 5 || row >= 5) {
            return null; // Click was outside the board
        }

        return new Position(col, row);
    }


    public void setLevelSelectAction(IntConsumer action) {
        this.levelSelectAction = action;
    }
    public void showBoard() {


        // Clear existing drawables
        drawables.clear();
        //drawables.add(new BackgroundRenderer(loadBackgroundImage()));
        drawables.add(new EmptyTileRenderer(tokenImages.get("EmptyCell.png")));  // Renders empty board tiles

        if (fireLaserButton == null) {
            fireLaserButton = new JButton("Fire Laser");
            fireLaserButton.setBounds(20, 20, 120, 30);
            add(fireLaserButton);
            if (fireLaserListener != null) {
                fireLaserButton.addActionListener(fireLaserListener);
            }
        }
        fireLaserButton.setVisible(true);
        fireLaserButton.setBackground(Color.ORANGE); // for visibility debugging




        // Hide menu buttons
        singlePlayer.setVisible(false);
        multiplayer.setVisible(false);
        quitGame.setVisible(false);
        levelScrollPane.setVisible(false);
        backButton.setVisible(false);

        repaint();
    }


    public void setQuitGameAction(ActionListener listener) {
        quitGame.addActionListener(listener);
    }

    public void setFireLaserAction(ActionListener listener) {
        System.out.println("Attaching fire laser listener");
        if (fireLaserButton != null) {
            fireLaserButton.addActionListener(listener);
        }
    }




    private String mapDirection(Direction dir) {
        return switch (dir) {
            case UP -> "NORTH";
            case DOWN -> "SOUTH";
            case LEFT -> "WEST";
            case RIGHT -> "EAST";
        };
    }

    public void setTilesToRender(List<RenderableTile> tiles) {
        this.tilesToRender = tiles;
    }
    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }
    private RenderableTile findMatchingTile(int x, int y) {
        for (RenderableTile tile : tilesToRender) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }
    public void setTokenImages(Map<String, BufferedImage> images) {
        tokenImages.clear();
        tokenImages.putAll(images);
    }
    public void switchToScreen(DisplayManager screen) {
        this.currentScreen = screen;
        screen.show();
        repaint();
    }
    public void setMainMenuVisible(boolean visible) {
        singlePlayer.setVisible(visible);
        multiplayer.setVisible(visible);
        quitGame.setVisible(visible);
    }

    public void setLevelSelectVisible(boolean visible) {
        levelScrollPane.setVisible(visible);
    }

    public void setBackButtonVisible(boolean visible) {
        backButton.setVisible(visible);
    }
    public void clearDrawables() {
        drawables.clear();
    }
    public List<Drawable> getDrawables() {
        return drawables;
    }

    public void setSinglePlayerAction(Runnable action) {
        this.singlePlayerAction = action;
    }
    public Map<String, BufferedImage> getTokenImages() {
        return tokenImages;
    }

    private static class RenderToken extends Token implements ITurnableToken {
        private final Direction direction;

        public RenderToken(Direction direction) {
            this.direction = direction;
        }

        @Override
        public Direction getDirection() {
            return direction;
        }

        @Override
        public void setDirection(Direction direction) {
            // No-op for rendering
        }

        @Override
        public boolean isTurnable() {
            return false;
        }
        @Override
        public boolean isTurned() {
            return false; // Or true, depending on what your renderers expect
        }
        @Override
        public void setTurnable(boolean turnable) {
            // no-op for dummy token
        }
    }

    public void setTokenRenderers(Map<String, TokenRenderer> renderers) {
        tokenRenderers.clear();
        tokenRenderers.putAll(renderers);
    }
    public void setStaticRenderers(Map<String, TokenRenderer> renderers) {
        staticRenderers.clear();
        staticRenderers.putAll(renderers);
    }

    public void setTurnableRenderers(Map<String, ITurnableTokenRenderer> renderers) {
        turnableRenderers.clear();
        turnableRenderers.putAll(renderers);
    }
    private static class StaticDummyToken extends Token {}


    public void setTurnableTokenRenderers(Map<String, ITurnableTokenRenderer> renderers) {
        turnableRenderers.clear();
        turnableRenderers.putAll(renderers);
    }
    private List<PositionDirection> laserPath = new ArrayList<>();

    public void setLaserPath(List<PositionDirection> path) {
        this.laserPath = path;
        repaint();
    }








}
