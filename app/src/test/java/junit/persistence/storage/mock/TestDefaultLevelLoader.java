package junit.persistence.storage.test;

import model.persistence.storage.DefaultLevelLoader;

import java.nio.file.Path;

public class TestDefaultLevelLoader extends DefaultLevelLoader {

    private final Path testFolderPath;

    public TestDefaultLevelLoader(Path testFolderPath) {
        this.testFolderPath = testFolderPath;
    }

    @Override
    public Path getFolderPath() {
        return testFolderPath;
    }
}
