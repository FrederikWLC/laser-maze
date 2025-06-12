package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {

    private JButton quitGame; // Declare it as a field

    private final List<Drawable> drawables = new ArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null); // For absolute positioning

        BufferedImage bgImage = loadBackgroundImage();
        drawables.add(new BackgroundRenderer(bgImage));
        drawables.add(new TitleRenderer("LASER MAZE", 160));

        setupButtons();
    }

    private BufferedImage loadBackgroundImage() {
        try {
            return ImageIO.read(getClass().getResource("/background/thelasermaze.jpeg"));
        } catch (Exception e) {
            System.err.println("Could not load background image. Using fallback.");
            return null;
        }
    }

    private void setupButtons() {
        JButton singlePlayer = new JButton("Single Player");
        JButton multiplayer = new JButton("Multiplayer");
        quitGame = new JButton("Quit Game");

        int w = 160, h = 40, x = (800 - w) / 2;
        singlePlayer.setBounds(x, 280, w, h);
        multiplayer.setBounds(x, 340, w, h);
        quitGame.setBounds(640, 560, w, h);

        add(singlePlayer);
        add(multiplayer);
        add(quitGame);
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
