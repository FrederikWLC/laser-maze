package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements MouseListener, MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
        // TODO: determine what tile was clicked, and pass to model
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Optional: drag start, click-hold actions
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Optional: drag end
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        // Optional: for dragging pieces
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Optional: hover highlighting, etc.
    }
}
