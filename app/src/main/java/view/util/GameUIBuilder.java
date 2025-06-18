package view.util;

import javax.swing.*;
import java.awt.*;

public class GameUIBuilder {

    public void setupTitleButtons(JPanel panel, JButton singlePlayer, JButton multiplayer, JButton quitGame) {
        int w = 160, h = 40, x = (800 - w) / 2;

        singlePlayer.setBounds(x, 280, w, h);
        multiplayer.setBounds(x, 340, w, h);
        quitGame.setBounds(640, 560, w, h);

        panel.add(singlePlayer);
        panel.add(multiplayer);
        panel.add(quitGame);
    }

    public void setupLevelSelectScreen(JPanel panel, JPanel levelListPanel, JScrollPane levelScrollPane, JButton backButton) {
        levelListPanel.setLayout(new GridLayout(0, 3, 10, 10));

        for (int i = 1; i <= 60; i++) {
            JButton levelButton = new JButton("Level " + i);
            levelListPanel.add(levelButton);
        }

        levelScrollPane.setBounds(150, 50, 500, 400);
        levelScrollPane.setVisible(false);
        panel.add(levelScrollPane);

        backButton.setBounds(320, 470, 160, 40);
        backButton.setVisible(false);
        panel.add(backButton);
    }
}
