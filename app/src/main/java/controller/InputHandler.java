package controller;
import model.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements MouseListener, MouseMotionListener {
    private final GameController gameController;

    public InputHandler(GameController gameController) {
        this.gameController = gameController;
    }

    private Position convertToGridPosition(int pixelX, int pixelY) {
        int tileSize = 64; // Use the same size used in GamePanel
        return new Position(pixelX / tileSize, pixelY / tileSize);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Position clicked = convertToGridPosition(e.getX(), e.getY());

        Token token = gameController.getTokenAt(clicked); // you'll need to add this helper method
        if (token == null) {
            System.out.println("No token at: " + clicked);
            return;
        }

        Direction current = token.getDirection();
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
