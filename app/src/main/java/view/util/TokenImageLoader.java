package view.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TokenImageLoader {

    public Map<String, BufferedImage> loadTokenImages() {
        Map<String, BufferedImage> images = new HashMap<>();

        String[] filenames = {
                "RedLaser-GENERATOR_ON_NORTH.png",
                "RedLaser-GENERATOR_ON_EAST.png",
                "RedLaser-GENERATOR_ON_SOUTH.png",
                "RedLaser-GENERATOR_ON_WEST.png",
                "RedLaser-GENERATOR_ON_default.png",

                "PurpleTarget-TARGET_ON_NORTH.png",
                "PurpleTarget-TARGET_ON_EAST.png",
                "PurpleTarget-TARGET_ON_SOUTH.png",
                "PurpleTarget-TARGET_ON_WEST.png",
                "PurpleTarget-TARGET_ON_default.png",

                "GreenMirror-BACKSLASH_MIRROR.png",
                "GreenMirror-SLASH_MIRROR.png",
                "GreenMirror-default_MIRROR.png",

                "BlueMirror-BACKSLASH_MIRROR.png",
                "BlueMirror-SLASH_MIRROR.png",
                "BlueMirror-default_MIRROR.png",

                "YellowBridge-HORIZONTAL_BRIDGE.png",
                "YellowBridge-VERTICAL_BRIDGE.png",
                "YellowBridge-default_BRIDGE.png",

                "WhiteObstacle-NONE-Dark.png",
                "EmptyCell.png",
                "LASER-BEAM-HORIZONTAL.png",
                "LASER-BEAM-VERTICAL.png"
        };

        for (String name : filenames) {
            try {
                BufferedImage img = ImageIO.read(getClass().getResource("/textures/tokens/" + name));
                images.put(name, img);
            } catch (Exception e) {
                System.err.println("Failed to load: " + name);
            }
        }
        for (String key : images.keySet()) {
        }


        return images;
    }

    public BufferedImage loadBackgroundImage() {
        try {
            return ImageIO.read(getClass().getResource("/texture/background/thelasermaze.jpeg"));
        } catch (Exception e) {
            System.err.println("Could not load background image. Using fallback.");
            return null;
        }
    }
}
