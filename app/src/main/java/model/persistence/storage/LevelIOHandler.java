package model.persistence.storage;

import model.domain.level.Level;

import java.util.List;

public class LevelIOHandler {
    private DefaultLevelLoader defaultLevelLoader;
    private SavedLevelLoader savedLevelLoader;
    private LevelSaver levelSaver;

    public LevelIOHandler(DefaultLevelLoader defaultLevelLoader, SavedLevelLoader savedLevelLoader, LevelSaver levelSaver) {
        this.defaultLevelLoader = defaultLevelLoader;
        this.savedLevelLoader = savedLevelLoader;
        this.levelSaver = levelSaver;
    }

    public DefaultLevelLoader getDefaultLevelLoader() {
        return defaultLevelLoader;
    }

    public SavedLevelLoader getSavedLevelLoader() {
        return savedLevelLoader;
    }

    public LevelSaver getLevelSaver() {
        return levelSaver;
    }

    public Level load(int levelId) {
        Level level;
        try {
            level = getSavedLevelLoader().load(levelId);
        } catch (RuntimeException e) {
            // If the level is not found, "save" and return a default level
            level = getDefaultLevelLoader().load(levelId);
        }
        return level;
    }

    public List<Level> loadAll() {
            try {
                return getDefaultLevelLoader().getAllAvailableLevelIds()
                        .stream()
                        .map(levelId -> load(levelId))
                        .toList();
            } catch (RuntimeException e) {
                throw new RuntimeException("Failed to load all levels: " + e.getMessage(), e);
            }
    }

    public void save(Level level) {
        getLevelSaver().save(level);
    }

    public Level restart(Level level) {
        Level defaultLevel = getDefaultLevelLoader().load(level.getId());
        save(defaultLevel);
        return defaultLevel;
    }
}
