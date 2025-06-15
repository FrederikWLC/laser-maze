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


import model.*;

public class GamePanel extends JPanel {

    private JButton quitGame;
    private JButton singlePlayer;
    private JButton multiplayer;
    private JButton backButton;
    private JButton fireLaserButton;

    private JScrollPane levelScrollPane;
    private JPanel levelListPanel;

    private Board board;

    private IntConsumer levelSelectAction;

    private String[][] boardLayout;

    private boolean isInBoardView = false;
    private int currentLevel = -1;

    private final List<Drawable> drawables = new ArrayList<>();

    private final Map<String, BufferedImage> tokenImages = new HashMap<>();


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

        singlePlayer.addActionListener(e -> showLevelSelectScreen());
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
        levelScrollPane.setVisible(false);
        backButton.setVisible(false);

        singlePlayer.setVisible(true);
        multiplayer.setVisible(true);
        quitGame.setVisible(true);

        //Hiding laser button
        if (fireLaserButton != null) fireLaserButton.setVisible(false);
    }
    private List<PositionDirection> laserPath = new ArrayList<>();
    public void setLaserPath(List<PositionDirection> path) {
        this.laserPath = path;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        for (Drawable d : drawables) {
            d.draw(g2d);
        }

        if (isInBoardView && board != null) {
            int tileSize = ViewConfig.TILE_SIZE;;
            for (int row = 0; row < board.getHeight(); row++) {
                for (int col = 0; col < board.getWidth(); col++) {
                    int x = ViewConfig.BOARD_OFFSET_X + col * tileSize;
                    int y = ViewConfig.BOARD_OFFSET_Y + row * tileSize;

                    BufferedImage emptyTile = tokenImages.get("EmptyCell.png");
                    if (emptyTile != null) {
                        g2d.drawImage(emptyTile, x, y, tileSize, tileSize, null);
                    } else {
                        g2d.setColor(Color.LIGHT_GRAY);
                        g2d.fillRect(x, y, tileSize, tileSize);
                    }

                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y, tileSize, tileSize);

                    Tile tile = board.getTile(col, row);
                    if (!tile.isEmpty()) {
                        drawToken(g2d, tile.getToken(), x, y, tileSize);
                    }
                }
            }
        }

        //Very basic logic for drawing a laser. Needs to be remade!
        g2d.setColor(Color.RED);
        for (PositionDirection pd : laserPath) {
            int x = 100 + pd.getPosition().getX() * 80;
            int y = 100 + pd.getPosition().getY() * 80;
            g2d.fillRect(x + 30, y + 30, 20, 20); // Adjust for visual centering
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
                e.printStackTrace(); // shows the real error

            }
        }
    }


    private void drawToken(Graphics2D g2d, Token token, int x, int y, int tileSize) {
        String filename = null;

        if (token instanceof ITurnableToken directionalToken) {
            String direction = mapDirection(directionalToken.getDirection());
            filename = switch (token.getClass().getSimpleName()) {
                case "LaserToken" -> "RedLaser-GENERATOR_ON_" + direction + ".png";
                case "TargetMirrorToken" -> "PurpleTarget-TARGET_ON_" + direction + ".png";
                default -> null;
            };
        } else {
            filename = switch (token.getClass().getSimpleName()) {
                case "DoubleMirrorToken" -> "GreenMirror-BACKSLASH_MIRROR.png";
                case "CellBlockerToken" -> "WhiteObstacle-NONE-Dark.png";
                // Add more tokens '
                default -> null;
            };
        }

        if (filename != null) {
            BufferedImage img = tokenImages.get(filename);
            if (img != null) {
                g2d.drawImage(img, x, y, tileSize, tileSize, null);
                return;
            }
        }

        // Fallback
        g2d.setColor(Color.MAGENTA);
        g2d.drawString("?", x + tileSize / 2 - 4, y + tileSize / 2 + 4);
    }

    public Position screenToBoard(int pixelX, int pixelY) {
        int tileSize = ViewConfig.TILE_SIZE;
        int offsetX = ViewConfig.BOARD_OFFSET_X;
        int offsetY = ViewConfig.BOARD_OFFSET_Y;

        int col = (pixelX - offsetX) / tileSize;
        int row = (pixelY - offsetY) / tileSize;

        if (board == null || col < 0 || row < 0 || col >= board.getWidth() || row >= board.getHeight()) {
            return null; // Click was outside the board
        }

        return new Position(col, row);
    }


    public void setLevelSelectAction(IntConsumer action) {
        this.levelSelectAction = action;
    }
    public void showBoard(int levelNumber) {
        this.isInBoardView = true;
        this.currentLevel = levelNumber;

        // Hide menu buttons
        singlePlayer.setVisible(false);
        multiplayer.setVisible(false);
        quitGame.setVisible(false);
        levelScrollPane.setVisible(false);
        backButton.setVisible(false);

        if (fireLaserButton == null) {
            fireLaserButton = new JButton("Fire Laser");
            fireLaserButton.setBounds(20, 20, 120, 30);
            add(fireLaserButton);
        }
        fireLaserButton.setVisible(true);

        repaint();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setFireLaserAction(ActionListener listener) {
        if (fireLaserButton != null) {
            fireLaserButton.addActionListener(listener);
        }
    }
    public void setQuitGameAction(ActionListener listener) {
        quitGame.addActionListener(listener);
    }

    private String mapDirection(Direction dir) {
        return switch (dir) {
            case UP -> "NORTH";
            case DOWN -> "SOUTH";
            case LEFT -> "WEST";
            case RIGHT -> "EAST";
        };
    }






}
