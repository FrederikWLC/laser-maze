package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class GameControlPanel extends JPanel {
    public final JButton singlePlayer = new JButton("Single Player");
    public final JButton multiplayer = new JButton("Multiplayer");
    public final JButton quitGame = new JButton("Quit Game");
    public final JButton backButton = new JButton("Back");

    public final JPanel levelListPanel = new JPanel();
    public final JScrollPane levelScrollPane = new JScrollPane(levelListPanel);
    public final BoardRendererPanel boardRenderer = new BoardRendererPanel();
    private JButton fireLaserButton;



    public GameControlPanel() {
        setLayout(null);
        levelListPanel.setLayout(new GridLayout(0, 3, 10, 10));
        for (int i = 1; i <= 60; i++) {
            levelListPanel.add(new JButton("Level " + i));
        }
    }
}
