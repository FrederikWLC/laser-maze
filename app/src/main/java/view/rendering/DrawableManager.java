package view.rendering;

import view.Drawable;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class DrawableManager {
    private final List<Drawable> drawables = new ArrayList<>();

    public void add(Drawable drawable) {
        drawables.add(drawable);
    }

    public void clear() {
        drawables.clear();
    }

    public void drawAll(Graphics2D g) {
        for (Drawable d : drawables) {
            d.draw(g);
        }
    }

    public List<Drawable> getAll() {
        return drawables;
    }
}
