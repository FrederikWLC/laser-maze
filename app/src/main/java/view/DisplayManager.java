package view;

import java.awt.Graphics2D;

public interface DisplayManager {
    void show(); // called when switching to this screen
    void draw(Graphics2D g); // called by GamePanel's paintComponent
}
