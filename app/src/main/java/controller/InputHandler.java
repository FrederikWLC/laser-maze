package controller;
import model.*;
import view.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements MouseListener, MouseMotionListener {
    private final GameController gameController;
    private final GamePanel gamePanel;

    public InputHandler(GameController gameController, GamePanel gamePanel) {
        this.gameController = gameController;
        this.gamePanel = gamePanel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        Position clicked = gamePanel.screenToBoard(e.getX(), e.getY());;

        Token token = gameController.getTokenAt(clicked);
        if (token == null) {
            System.out.println("No token at: " + clicked);
            return;
        }

        Direction current = (token instanceof ITurnableToken turnable) ? turnable.getDirection() : null;
        Direction next = current.rotateClockwise();

        boolean success = gameController.rotateToken(clicked, next);

        if (success) {
            System.out.println("Rotated token to " + next);
        } else {
            System.out.println("Rotation failed.");
        }
    }


    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
