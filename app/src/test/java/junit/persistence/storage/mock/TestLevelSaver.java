package junit.persistence.storage.test;

import model.persistence.storage.LevelSaver;

import java.nio.file.Path;

public class TestLevelSaver extends LevelSaver {
    private final Path testFolderPath;

    public TestLevelSaver(Path testFolderPath) {
        this.testFolderPath = testFolderPath;
    }

    @Override
    public Path getFolderPath() {
        return testFolderPath;
    }
}
