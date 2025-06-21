package junit.persistence.storage;

import junit.persistence.storage.test.TestDefaultLevelLoader;
import junit.persistence.storage.test.TestLevelSaver;
import junit.persistence.storage.test.TestSavedLevelLoader;
import model.domain.level.Level;
import model.persistence.storage.LevelIOHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;



import java.nio.file.Path;

public class LevelIOTest {

    @Test
    public void testLoadLevel(@TempDir Path ressourcePath,@TempDir Path appDataPath) throws Exception {
        // Arrange
        int id = 1;
        int width = 5;
        int height = 5;
        int requiredTargetNumber = 1;
        int currentTargetNumber = 0;
        boolean complete = false;

        // Create mock level file in mock resource path
        Path levelFile = ressourcePath.resolve("1.json");
        String json = String.format("""
            {
              "id": %d,
              "width": %d,
              "height": %d,
              "requiredTargetNumber": %d,
              "currentTargetNumber": %d,
              "complete": %b,
              "tokens": []
            }
        """, id, width, height, requiredTargetNumber, currentTargetNumber, complete);

        Files.writeString(levelFile, json, StandardCharsets.UTF_8);

        // Mock the loaders and saver to inject test paths
        TestDefaultLevelLoader defaultLevelLoader = new TestDefaultLevelLoader(ressourcePath);
        TestSavedLevelLoader savedLevelLoader = new TestSavedLevelLoader(appDataPath);
        TestLevelSaver levelSaver = new TestLevelSaver(appDataPath);
        LevelIOHandler levelIOHandler = new LevelIOHandler(defaultLevelLoader, savedLevelLoader,levelSaver);

        // Load the default level with the given ID
        Level defaultLevel = levelIOHandler.load(id);
        assertNotNull(defaultLevel,"Default level should not be null");
        assertEquals(id,defaultLevel.getId(),"Loaded level's id should match the requested id");
        assertEquals(width,defaultLevel.getBoard().getWidth(), "Loaded level's board width should match the expected width");
        assertEquals(height,defaultLevel.getBoard().getHeight(), "Loaded level's board height should match the expected height");
        assertEquals(requiredTargetNumber,defaultLevel.getRequiredTargetNumber(), "Loaded level's required target number should match the expected value");
        assertEquals(currentTargetNumber,defaultLevel.getCurrentTargetNumber(), "Loaded level's current target number should match the expected value");
        assertEquals(complete, defaultLevel.isComplete(), "Loaded level's completion state should match the expected value");

        // Alter the level's properties
        defaultLevel.setComplete(true);

        // Save the altered level
        levelIOHandler.save(defaultLevel);

        // Load the saved level
        Level loadedSavedLevel = levelIOHandler.load(id);

        // Check that the loaded saved level matches the altered properties
        assertTrue(loadedSavedLevel.isComplete(),"Loaded level's completion state should match the expected value after saving");

        // Restart the level (is reloaded implicitly)
        Level restartedLevel = levelIOHandler.restart(loadedSavedLevel);
        assertEquals(complete,restartedLevel.isComplete(), "Loaded level's completion state should match the expected value after restart");
    }
}
