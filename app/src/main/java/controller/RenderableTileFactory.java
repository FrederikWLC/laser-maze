package controller;

import model.domain.board.Board;
import model.domain.board.Direction;
import model.domain.board.Tile;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.Token;
import view.RenderableTile;

import java.util.ArrayList;
import java.util.List;

public class RenderableTileFactory {
    public List<RenderableTile> convertBoardToRenderableTiles(Board board) {
        List<RenderableTile> renderables = new ArrayList<>();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Tile tile = board.getTile(x, y);
                if (!tile.isEmpty()) {
                    Token token = tile.getToken();
                    String type = token.getClass().getSimpleName();
                    Direction direction = token instanceof ITurnableToken turnable ? turnable.getDirection() : null;
                    renderables.add(new RenderableTile(x, y, type, direction));
                }
            }
        }
        return renderables;
    }
}
