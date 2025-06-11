package cucumber;

import io.cucumber.datatable.DataTable;
import model.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardSteps {
    Board board;
    BoardEngine boardEngine;
    Tile tile;
    LaserEngine laserEngine;
    LaserToken laser;
    CellBlockerToken cellBlocker;
    List<PositionDirection> actualBeamPath;

    @Given("^a new game is started$")
    public void aNewGameIsStarted() {

    }

    @When("^the board is initialized with width (\\d+) and height (\\d+)$")
    public void theBoardIsInitializedWithWidthAndHeight(int width, int height) {
        board = new Board(width, height);
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

    @When("I get the tile at position \\({int}, {int})")
    public void iGetTheTileAtPosition(int x, int y) {
        tile = board.getTile(x, y);
    }

    Exception exception;
    @When("I try to get the tile at position \\({int}, {int})")
    public void iTryToGetTheTileAtPosition(int x, int y) {
        try {
            board.getTile(x, y);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("an error should occur")
    public void anErrorShouldOccur() {
        assertNotNull(exception);
        assertTrue(exception instanceof Exception);
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
    public void theTileSPositionShouldBe(int expectedX, int expectedY) {
        assertEquals(expectedX, tile.getX());
        assertEquals(expectedY, tile.getY());
    }

    @Given("the board contains a Laser token at \\({int}, {int}) facing {direction}")
    public void theBoardContainsALaserTokenAtFacingRight(int x, int y, Direction direction) {
        laserEngine = new LaserEngine();
        laser = new LaserToken(new Position(x, y), direction);
    }

    @When("I activate the laser")
    public void iActivateTheLaser() {
        laser.trigger(true);
    }

    @And("the laser forms a beam path")
    public void theLaserFormsABeamPath() {
        actualBeamPath = laserEngine.fire(laser, board);
    }

    @Then("the laser beam should pass through the following position directions:")
    public void theLaserBeamShouldPassThroughTheFollowingPositions(DataTable table) {

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

    @Then("the tile should return null")
    public void theTileShouldReturnNull() {
        assertNull(tile);
    }

    @And("the board contains a Cell Blocker token at \\({int}, {int})")
    public void theBoardContainsACellBlockerTokenAt(int x, int y) {
        cellBlocker = new CellBlockerToken(new Position(x, y));
        Tile blockerTile = board.getTile(x, y);
        blockerTile.setToken(cellBlocker);
    }


    @Given("I try to move the Cell Blocker token to \\({int}, {int})")
    public void iTryToMoveTheCellBlockerTokenTo(int x, int y) {
        BoardEngine boardEngine = new BoardEngine();
        boardEngine.moveToken(cellBlocker, new Position(x, y), board);
    }

    @Then("the Cell Blocker token should remain at \\({int}, {int})")
    public void theCellBlockerTokenShouldRemainAt(int x, int y) {
        Tile blockerTile = board.getTile(x, y);
        assertEquals(cellBlocker,blockerTile.getToken(), "Cell Blocker token should not change position as it is immutable");
        assertEquals(cellBlocker.getPosition(),new Position(x, y),"Cell Blocker token should not change position as it is immutable");
    }

    @Given("I try to turn the Cell Blocker token to face right")
    public void iTryToTurnTheCellBlockerTokenToFaceRight() {
        BoardEngine boardEngine = new BoardEngine();
        boardEngine.turnToken(cellBlocker, Direction.RIGHT);
    }

    @Then("the Cell Blocker token should still face down")
    public void theCellBlockerTokenShouldStillFaceDown() {
        assertEquals(Direction.DOWN, cellBlocker.getDirection(),
                "Cell Blocker token should not change direction as it is immutable");
    }
}
