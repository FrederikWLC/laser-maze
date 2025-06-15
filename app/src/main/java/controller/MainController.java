package controller;

import view.GamePanel;
import model.*;
import java.util.List;

import javax.swing.*;

import model.Board;

import model.Token;
import view.GamePanel;
import model.Board;
import model.Tile;
import model.LaserToken;
import model.DoubleMirrorToken;
import model.TargetMirrorToken;
import model.Position;
import model.Direction;


public class MainController {
    private GamePanel gamePanel;
    private Board board;
    private GameController gameController;


    public void startGame() {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Laser Maze");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

            gamePanel = new GamePanel();

            gamePanel.setQuitGameAction(e -> System.exit(0));
            gamePanel.setLevelSelectAction(LevelLoader::load);

            window.add(gamePanel);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }

    private void setupFireLaserAction() {
        gamePanel.setFireLaserAction(e -> {
            List<PositionDirection> laserPath = gameController.fireLaser();

            System.out.println("Laser path:");
            for (PositionDirection pd : laserPath) {
                System.out.println(" -> " + pd);
            }

            gamePanel.setLaserPath(laserPath);
        });
    }

}
