package controller;

import model.domain.board.Position;
import model.domain.token.base.Token;
import view.GamePanel;
import view.RenderableTile;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class InputHandler implements MouseListener, MouseMotionListener {
    private final GameController gameController;
    private final GamePanel gamePanel;
    private final RenderableTileFactory tileFactory;
    private final SoundManager soundManager;

    public InputHandler(GameController gameController, GamePanel gamePanel, RenderableTileFactory tileFactory, SoundManager soundManager) {
        this.gameController = gameController;
        this.gamePanel = gamePanel;
        this.tileFactory = tileFactory;
        this.soundManager = soundManager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        soundManager.play(SoundManager.Sound.CLICK, false);
        Position clicked = gamePanel.screenToBoard(e.getX(), e.getY());

        Token token = gameController.getTokenAt(clicked);
        if (token == null) {
            System.out.println("No token at: " + clicked);
            return;
        }

        gameController.rotateTokenClockwise(token);
        List<RenderableTile> updatedTiles = tileFactory.convertBoardToRenderableTiles(
                gameController.getLevel().getBoard()
        );

        gamePanel.setTilesToRender(updatedTiles);
        gamePanel.repaint();

        gamePanel.getControlPanel().boardRenderer.setTilesToRender(updatedTiles);
        gamePanel.getControlPanel().boardRenderer.repaint();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Optional: implement if you need drag/selection behavior
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Optional
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Optional
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Optional
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Optional: implement if you want drag movement for tokens
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Optional: implement hover effects if needed
    }
}
