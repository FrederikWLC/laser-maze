package controller;

import model.domain.board.Position;
import model.domain.token.base.Token;
import model.domain.token.base.ITurnableToken;
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
        Position clicked = gamePanel.screenToBoard(e.getX(), e.getY());
        if (clicked == null) return;

        soundManager.play(SoundManager.Sound.CLICK, false);

        Token token = gameController.getTokenAt(clicked);
        if (token instanceof ITurnableToken turnable) {
            gameController.rotateTokenClockwise(turnable);
        }

        List<RenderableTile> updatedTiles = tileFactory.convertBoardToRenderableTiles(
                gameController.getLevel().getBoard()
        );

        gamePanel.setTilesToRender(updatedTiles);
        gamePanel.repaint();
        gamePanel.getControlPanel().boardRenderer.setTilesToRender(updatedTiles);
        gamePanel.getControlPanel().boardRenderer.repaint();
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
