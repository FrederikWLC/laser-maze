package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import model.domain.board.Position;
import model.domain.board.PositionTurn;
import model.domain.board.TileContainer;
import view.util.TokenImageLoader;
import view.util.GameUIBuilder;
import view.rendering.DrawableManager;
import javax.swing.JOptionPane;


public class GamePanel extends JPanel {

    private JButton quitGame;
    private JButton singlePlayer;
    private JButton multiplayer;
    private JButton backButton;
    private JButton fireLaserButton;
    private JButton restartButton;
    private JButton exitButton;
    private JButton saveAndExitButton;


    private JScrollPane levelScrollPane;
    private JPanel levelListPanel;

    private JLabel multiplayerTimerLabel = new JLabel("00:00");
    private Timer multiplayerTimer;
    private long multiplayerStartTime;

    private final Map<String, TokenRenderer> tokenRenderers = new HashMap<>();

    private DisplayManager currentScreen;

    private BoardRendererPanel boardRenderer;
    private final DrawableManager drawableManager = new DrawableManager();

    private BufferedImage backgroundImage;

    private final Map<String, BufferedImage> tokenImages = new HashMap<>();
    private final Map<String, TokenRenderer> staticRenderers = new HashMap<>();
    private final Map<String, ITurnableTokenRenderer> turnableRenderers = new HashMap<>();

    private final GameRenderer gameRenderer = new GameRenderer();
    private GameControlPanel controlPanel;


    public GamePanel(TokenImageLoader loader, GameControlPanel controlPanel) {
        tokenImages.putAll(loader.loadTokenImages());
        backgroundImage = loader.loadBackgroundImage();

        setPreferredSize(new Dimension(800, 600));
        setLayout(null); // Absolute positioning

        this.controlPanel = controlPanel;
        this.boardRenderer = controlPanel.boardRenderer;

        add(controlPanel);
        add(controlPanel.boardRenderer);
        controlPanel.boardRenderer.setBounds(0, 0, 800, 600);
        controlPanel.boardRenderer.setVisible(false); // Hide by default

        this.singlePlayer = controlPanel.singlePlayer;
        this.multiplayer = controlPanel.multiplayer;
        this.quitGame = controlPanel.quitGame;
        this.backButton = controlPanel.backButton;
        this.levelScrollPane = controlPanel.levelScrollPane;
        this.levelListPanel = controlPanel.levelListPanel;

        createFireLaserButton();

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
            return null;
        }
        return new Position(col, row);
    }

    public void setTilesToRender(List<RenderableTile> tiles) {
        gameRenderer.setTilesToRender(tiles);
    }

    public void addDrawable(Drawable drawable) {
        drawableManager.add(drawable);
    }

    public void setTokenImages(Map<String, BufferedImage> images) {
        gameRenderer.setTokenImages(images);
    }

    public void switchToScreen(DisplayManager screen) {
        this.currentScreen = screen;
        screen.show();
        repaint();
    }

    public void clearDrawables() {
        drawableManager.clear();
    }

    public Map<String, BufferedImage> getTokenImages() {
        return tokenImages;
    }

    public void setStaticRenderers(Map<String, TokenRenderer> renderers) {
        gameRenderer.setStaticRenderers(renderers);
        boardRenderer.setStaticRenderers(renderers);
    }

    public void setTurnableRenderers(Map<String, ITurnableTokenRenderer> renderers) {
        gameRenderer.setTurnableRenderers(renderers);
        boardRenderer.setTurnableRenderers(renderers);
    }

    public void setLaserPath(List<PositionTurn> path) {
        gameRenderer.setLaserPath(path);
    }

    public DrawableManager getDrawableManager() {
        return drawableManager;
    }

    public GameControlPanel getControlPanel() {
        return controlPanel;
    }

    public boolean hasFireLaserButton() {
        return fireLaserButton != null;
    }

    public void setFireLaserListener(ActionListener listener) {
        if (fireLaserButton != null) {
            fireLaserButton.addActionListener(listener);
        }
    }

    public void clearLaserPath() {
        gameRenderer.setLaserPath(null);
        boardRenderer.setLaserPath(null);
        repaint();
    }

    public void createFireLaserButton() {
        if (fireLaserButton == null) {
            fireLaserButton = new JButton("Fire Laser");
            fireLaserButton.setBounds(20, 20, 120, 30);
            fireLaserButton.setVisible(false);
            add(fireLaserButton);
        }
    }

    public void createControlButtons() {
        if (restartButton == null) {
            restartButton = new JButton("Restart");
            restartButton.setBounds(160, 20, 100, 30);
            add(restartButton);
        }
        if (exitButton == null) {
            exitButton = new JButton("Exit");
            exitButton.setBounds(270, 20, 100, 30);
            add(exitButton);
        }
        if (saveAndExitButton == null) {
            saveAndExitButton = new JButton("Save & Exit");
            saveAndExitButton.setBounds(380, 20, 130, 30);
            add(saveAndExitButton);
        }

        revalidate();
        repaint();
    }

    public void startMultiplayerTimer() {
        multiplayerStartTime = System.currentTimeMillis();
        multiplayerTimerLabel.setBounds(600, 40, 160, 30);
        multiplayerTimerLabel.setForeground(Color.WHITE);
        multiplayerTimerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        multiplayerTimerLabel.setVisible(true);
        add(multiplayerTimerLabel);

        multiplayerTimer = new Timer(1000, e -> {
            long elapsed = System.currentTimeMillis() - multiplayerStartTime;
            long seconds = (elapsed / 1000) % 60;
            long minutes = (elapsed / 1000) / 60;
            multiplayerTimerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
        });
        multiplayerTimer.start();
    }

    public void stopMultiplayerTimer() {
        if (multiplayerTimer != null) {
            multiplayerTimer.stop();
            multiplayerTimer = null;
        }
        multiplayerTimerLabel.setVisible(false);
    }


    public void resetBoardUI() {
        if (boardRenderer != null) {
            boardRenderer.setVisible(false);
            boardRenderer.setTilesToRender(List.of());
            boardRenderer.setLaserPath(null);
        }

        stopMultiplayerTimer();
        setTilesToRender(List.of());
        setLaserPath(null);
        clearGameplayButtons();
        repaint();
    }

    public void clearGameplayButtons() {
        clearButton(fireLaserButton);
        clearButton(restartButton);
        clearButton(exitButton);
        clearButton(saveAndExitButton);
    }

    private void clearButton(JButton button) {
        if (button != null) {
            for (ActionListener l : button.getActionListeners())
                button.removeActionListener(l);
            button.setVisible(false);
        }
    }

    public void showBoardUI() {
        if (boardRenderer != null) {
            boardRenderer.setVisible(true);
            boardRenderer.repaint();
        }

        if (fireLaserButton != null) fireLaserButton.setVisible(true);
        if (restartButton != null) restartButton.setVisible(true);
        if (exitButton != null) exitButton.setVisible(true);
        if (saveAndExitButton != null) saveAndExitButton.setVisible(true);
    }

    public void clearMouseListeners() {
        for (var l : getMouseListeners()) removeMouseListener(l);
        for (var l : getMouseMotionListeners()) removeMouseMotionListener(l);
    }

    // Getters
    public JButton getRestartButton() {
        return restartButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getSaveAndExitButton() {
        return saveAndExitButton;
    }

    public JButton getFireLaserButton() {
        return fireLaserButton;
    }

    private TileContainer inventory;

    public void setInventory(TileContainer inventory) {
        this.inventory = inventory;
    }

    public void setInventoryTilesToRender(List<RenderableTile> tiles) {
        boardRenderer.setInventoryTilesToRender(tiles);
    }

    public Position screenToInventory(int pixelX, int pixelY) {
        int tileSize = 60;
        int padding = 10;
        int offsetX = 100;
        int offsetY = 520;

        int x = pixelX - offsetX;
        int y = pixelY - offsetY;

        if (y < 0 || y > tileSize) return null;

        int col = x / (tileSize + padding);
        if (col < 0 || col >= 5) return null; // assuming max 5 inventory slots

        return new Position(col, 0);
    }
    public boolean isInventoryArea(int pixelX, int pixelY) {
        return pixelY >= 520 && pixelY <= 580; // matches inventoryStartY + tileSize
    }
    public boolean isBoardArea(int x, int y) {
        int tileSize = ViewConfig.TILE_SIZE;
        int boardX = ViewConfig.BOARD_OFFSET_X;
        int boardY = ViewConfig.BOARD_OFFSET_Y;
        int boardWidth = 5 * tileSize;
        int boardHeight = 5 * tileSize;

        return x >= boardX && x < boardX + boardWidth &&
                y >= boardY && y < boardY + boardHeight;
    }

    public void showLevelComplete() {
        JOptionPane.showMessageDialog(
                this,
                "Congratulations! You’ve completed the level!",
                "Level Complete",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}
