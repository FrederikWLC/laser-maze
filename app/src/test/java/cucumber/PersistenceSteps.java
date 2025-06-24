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

import io.cucumber.datatable.DataTable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;


public class PersistenceSteps {
    private final TestWorld world = new TestWorld();
    private List<Integer> listedIds;
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
              "tokens": [
                         {
                                       "type": "DoubleMirrorToken",
                                       "movable": true,
                                       "turnable": true
                                     },
                                     {
                                       "type": "LaserToken",
                                       "x": 3,
                                       "y": 1,
                                       "movable": false,
                                       "turnable": true
                                     },
                                     {
                                       "type": "TargetMirrorToken",
                                       "x": 2,
                                       "y": 0,
                                       "movable": false,
                                       "turnable": true,
                                       "isRequiredTarget": true
                                     },
                                     {
                                       "type": "TargetMirrorToken",
                                       "x": 0,
                                       "y": 1,
                                       "movable": false,
                                       "turnable": true
                                     },
                                     {
                                       "type": "TargetMirrorToken",
                                       "x": 1,
                                       "y": 2,
                                       "movable": false,
                                       "turnable": true
                                     },
                                     {
                                       "type": "TargetMirrorToken",
                                       "x": 0,
                                       "y": 3,
                                       "movable": false,
                                       "turnable": true
                                     },
                                     {
                                       "type": "TargetMirrorToken",
                                       "x": 2,
                                       "y": 3,
                                       "movable": false,
                                       "turnable": true
                                     },
                                     {
                                       "type": "CheckpointToken",
                                       "x": 1,
                                       "y": 1,
                                       "direction": "LEFT",
                                       "movable": false,
                                       "turnable": true
                                     },
                                     {
                                       "type": "BeamSplitterToken",
                                       "x": 2,
                                       "y": 2,
                                       "movable": false,
                                       "turnable": true
                                     }]
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
        Level defaultLevel = defaultLevelLoader.load(level.getId());
        String levelSerialization = levelSerializer.serialize(level).toString();
        String defaultLevelSerialization = levelSerializer.serialize(defaultLevel).toString();
        assertEquals(levelSerialization, defaultLevelSerialization,
                "The level should be loaded from the default source, but it was not.");;
    }

    @Then("the level should not be equivalent to the one loaded from the default source")
    public void theLevelShouldNotBeEquivalentToTheOneLoadedFromTheDefaultSource() {
        Level defaultLevel = defaultLevelLoader.load(level.getId());
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

    @Given("the resource folder has no JSON files")
    public void theResourceFolderHasNoJSONFiles() throws IOException {
        Files.list(world.resourcePath)
                .filter(p -> p.toString().endsWith(".json"))
                .forEach(p -> p.toFile().delete());
    }

    @Given("the resource folder contains files:")
    public void theResourceFolderContainsFiles(DataTable table) throws IOException {
        Files.list(world.resourcePath)
                .forEach(p -> p.toFile().delete());
        for (Map<String,String> row : table.asMaps(String.class, String.class)) {
            Path f = world.resourcePath.resolve(row.get("filename"));
            Files.writeString(f, "{}", java.nio.charset.StandardCharsets.UTF_8);
        }
    }

    @When("I retrieve all available level IDs")
    public void iRetrieveAllAvailableLevelIDs() {
        listedIds = defaultLevelLoader.getAllAvailableLevelIds();
    }

    @Then("the list of available level IDs should be empty")
    public void theListOfAvailableLevelIDsShouldBeEmpty() {
        assertNotNull(listedIds);
        assertTrue(listedIds.isEmpty(), "Expected no level IDs but got " + listedIds);
    }

    @Then("the list of available level IDs should be:")
    public void theListOfAvailableLevelIDsShouldBe(DataTable table) {
        List<Integer> expected = table.asMaps().stream()
                .map(m -> Integer.parseInt(m.get("id")))
                .sorted()
                .collect(Collectors.toList());

        List<Integer> actualSorted = listedIds.stream()
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actualSorted, "Parsed level IDs mismatch");
    }
}