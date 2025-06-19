package controller;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {
    public enum Sound {
        BACKGROUND("sound/background.wav"),
        LASER("sound/laser.wav"),
        CLICK("sound/click.wav");

        private final String path;

        Sound(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    private Clip backgroundClip;

    public void play(Sound sound, boolean loop) {
        try {
            URL soundURL = getClass().getClassLoader().getResource(sound.getPath());
            if (soundURL == null) {
                System.err.println("Sound file not found: " + sound.getPath());
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundClip = clip;
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.err.println("Failed to play sound: " + ex.getMessage());
        }
    }

    public void stopBackground() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }
}
