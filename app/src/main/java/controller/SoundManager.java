package controller;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

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
    private final Map<Sound, byte[]> soundEffectData = new EnumMap<>(Sound.class);
    private final Map<Sound, AudioFormat> soundFormats = new EnumMap<>(Sound.class);

    public SoundManager() {
        preloadEffects();
    }

    private void preloadEffects() {
        for (Sound sound : Sound.values()) {
            if (sound == Sound.BACKGROUND) continue;

            try {
                URL soundURL = getClass().getClassLoader().getResource(sound.getPath());
                if (soundURL == null) continue;

                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
                AudioFormat format = ais.getFormat();
                byte[] data = ais.readAllBytes();
                soundEffectData.put(sound, data);
                soundFormats.put(sound, format);
            } catch (Exception e) {
                System.err.println("Failed to preload sound: " + sound.getPath());
            }
        }
    }

    public void play(Sound sound, boolean loop) {
        if (sound == Sound.BACKGROUND) {
            stopBackground();
            try {
                URL url = getClass().getClassLoader().getResource(sound.getPath());
                if (url == null) return;

                AudioInputStream stream = AudioSystem.getAudioInputStream(url);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(stream);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                System.err.println("Failed to play background: " + e.getMessage());
            }
            return;
        }

        // 🔁 Clone a new Clip from preloaded data every time
        try {
            byte[] audioBytes = soundEffectData.get(sound);
            AudioFormat format = soundFormats.get(sound);
            if (audioBytes == null || format == null) return;

            Clip clip = AudioSystem.getClip();
            clip.open(format, audioBytes, 0, audioBytes.length);
            clip.start();

        } catch (Exception e) {
            System.err.println("Failed to play sound: " + e.getMessage());
        }
    }

    public void stopBackground() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
            backgroundClip = null;
        }
    }
}
