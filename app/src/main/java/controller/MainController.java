package controller;

import view.GamePanel;

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

    public void startGame() {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Laser Maze");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

            gamePanel = new GamePanel();

            gamePanel.setQuitGameAction(e -> System.exit(0));
            gamePanel.setLevelSelectAction(this::loadLevel);

            InputHandler inputHandler = new InputHandler();
            gamePanel.addMouseListener(inputHandler);
            gamePanel.addMouseMotionListener(inputHandler);

            window.add(gamePanel);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
    public void loadLevel(int levelNumber) {
        Board board = new Board(5, 5); // or dynamic size per level

        // Proper tokens with positions and directions
        board.getTile(0, 0).setToken(new LaserToken(new Position(0, 0), Direction.LEFT));
        board.getTile(1, 3).setToken(new DoubleMirrorToken(new Position(1, 3), Direction.DOWN));
        board.getTile(4, 4).setToken(new TargetMirrorToken(new Position(4, 4), Direction.UP));

        gamePanel.setBoard(board);
        gamePanel.showBoard(levelNumber);
    }



}
