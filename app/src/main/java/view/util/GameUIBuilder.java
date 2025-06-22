package view.util;

import javax.swing.*;
import java.awt.*;
import view.GamePanel;
import model.persistence.storage.LevelIOHandler;

import model.domain.level.Level;
import model.persistence.storage.DefaultLevelLoader;
import model.persistence.storage.SavedLevelLoader;
import model.persistence.storage.LevelSaver;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;




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

    public void setupLevelSelectScreen(
            JPanel panel,
            JPanel levelListPanel,
            JScrollPane levelScrollPane,
            JButton backButton
    ) {
        levelListPanel.setLayout(new GridLayout(0, 3, 10, 10));
        levelListPanel.removeAll();

        LevelIOHandler levelIOHandler = new LevelIOHandler(
                new DefaultLevelLoader(),
                new SavedLevelLoader(),
                new LevelSaver()
        );

        List<Level> levels = new ArrayList<>(levelIOHandler.loadAll());
        levels.sort(Comparator.comparingInt(Level::getId));

        for (Level level : levels) {
            JButton button = new JButton("Level " + level.getId());
            button.addActionListener(e -> System.out.println("Selected Level " + level.getId()));
            levelListPanel.add(button);
        }

        levelScrollPane.setBounds(150, 50, 500, 400);
        levelScrollPane.setVisible(true);
        panel.add(levelScrollPane);

        backButton.setBounds(320, 470, 160, 40);
        backButton.setVisible(true);
        panel.add(backButton);
    }


}
