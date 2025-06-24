package view;

import java.awt.*;
import java.awt.font.GlyphVector;

public class TitleRenderer implements Drawable {

    private final String text;
    private final Font font;
    private final int y;

    public TitleRenderer(String text, int y) {
        this.text = text;
        this.font = new Font("Serif", Font.BOLD, 48);
        this.y = y;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = g.getFontMetrics(font);
        int x = (800 - fm.stringWidth(text)) / 2;

        GlyphVector gv = font.createGlyphVector(g.getFontRenderContext(), text);
        Shape shape = gv.getOutline(x, y);

        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(3f));
        g.draw(shape);

        g.setColor(Color.BLACK);
        g.fill(shape);
    }
}
