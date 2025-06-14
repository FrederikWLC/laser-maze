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
            gamePanel.setLevelSelectAction(this::loadLevel);

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

    public void loadLevel(int levelNumber) {
        Board board = new Board(5, 5);

        // Proper tokens with positions and directions
        board.getTile(1, 0).setToken(new LaserToken(new Position(1, 0), Direction.LEFT));
        board.getTile(1, 3).setToken(new DoubleMirrorToken(new Position(1, 3), Direction.DOWN));
        board.getTile(4, 3).setToken(new TargetMirrorToken(new Position(4, 3), Direction.UP));

        this.gameController = new GameController(board);
        gamePanel.setBoard(board);
        gamePanel.showBoard(levelNumber);
        setupFireLaserAction();

        InputHandler inputHandler = new InputHandler(gameController);
        gamePanel.addMouseListener(inputHandler);
        gamePanel.addMouseMotionListener(inputHandler);
    }



}
