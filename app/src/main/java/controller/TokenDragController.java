package controller;

import model.domain.board.*;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.Token;

public class TokenDragController {
    private Token draggedToken;
    private Position origin;
    private TileContainer source;

    public boolean startDrag(TileContainer container, Position from) {
        if (!container.isWithinBounds(from.getX(), from.getY())) return false;
        Token token = container.getTile(from.getX(), from.getY()).getToken();
        if (token == null || !token.isMovable()) return false;

        draggedToken = token;
        origin = from;
        source = container;
        return true;
    }

    public boolean drop(TileContainer target, Position to) {
        if (draggedToken == null || to == null || !target.isWithinBounds(to.getX(), to.getY())) return false;

        // Allow dropping into the same position
        if (target == source && to.equals(origin)) {
            cancel();
            return false;
        }

        if (!target.getTile(to.getX(), to.getY()).isEmpty()) {
            System.out.println("Drop target occupied.");
            cancel(); // or allow swap if desired
            return false;
        }

        // Remove from source
        source.getTile(origin.getX(), origin.getY()).setToken(null);

        // Place in target
        target.getTile(to.getX(), to.getY()).setToken(draggedToken);
        return true;
    }

    public void dropOnInventory(Inventory target, Position to) {
        if (drop(target,to)) {
            ((ITurnableToken) draggedToken).setDirection(null);
            draggedToken.setPosition(null);
        }
        else {
            System.out.println("Failed to drop token on inventory.");
        }
        cancel();
    }

    public void dropOnBoard(Board target, Position to) {
        if (drop(target,to)) {
            draggedToken.setPosition(to);
            if (draggedToken instanceof ITurnableToken turnable && turnable.getDirection() == null) {
                turnable.setDirection(Direction.UP); // Safe default direction
            }
        }
        else {
            System.out.println("Failed to drop token on board.");
        }
        cancel();
    }

    public Token getDraggedToken() {
        return draggedToken;
    }


    public void cancel() {
        draggedToken = null;
        origin = null;
        source = null;
    }

}