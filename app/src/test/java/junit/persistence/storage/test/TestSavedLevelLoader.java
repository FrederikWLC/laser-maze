package junit.persistence.storage.test;

import model.persistence.storage.SavedLevelLoader;

import java.nio.file.Path;

public class TestSavedLevelLoader extends SavedLevelLoader {

    private final Path testFolderPath;

    public TestSavedLevelLoader(Path testFolderPath) {
        this.testFolderPath = testFolderPath;
    }

    @Override
    public Path getFolderPath() {
        return testFolderPath;
    }

}
