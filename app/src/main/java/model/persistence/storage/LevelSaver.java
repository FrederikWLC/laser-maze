package model.persistence.storage;

import model.domain.level.Level;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LevelSaver extends LevelPathResolver {

    public Path getFolderPath() {
        return pathHelper.getAppDataPath(getFolderName());
    }

    public void save(Level level) {
        Path levelFile = getLevelPath(level.getId());
        try {
            String json = levelSerializer.serialize(level).toString();
            Files.writeString(levelFile, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON for level " + level.getId() + ": " + levelFile, e);
        }
    }

}
