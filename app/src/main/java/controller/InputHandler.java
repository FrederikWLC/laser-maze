package controller;

import model.domain.board.Position;
import model.domain.board.TileContainer;
import model.domain.token.base.Token;
import view.GamePanel;
import view.RenderableTile;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import model.domain.token.base.ITurnableToken;


public class InputHandler implements MouseListener, MouseMotionListener {
    private final GameController gameController;
    private final GamePanel gamePanel;
    private final RenderableTileFactory tileFactory;
    private final SoundManager soundManager;
    private final TileContainer inventory;
    private final TokenDragController dragController;

    public InputHandler(GameController gameController, GamePanel gamePanel, RenderableTileFactory tileFactory, SoundManager soundManager, TileContainer inventory, TokenDragController dragController) {
        this.gameController = gameController;
        this.gamePanel = gamePanel;
        this.tileFactory = tileFactory;
        this.soundManager = soundManager;
        this.inventory = inventory;
        this.dragController = dragController;
    }

    private void refreshBoardAndInventoryView() {
        List<RenderableTile> boardTiles = tileFactory.convertBoardToRenderableTiles(gameController.getLevel().getBoard());
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

    /*
    private void refreshBoardAndInventoryView() {
        List<RenderableTile> updatedTiles = tileFactory.convertBoardToRenderableTiles(
                gameController.getLevel().getBoard()
        );
        List<RenderableTile> boardTiles = tileFactory.convertBoardToRenderableTiles(gameController.getLevel().getBoard());


        gamePanel.setTilesToRender(updatedTiles);
        gamePanel.repaint();

        gamePanel.getControlPanel().boardRenderer.setTilesToRender(updatedTiles);
        gamePanel.getControlPanel().boardRenderer.repaint();
    }
*/
    @Override
    public void mouseClicked(MouseEvent e) {
        soundManager.play(SoundManager.Sound.CLICK, false);
        Position clicked = gamePanel.screenToBoard(e.getX(), e.getY());
        if (clicked == null) return;

        Token token = gameController.getTokenAt(clicked);
        if (token == null) {
            System.out.println("No token at: " + clicked);
            return;
        }

        if (token instanceof ITurnableToken turnable && token.isTurnable()) {
            gameController.rotateTokenClockwise(turnable);
        }

        refreshBoardAndInventoryView();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Position boardPos = gamePanel.screenToBoard(e.getX(), e.getY());
        Position inventoryPos = gamePanel.screenToInventory(e.getX(), e.getY());

        if (gamePanel.isBoardArea(e.getX(), e.getY())) {
            dragController.startDrag(gameController.getLevel().getBoard(), boardPos);
        } else if (gamePanel.isInventoryArea(e.getX(), e.getY())) {
            dragController.startDrag(inventory, inventoryPos);
        }

        refreshBoardAndInventoryView();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Position boardPos = gamePanel.screenToBoard(e.getX(), e.getY());
        Position inventoryPos = gamePanel.screenToInventory(e.getX(), e.getY());

        if (gamePanel.isBoardArea(e.getX(), e.getY())) {
            dragController.drop(gameController.getLevel().getBoard(), boardPos);
        } else if (gamePanel.isInventoryArea(e.getX(), e.getY())) {
            dragController.drop(inventory, inventoryPos);
        }

        refreshBoardAndInventoryView();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Optional: implement if you want drag movement for tokens
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
