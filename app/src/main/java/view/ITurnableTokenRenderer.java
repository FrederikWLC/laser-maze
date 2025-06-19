package view;

import model.domain.board.Direction;
import model.domain.token.base.ITurnableToken;
import java.awt.Graphics2D;

public interface ITurnableTokenRenderer {
    void render(Graphics2D g, ITurnableToken token, int x, int y, int tileSize);

    String getDirString(Direction dir);
}
