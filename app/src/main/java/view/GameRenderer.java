package view;

import model.domain.board.PositionTurn;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class GameRenderer {
    private final BoardRendererPanel boardRenderer = new BoardRendererPanel();

    public GameRenderer() {
        boardRenderer.setOpaque(false);
        boardRenderer.setBounds(0, 0, 800, 600);
    }

    public BoardRendererPanel getBoardRenderer() {
        return boardRenderer;
    }

    public void setTilesToRender(List<RenderableTile> tiles) {
        boardRenderer.setTilesToRender(tiles);
    }

    public void setLaserPath(List<PositionTurn> path) {
        boardRenderer.setLaserPath(path);
    }

    public void setTokenImages(Map<String, BufferedImage> images) {
        boardRenderer.setTokenImages(images);
    }

    public void setStaticRenderers(Map<String, TokenRenderer> renderers) {
        boardRenderer.setStaticRenderers(renderers);
    }

    public void setTurnableRenderers(Map<String, ITurnableTokenRenderer> renderers) {
        boardRenderer.setTurnableRenderers(renderers);
    }
}
