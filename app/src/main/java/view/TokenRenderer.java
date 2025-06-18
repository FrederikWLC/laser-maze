package view;

import model.domain.token.base.Token;
import java.awt.Graphics2D;

public interface TokenRenderer {
    void render(Graphics2D g, Token token, int x, int y, int tileSize);
}
