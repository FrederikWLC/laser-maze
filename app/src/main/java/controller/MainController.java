package controller;

import view.GamePanel;

import javax.swing.*;

public class MainController {

    public void startGame() {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Laser Maze");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

            GamePanel gamePanel = new GamePanel();

            gamePanel.setQuitGameAction(e -> System.exit(0));

            InputHandler inputHandler = new InputHandler();
            gamePanel.addMouseListener(inputHandler);
            gamePanel.addMouseMotionListener(inputHandler);

            window.add(gamePanel);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}
