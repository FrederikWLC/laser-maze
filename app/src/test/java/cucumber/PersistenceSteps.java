package cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.persistence.storage.test.TestDefaultLevelLoader;
import junit.persistence.storage.test.TestLevelSaver;
import junit.persistence.storage.test.TestSavedLevelLoader;
import junit.persistence.storage.test.TestWorld;
import model.domain.level.Level;
import model.persistence.serializer.LevelSerializer;
import model.persistence.storage.LevelIOHandler;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistenceSteps {
    private final TestWorld world = new TestWorld();
    LevelSerializer levelSerializer = new LevelSerializer();
    Level level;


    // Mock the loaders and saver to inject test paths
    TestDefaultLevelLoader defaultLevelLoader = new TestDefaultLevelLoader(world.resourcePath);
    TestSavedLevelLoader savedLevelLoader = new TestSavedLevelLoader(world.appDataPath);
    TestLevelSaver levelSaver = new TestLevelSaver(world.appDataPath);
    LevelIOHandler levelIOHandler = new LevelIOHandler(defaultLevelLoader, savedLevelLoader,levelSaver);

    @Given("a default level file with id {int} exists")
    public void aDefaultLevelFileWithId1Exists(int id) throws Exception {
        // Arrange
        int width = 5;
        int height = 5;
        int requiredTargetNumber = 1;
        int currentTargetNumber = 0;
        boolean complete = false;

        // Create mock level file in mock resource path
        Path levelFile = world.resourcePath.resolve("1.json");
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
    }


    @And("no saved level exists for id {int}")
    public void noSavedLevelExistsForId(int arg0) {
        // Ensure no saved level exists in the app data path
        Path savedLevelFile = world.appDataPath.resolve(arg0 + ".json");
        if (Files.exists(savedLevelFile)) {
            try {
                Files.delete(savedLevelFile);
            } catch (Exception e) {
                fail("Failed to delete saved level file: " + e.getMessage());
            }
        }
    }

    @When("the player loads level {int}")
    public void thePlayerLoadsLevel(int id) {
        level = levelIOHandler.load(id);
    }

    @Then("the level should be equivalent to the one loaded from the default source")
    public void theLevelShouldBeLoadedFromTheDefaultSource() {
        Level defaultLevel = levelIOHandler.getDefaultLevelLoader().load(level.getId());
        String levelSerialization = levelSerializer.serialize(level).toString();
        String defaultLevelSerialization = levelSerializer.serialize(defaultLevel).toString();
        assertEquals(levelSerialization, defaultLevelSerialization,
                "The level should be loaded from the default source, but it was not.");;
    }

    @Then("the level should not be equivalent to the one loaded from the default source")
    public void theLevelShouldNotBeEquivalentToTheOneLoadedFromTheDefaultSource() {
        Level defaultLevel = levelIOHandler.getDefaultLevelLoader().load(level.getId());
        String levelSerialization = levelSerializer.serialize(level).toString();
        String defaultLevelSerialization = levelSerializer.serialize(defaultLevel).toString();
        assertNotEquals(levelSerialization, defaultLevelSerialization,
                "The level should not be loaded from the default source, but it was.");;
    }

    @And("the completion state should be false")
    public void theCompletionStateShouldBeFalse() {
        assertFalse(level.isComplete(), "The level should not be complete.");
    }

    @And("the completion state should be true")
    public void theCompletionStateShouldBeTrue() {
        assertTrue(level.isComplete(), "The level should be complete.");
    }

    @And("the player marks the level as complete")
    public void thePlayerMarksTheLevelAsComplete() {
        level.setComplete(true);
    }

    @When("the player saves the level")
    public void thePlayerSavesTheLevel() {
        levelIOHandler.save(level);
    }


    @And("the player loads it again")
    public void thePlayerLoadsItAgain() {
        level = levelIOHandler.load(level.getId());
    }

    @And("the player restarts the level")
    public void thePlayerRestartsTheLevel() {
        level = levelIOHandler.restart(level);
    }
}
