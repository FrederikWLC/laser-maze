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

    public void loadLevel(int levelNumber) {
        Board board = new Board(5, 5);

        // Proper tokens with positions and directions
        board.getTile(1, 0).setToken(
                TokenBuilder.of(LaserToken::new)
                        .withPosition(1, 0)
                        .withDirection(Direction.LEFT)
                        .withMutability(true, true) // assuming LaserToken is turnable and movable
                        .build()
        );

        board.getTile(1, 3).setToken(
                TokenBuilder.of(DoubleMirrorToken::new)
                        .withPosition(1, 3)
                        .withDirection(Direction.DOWN)
                        .withMutability(true, true) // adjust as needed
                        .build()
        );

        board.getTile(4, 3).setToken(
                TokenBuilder.of(TargetMirrorToken::new)
                        .withPosition(4, 3)
                        .withDirection(Direction.UP)
                        .withMutability(true, true)     // if needed
                        .withRequiredTarget()           // because it's a TargetMirrorToken
                        .build()
        );

        this.gameController = new GameController(board);
        gamePanel.setBoard(board);
        gamePanel.showBoard(levelNumber);
        setupFireLaserAction();

        InputHandler inputHandler = new InputHandler(gameController,gamePanel);
        gamePanel.addMouseListener(inputHandler);
        gamePanel.addMouseMotionListener(inputHandler);
    }



}
