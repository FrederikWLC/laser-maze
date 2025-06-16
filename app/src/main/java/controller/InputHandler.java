package controller;
import model.domain.board.Position;
import model.domain.token.Token;
import view.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;


public class InputHandler implements MouseListener, MouseMotionListener {
    private final GameController gameController;
    private final GamePanel gamePanel;
    private final MainController mainController;

    public InputHandler(GameController gameController, GamePanel gamePanel, MainController mainController) {
        this.gameController = gameController;
        this.gamePanel = gamePanel;
        this.mainController = mainController;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Position clicked = gamePanel.screenToBoard(e.getX(), e.getY());;

        Token token = gameController.getTokenAt(clicked);
        if (token == null) {
            System.out.println("No token at: " + clicked);
            return;
        }

        gameController.rotateTokenClockwise(token);
        List<RenderableTile> updatedTiles = mainController.convertBoardToRenderableTiles(gameController.getLevel().getBoard());
        gamePanel.setTilesToRender(updatedTiles);
        gamePanel.repaint();
    }


    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
