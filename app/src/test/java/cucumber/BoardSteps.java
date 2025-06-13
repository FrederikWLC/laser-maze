package cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import model.*;
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
    DoubleMirrorToken doubleMirror;
    TargetMirrorToken targetMirror;
    BeamSplitterToken beamSplitter;
    List<PositionDirection> actualBeamPath;

    @ParameterType("(?i)laser|cell blocker|double mirror|target mirror|beam splitter")
    public Token token(String name) {
        switch (name.toLowerCase()) {
            case "laser":
                return laser;
            case "cell blocker":
                return cellBlocker;
            case "double mirror":
                return doubleMirror;
            case "target mirror":
                return targetMirror;
            case "beam splitter":
                return beamSplitter;

        }

        throw new IllegalArgumentException("Unknown token type: " + name);
    }

    @ParameterType("(?i)laser|cell blocker|double mirror|target mirror|beam splitter")
    public String tokenName(String name) {
        return name.toLowerCase();
    }

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

    @When("I activate the laser")
    public void iActivateTheLaser() {
        laser.trigger(true);
    }

    @And("the laser forms a beam path")
    public void theLaserFormsABeamPath() {
        actualBeamPath = laserEngine.fire(laser, board);
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

    @Then("the tile should return null")
    public void theTileShouldReturnNull() {
        assertNull(tile);
    }

    @And("a Cell Blocker token is placed on the board at \\({int}, {int})")
    public void aCellBlockerTokenIsPlacedOnTheBoardAt(int x, int y) {
        cellBlocker = new CellBlockerToken(new Position(x, y));
        Tile blockerTile = board.getTile(x, y);
        blockerTile.setToken(cellBlocker);
    }

    @Given("a {tokenName} token is placed on the board at \\({int}, {int}) facing {direction}")
    public void aTokenIsPlacedOnTheBoardAtFacing(String tokenName,int x, int y, Direction direction) {
        switch (tokenName.toLowerCase()) {
            case "laser":
                laserEngine = new LaserEngine();
                laser = new LaserToken(new Position(x, y), direction);
                Tile laserTile = board.getTile(x, y);
                laserTile.setToken(laser);
                break;
            case "double mirror":
                doubleMirror = new DoubleMirrorToken(new Position(x, y), direction);
                Tile doubleMirrorTile = board.getTile(x, y);
                doubleMirrorTile.setToken(doubleMirror);
                break;
            case "target mirror":
                targetMirror = new TargetMirrorToken(new Position(x, y), direction);
                Tile targetMirrorTile = board.getTile(x, y);
                targetMirrorTile.setToken(targetMirror);
                break;
            case "beam splitter":
                beamSplitter = new BeamSplitterToken(new Position(x, y), direction);
                Tile beamSplitterTile = board.getTile(x, y);
                beamSplitterTile.setToken(beamSplitter);
                break;
            default:
                throw new IllegalArgumentException("Unknown token type: " + tokenName);
        }
    }

    @Given("I try to move the {token} token to \\({int}, {int})")
    public void iTryToMoveTheTokenTo(Token token, int x, int y) {
        BoardEngine boardEngine = new BoardEngine();
        boardEngine.moveToken(token, new Position(x, y), board);
    }

    @Then("the {token} token should be at \\({int}, {int})")
    public void theTokenShouldRemainAt(Token token, int x, int y) {
        Tile tokenTile = board.getTile(x, y);
        assertEquals(token,tokenTile.getToken(), "Token should not change position");
        assertEquals(token.getPosition(),new Position(x, y),"Token should not change position");
    }

    @Given("I try to turn the {token} token to face {direction}")
    public void iTryToTurnTheTokenToFace(Token token, Direction direction) {
        BoardEngine boardEngine = new BoardEngine();
        boardEngine.turnToken(token, direction);
    }

    @Then("the {token} token should face {direction}")
    public void theCellBlockerTokenShouldStillFace(Token token, Direction direction) {
        assertEquals(direction, token.getDirection(),
                "Token should not change direction");
    }
}
