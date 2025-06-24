package controller;

import model.domain.board.Inventory;
import model.domain.board.Position;
import model.domain.token.base.Token;
import model.domain.token.base.ITurnableToken;
import view.GamePanel;
import view.RenderableTile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;


public class InputHandler implements MouseListener, MouseMotionListener {
    private final LevelController levelController;
    private final GamePanel gamePanel;
    private final RenderableTileFactory tileFactory;
    private final SoundManager soundManager;
    private final Inventory inventory;
    private final TokenDragController dragController;
    private Point mousePosition = null;

    public InputHandler(LevelController levelController, GamePanel gamePanel, RenderableTileFactory tileFactory, SoundManager soundManager, Inventory inventory, TokenDragController dragController) {
        this.levelController = levelController;
        this.gamePanel = gamePanel;
        this.tileFactory = tileFactory;
        this.soundManager = soundManager;
        this.inventory = inventory;
        this.dragController = dragController;
    }

    private void refreshBoardAndInventoryView() {
        List<RenderableTile> boardTiles = tileFactory.convertBoardToRenderableTiles(levelController.getLevel().getBoard());
        List<RenderableTile> inventoryTiles = tileFactory.convertBoardToRenderableTiles(inventory);

        // Update both game panel and board renderer with board tiles
        gamePanel.setTilesToRender(boardTiles);
        gamePanel.getControlPanel().boardRenderer.setTilesToRender(boardTiles);

        // Update inventory tiles
        gamePanel.setInventoryTilesToRender(inventoryTiles);
        gamePanel.getControlPanel().boardRenderer.setInventoryTilesToRender(inventoryTiles);

        gamePanel.repaint();
        gamePanel.getControlPanel().boardRenderer.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        soundManager.play(SoundManager.Sound.CLICK, false);
        Position clicked = gamePanel.screenToBoard(e.getX(), e.getY());
        if (clicked == null) return;

        soundManager.play(SoundManager.Sound.CLICK, false);

        Token token = levelController.getTokenAt(clicked);
        if (token == null) {
            System.out.println("No token at: " + clicked);
            return;
        }

        if (token instanceof ITurnableToken turnable && token.isTurnable()) {
            levelController.rotateTokenClockwise(turnable);
        }

        refreshBoardAndInventoryView();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Position boardPos = gamePanel.screenToBoard(e.getX(), e.getY());
        Position inventoryPos = gamePanel.screenToInventory(e.getX(), e.getY());

        if (gamePanel.isBoardArea(e.getX(), e.getY())) {
            dragController.startDrag(levelController.getLevel().getBoard(), boardPos);
        } else if (gamePanel.isInventoryArea(e.getX(), e.getY())) {
            dragController.startDrag(inventory, inventoryPos);
        }

        // Provide dragged token to the renderer
        Token dragged = dragController.getDraggedToken();
        gamePanel.getControlPanel().boardRenderer.setCurrentlyDraggedToken(dragged);


        refreshBoardAndInventoryView();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Position boardPos = gamePanel.screenToBoard(e.getX(), e.getY());
        Position inventoryPos = gamePanel.screenToInventory(e.getX(), e.getY());

        // Try dropping in either valid area
        if (gamePanel.isBoardArea(e.getX(), e.getY())) {
            dragController.dropOnBoard(levelController.getLevel().getBoard(), boardPos);
        } else if (gamePanel.isInventoryArea(e.getX(), e.getY())) {
            dragController.dropOnInventory(inventory, inventoryPos);
        }

        // Clear ghost image always
        gamePanel.getControlPanel().boardRenderer.setDragMousePosition(null);
        gamePanel.getControlPanel().boardRenderer.setCurrentlyDraggedToken(null);
        gamePanel.repaint();

        refreshBoardAndInventoryView();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = e.getPoint(); // update cursor location
        gamePanel.getControlPanel().boardRenderer.setDragMousePosition(mousePosition);
        gamePanel.repaint();
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
    public void mouseMoved(MouseEvent e) {
        // Optional: implement hover effects if needed
    }
}
