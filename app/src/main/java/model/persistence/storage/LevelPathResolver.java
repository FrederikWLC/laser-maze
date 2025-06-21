package model.persistence.storage;

import model.persistence.serializer.LevelSerializer;
import model.persistence.util.FolderNameRegistry;
import model.persistence.util.PathHelper;

import java.nio.file.Path;

public abstract class LevelPathResolver {
    // used by both DefaultLevelLoader and LevelSaver
    protected final LevelSerializer levelSerializer = new LevelSerializer();
    protected final PathHelper pathHelper = new PathHelper();

    public String getFolderName() {
        return FolderNameRegistry.LEVEL_FOLDER_NAME;
    }

    public abstract Path getFolderPath();

    public Path getLevelPath(int id) {
        return getFolderPath().resolve(id + ".json");
    }

}
