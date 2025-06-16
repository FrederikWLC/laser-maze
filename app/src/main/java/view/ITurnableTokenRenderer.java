package view;

import model.Direction;
import model.ITurnableToken;
import java.awt.Graphics2D;

public interface ITurnableTokenRenderer {
    void render(Graphics2D g, ITurnableToken token, int x, int y, int tileSize);

    String getDirString(Direction dir);
}
