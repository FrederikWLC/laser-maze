package cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.domain.board.*;
import model.domain.board.builder.BoardBuilder;
import model.domain.engine.BoardEngine;
import model.domain.engine.LaserEngine;
import model.domain.engine.LevelEngine;
import model.domain.token.base.*;
import model.domain.token.builder.base.*;
import model.domain.token.builder.impl.*;
import model.domain.token.impl.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardSteps extends BaseSteps {
    public Exception exception;
    public Tile tile;
    public List<PositionDirection> actualBeamPath;

    @ParameterType("(?i)laser|cell blocker|double mirror|target mirror|beam splitter|checkpoint|portal")
    public Class<? extends Token> tokenType(String name) {
        return switch (name.toLowerCase()) {
            case "laser" -> LaserToken.class;
            case "cell blocker" -> CellBlockerToken.class;
            case "double mirror" -> DoubleMirrorToken.class;
            case "target mirror" -> TargetMirrorToken.class;
            case "beam splitter" -> BeamSplitterToken.class;
            case "checkpoint" -> CheckpointToken.class;
            case "portal" -> PortalToken.class;
            default -> throw new IllegalArgumentException("Unknown token type: " + name);
        };
    }


    @Given("^a new game is started$")
    public void aNewGameIsStarted() {
        // Placeholder for game start initialization if needed
    }

    @When("^the board is initialized with width (\\d+) and height (\\d+)$")
    public void theBoardIsInitializedWithWidthAndHeight(int width, int height) {
        board = new BoardBuilder().withDimensions(width, height).build();
    }

    @Then("^the board should have (\\d+) empty tiles$")
    public void theBoardShouldHaveEmptyTiles(int expectedCount) {
        int emptyTileCount = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.getTile(x, y).isEmpty()) {
                    emptyTileCount++;
                }
            }
        }
        assertEquals(expectedCount, emptyTileCount);
    }

    @Then("the tile should exist and be empty")
    public void theTileShouldExistAndBeEmpty() {
        assertNotNull(tile);
        assertTrue(tile.isEmpty());
    }

    @Then("the tile should exist and not be empty")
    public void theTileShouldExistAndNotBeEmpty() {
        assertNotNull(tile);
        assertFalse(tile.isEmpty());
    }

    @When("I try to get the tile at position \\({int}, {int})")
    public void iTryToGetTheTileAtPosition(int x, int y) {
        try {
            tile = board.getTile(x, y);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("I get the tile at position \\({int}, {int})")
    public void iGetTheTileAtPosition(int x, int y) {
        tile = board.getTile(x, y);
    }

    @Then("an error should occur")
    public void anErrorShouldOccur() {
        assertNotNull(exception);
    }

    @Then("the board width should be {int}")
    public void theBoardWidthShouldBe(int expectedWidth) {
        assertEquals(expectedWidth, board.getWidth());
    }

    @And("the board height should be {int}")
    public void theBoardHeightShouldBe(int expectedHeight) {
        assertEquals(expectedHeight, board.getHeight());
    }

    @Then("the tile's position should be \\({int}, {int})")
    public void theTileSPositionShouldBe(int x, int y) {
        assertEquals(x, tile.getX());
        assertEquals(y, tile.getY());
    }

    @Then("the tile should return null")
    public void theTileShouldReturnNull() {
        assertNull(tile);
    }

    @When("I activate the laser")
    public void iActivateTheLaser() {
        laser.trigger(true);
    }

    @And("the laser forms a beam path")
    public void theLaserFormsABeamPath() {
        actualBeamPath = LaserEngine.fire(laser, board);
    }

    @And("the level's laser forms a beam path")
    public void theLevelsLaserFormsABeamPath() {
        actualBeamPath = LevelEngine.fireLaserToken(level);
    }

    @Then("the laser beam should pass through the following position directions:")
    public void theLaserBeamShouldPassThroughTheFollowingPositionDirections(DataTable table) {
        List<PositionDirection> expected = table.asMaps(String.class, String.class)
                .stream()
                .map(row -> new PositionDirection(
                        new Position(Integer.parseInt(row.get("x")),
                                Integer.parseInt(row.get("y"))),
                        Direction.valueOf(row.get("dir").toUpperCase())))
                .toList();

        assertEquals(expected, actualBeamPath,   // actualBeamPath is now List<PositionDirection>
                () -> "Beam path mismatch; expected " + expected +
                        " but was " + actualBeamPath);
    }

    @And("the number of targets hit by the beam path should be {int}")
    public void theNumberOfTargetsHitShouldBe(int expected) {
        int actual = LaserEngine.getTargetHitNumber(actualBeamPath, level.getTokens());
        assertEquals(expected, actual);
    }

    @And("the beam path should hit all the required targets")
    public void theBeamPathHitsAllTheRequiredTargets() {
        assertTrue(LaserEngine.areAllRequiredTargetsHit(actualBeamPath, level.getTokens()));
    }

    @And("the beam path should touch every touch-required token given by the level")
    public void theBeamPathTouchesEveryTokenOnTheBoardExceptTheOnesNotTouchRequired() {
        assertTrue(LaserEngine.areAllTouchRequiredTokensTouched(actualBeamPath, level.getTokens()));
    }

    @And("the beam path should pass through all checkpoints")
    public void theBeamPathShouldPassThroughAllCheckpoints() {
        assertTrue(LaserEngine.areAllCheckpointsPenetrated(actualBeamPath, level.getTokens()));
    }

    @And("all turnable tokens should have a direction")
    public void allTurnableTokensShouldHaveADirection() {
        level.getTokens().stream()
                .filter(t -> t instanceof ITurnableToken)
                .map(t -> (ITurnableToken) t)
                .forEach(t -> assertNotNull(t.getDirection()));
    }

    @And("all tokens required to be placed should be placed on the board")
    public void allTokensRequiredToBePlacedShouldBePlacedOnTheBoard() {
        level.getRequiredTokens().forEach(t -> assertTrue(t.isPlaced()));
    }

    @Given("I activate the level's laser")
    public void iActivateTheLevelsLaser() {
        LevelEngine.triggerLaserToken(level, true);
        laser = level.getActiveLaser().orElseThrow();
    }

    @Then("the Portal token's blue opening side should face {direction}")
    public void thePortalTokenSBlueOpeningSideShouldFaceUp(Direction direction) {
        assertEquals(direction, portal.getBluePortalDirection());
    }

    @Then("the Portal token's red opening side should face {direction}")
    public void thePortalTokenSRedOpeningSideShouldFaceUp(Direction direction) {
        assertEquals(direction, portal.getRedPortalDirection());
    }
}
