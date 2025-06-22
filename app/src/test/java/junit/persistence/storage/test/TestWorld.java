package junit.persistence.storage.test;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestWorld {
    public final Path resourcePath;
    public final Path appDataPath;

    public TestWorld()  {
        try {
            resourcePath = Files.createTempDirectory("resources");
            appDataPath = Files.createTempDirectory("appData");
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temporary directories for test world", e);
        }
    }
}
