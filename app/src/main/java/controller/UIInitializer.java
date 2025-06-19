package controller;

import view.GamePanel;

import javax.swing.*;

public class UIInitializer {
    public static void setupWindow(JFrame window, GamePanel gamePanel) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
