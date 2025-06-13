package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private JButton quitGame;
    private JButton singlePlayer;
    private JButton multiplayer;
    private JButton backButton;

    private JScrollPane levelScrollPane;
    private JPanel levelListPanel;

    private final List<Drawable> drawables = new ArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null); // Absolute positioning

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
            JButton levelButton = new JButton("Level " + i);
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        for (Drawable d : drawables) {
            d.draw(g2d);
        }

        g2d.dispose();
    }

    public void setQuitGameAction(ActionListener listener) {
        quitGame.addActionListener(listener);
    }
}
