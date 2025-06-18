package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.Map;
import java.util.HashMap;
import model.domain.board.Position;
import model.domain.token.ITurnableToken;

import model.domain.token.Token;
import model.domain.board.Direction;
import model.domain.board.PositionDirection;

import view.util.TokenImageLoader;
import view.util.GameUIBuilder;
import view.rendering.DrawableManager;
public class GamePanel extends JPanel {

    private JButton quitGame;
    private JButton singlePlayer;
    private JButton multiplayer;
    private JButton backButton;
    private JButton fireLaserButton;

    private JScrollPane levelScrollPane;
    private JPanel levelListPanel;

    private final Map<String, TokenRenderer> tokenRenderers = new HashMap<>();

    private DisplayManager currentScreen;

    private BoardRendererPanel boardRenderer;

    private ActionListener fireLaserListener;


    private final DrawableManager drawableManager = new DrawableManager();


    private BufferedImage backgroundImage;


    private final Map<String, BufferedImage> tokenImages = new HashMap<>();
    private final Map<String, TokenRenderer> staticRenderers = new HashMap<>();
    private final Map<String, ITurnableTokenRenderer> turnableRenderers = new HashMap<>();




    public GamePanel(TokenImageLoader loader) {
        tokenImages.putAll(loader.loadTokenImages());
        backgroundImage = loader.loadBackgroundImage();

        setPreferredSize(new Dimension(800, 600));
        setLayout(null); // Absolute positioning

        boardRenderer = new BoardRendererPanel();
        boardRenderer.setBounds(0, 0, 800, 600); // Full size, or just board area
        boardRenderer.setOpaque(false);
        add(boardRenderer);
        boardRenderer.setVisible(false); // Hidden unless in game view



        singlePlayer = new JButton("Single Player");
        multiplayer = new JButton("Multiplayer");
        quitGame = new JButton("Quit Game");

        levelListPanel = new JPanel();
        levelScrollPane = new JScrollPane(levelListPanel);
        backButton = new JButton("Back");


        GameUIBuilder uiBuilder = new GameUIBuilder();
        uiBuilder.setupTitleButtons(this, singlePlayer, multiplayer, quitGame);
        uiBuilder.setupLevelSelectScreen(this, levelListPanel, levelScrollPane, backButton);


    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (currentScreen != null) {
            currentScreen.draw(g2d);
        } else {
            drawableManager.drawAll(g2d);
        }

        g2d.dispose();
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

    public void setOnLevelSelectClick(IntConsumer action) {
        for (Component comp : levelListPanel.getComponents()) {
            if (comp instanceof JButton button) {
                String text = button.getText(); // "Level 1", etc.
                int levelNumber = Integer.parseInt(text.replace("Level ", ""));
                button.addActionListener(e -> action.accept(levelNumber));
            }
        }
    }



    public void setOnQuitClick(Runnable action) {
        quitGame.addActionListener(e -> action.run());
    }
    public void setOnFireLaserClick(ActionListener listener) {
        this.fireLaserListener = listener;
        if (fireLaserButton != null) {
            fireLaserButton.addActionListener(listener);
        }
    }
    public void setTilesToRender(List<RenderableTile> tiles) {

        boardRenderer.setTilesToRender(tiles);
    }
    public void addDrawable(Drawable drawable) {

        drawableManager.add(drawable);
    }

    public void setTokenImages(Map<String, BufferedImage> images) {
        boardRenderer.setTokenImages(images);
    }
    public void switchToScreen(DisplayManager screen) {
        this.currentScreen = screen;
        screen.show();
        repaint();
    }
    public void clearDrawables() {

        drawableManager.clear();
    }

    public void setOnSinglePlayerClick(Runnable action) {
        singlePlayer.addActionListener(e -> action.run());
    }


    public Map<String, BufferedImage> getTokenImages() {

        return tokenImages;
    }

    public static class RenderToken extends Token implements ITurnableToken {
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
    public void setStaticRenderers(Map<String, TokenRenderer> renderers) {
        boardRenderer.setStaticRenderers(renderers);
    }
    public void setTurnableRenderers(Map<String, ITurnableTokenRenderer> renderers) {
        boardRenderer.setTurnableRenderers(renderers);
    }
    public static class StaticDummyToken extends Token {
    }
    public void setLaserPath(List<PositionDirection> path) {
        boardRenderer.setLaserPath(path);
    }
    public void setOnMultiplayerClick(Runnable action) {
        multiplayer.addActionListener(e -> action.run());
    }
    public JButton getSinglePlayerButton() {
        return singlePlayer;
    }

    public JButton getMultiplayerButton() {
        return multiplayer;
    }

    public JButton getQuitGameButton() {
        return quitGame;
    }

    public JScrollPane getLevelScrollPane() {
        return levelScrollPane;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getFireLaserButton() {
        return fireLaserButton;
    }

    public boolean hasFireLaserButton() {
        return fireLaserButton != null;
    }

    public void createFireLaserButton() {
        fireLaserButton = new JButton("Fire Laser");
        fireLaserButton.setBounds(20, 20, 120, 30);
        add(fireLaserButton);
    }

    public BoardRendererPanel getBoardRenderer() {
        return boardRenderer;
    }
    public void setOnBackClick(Runnable action) {
        backButton.addActionListener(e -> action.run());
    }
    public DrawableManager getDrawableManager() {
        return drawableManager;
    }




}
