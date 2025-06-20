package controller;

import model.domain.board.Inventory;
import model.domain.board.Position;
import model.domain.board.TileContainer;
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

    public void drop(TileContainer target, Position to) {
        if (draggedToken == null || to == null || !target.isWithinBounds(to.getX(), to.getY())) return;

        // Allow dropping into the same position
        if (target == source && to.equals(origin)) {
            cancel();
            return;
        }

        if (!target.getTile(to.getX(), to.getY()).isEmpty()) {
            System.out.println("Drop target occupied.");
            cancel(); // or allow swap if desired
            return;
        }

        // Remove from source
        source.getTile(origin.getX(), origin.getY()).setToken(null);

        // Place in target
        target.getTile(to.getX(), to.getY()).setToken(draggedToken);
        draggedToken.setPosition(to);

        cancel();
    }


    public void cancel() {
        draggedToken = null;
        origin = null;
        source = null;
    }
}