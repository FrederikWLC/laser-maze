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
import model.domain.token.base.*;
import model.domain.token.builder.base.*;
import model.domain.token.builder.impl.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardSteps extends BaseSteps {

    LaserEngine laserEngine;
    BoardEngine boardEngine = new BoardEngine();

    @ParameterType("(?i)laser|cell blocker|double mirror|target mirror|beam splitter|checkpoint|portal")
    public Token token(String name) {
        return getSavedToken(name);
    }

    @ParameterType("(?i)laser|cell blocker|double mirror|target mirror|beam splitter|checkpoint|portal")
    public TokenBuilder tokenBuilder(String name) {
        return getTokenBuilder(name);
    }

    @ParameterType("(?i)laser|cell blocker|double mirror|target mirror|beam splitter|checkpoint|portal")
    public Class<? extends Token> tokenType(String name) {
        return getTokenType(name);
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
    public void theTileSPositionShouldBe(int expectedX, int expectedY) {
        assertEquals(expectedX, tile.getX());
        assertEquals(expectedY, tile.getY());
    }

    @When("I activate the laser")
    public void iActivateTheLaser() {
        laserEngine = new LaserEngine(laser,board);
        assertNotNull(laser, "Laser was not initialized. Ensure it's placed and saved via saveTokenAsType()");
        laser.trigger(true);
    }

    @And("the laser forms a beam path")
    public void theLaserFormsABeamPath() {
        laserEngine.fire();
        actualBeamPath = laserEngine.getLastBeamPath();
    }


    @Then("a laser should be present on the board")
    public void aLaserShouldBePresent() {
        assertNotNull(laser, "No laser assigned via saveTokenAsType()");
    }

    @Then("the laser beam should pass through the following position directions:")
    public void theLaserBeamShouldPassThroughTheFollowingPositionTurns(DataTable table) {
        List<PositionTurn> expected = table.asMaps(String.class, String.class)
                .stream()
                .map(row -> new PositionTurn(
                        new Position(Integer.parseInt(row.get("x")),
                                Integer.parseInt(row.get("y"))),
                        Direction.valueOf(row.get("in").toUpperCase()),
                        Direction.valueOf(row.get("out").toUpperCase())))
                .toList();

        assertEquals(expected, actualBeamPath,
                () -> "Beam path mismatch; expected " + expected +
                        " but was " + actualBeamPath);
    }

    @Then("the tile should return null")
    public void theTileShouldReturnNull() {
        assertNull(tile);
    }

    @And("a Cell Blocker token is preplaced on the board at \\({int}, {int})")
    public void aCellBlockerTokenIsPreplacedOnTheBoardAt(int x, int y) {
        cellBlocker = new CellBlockerTokenBuilder()
                .withPosition(x,y)
                .build();
        boardEngine.placeToken(board,cellBlocker, new Position(x, y));
    }

    public Token buildToken(String tokenName, Integer x, Integer y, Direction direction, boolean movable, boolean turnable) {
        TokenBuilder tokenBuilder = getTokenBuilder(tokenName);
        if (IMutableToken.class.isAssignableFrom(getTokenType(tokenName))) {
            tokenBuilder = ((MutableTokenBuilder) tokenBuilder).withMutability(movable, turnable).withDirection(direction);
        }
        if (x != null && y != null) {
            tokenBuilder = tokenBuilder.withPosition(x, y);
        }
        return tokenBuilder.build();
    }

    public void placeTokenOnTheBoard(Board board, String tokenName, int x, int y, Direction direction, boolean movable, boolean turnable) {
        token = buildToken(tokenName, x, y, direction, movable, turnable);
        saveTokenAsType(token);
        boardEngine.placeToken(board,token, new Position(x, y)) ;
    }

    @Given("a completely mutable {tokenName} token is placed on the board at \\({int}, {int}) facing {direction}")
    public void aCompletelyMutableTokenIsPlacedOnTheBoardAtFacing(String tokenName,int x, int y, Direction direction) {
        placeTokenOnTheBoard(board,tokenName,x,y,direction,true,true);
    }

    @Given("a turnable {tokenName} token is preplaced on the board at \\({int}, {int}) facing {direction}")
    public void aTurnableTokenIsPreplacedOnTheBoardAtFacing(String tokenName,int x, int y, Direction direction) {
        placeTokenOnTheBoard(board,tokenName,x,y,direction,false,true);
    }

    @Given("an immutable {tokenName} token is preplaced on the board at \\({int}, {int}) facing {direction}")
    public void anImmutableTokenIsPreplacedOnTheBoardAtFacing(String tokenName,int x, int y, Direction direction) {
        placeTokenOnTheBoard(board,tokenName,x,y,direction,false,false);
    }

    @Then("the {token} token should be movable")
    public void theTokenShouldBeMovable(Token token) {
        assertTrue(token.isMovable(), "Token should be movable, but is not");
    }

    @Then("the {token} token should be turnable")
    public void theTokenShouldBeTurnable(Token token) {
        assertTrue(token.isTurnable(), "Token should be turnable, but is not");
    }

    @Then("the {token} token should be immovable")
    public void theTokenShouldBeImmovable(Token token) {
        assertFalse(token.isMovable(), "Token should be immovable, but is movable");
    }

    @Then("the {token} token should be immutable")
    public void theTokenShouldBeImmutable(Token token) {
        assertFalse(token.isMovable(), "Token should be immutable, but is mutable");
        assertFalse(token.isTurnable(),
                "Token should be immutable, but is turnable");
    }

    @Given("I try to move the {token} token to \\({int}, {int})")
    public void iTryToMoveTheTokenTo(Token token, int x, int y) {
        try {
            boardEngine.moveToken(board, token, new Position(x, y));
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("I move the {token} token to \\({int}, {int})")
    public void iMoveTheTokenTo(Token token, int x, int y) {
        boardEngine.moveToken(board, token, new Position(x, y));
    }

    @Then("the {token} token should be at \\({int}, {int})")
    public void theTokenShouldRemainAt(Token token, int x, int y) {
        Tile tokenTile = board.getTile(x, y);
        assertEquals(token,tokenTile.getToken(), "Token should be in position:"+ " (" + x + ", " + y + ")");
        assertEquals(new Position(x, y),token.getPosition(),"Token should not change position:" + " (" + x + ", " + y + ")");
    }

    @Given("I try to turn the {token} token to face {direction}")
    public void iTryToTurnTheTokenToFace(Token token, Direction direction) {
        try {
            boardEngine.turnToken((MutableToken) token, direction);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("I turn the {token} token to face {direction}")
    public void iTurnTheTokenToFace(Token token, Direction direction) {
            boardEngine.turnToken((MutableToken) token, direction);
    }

    @Then("the {token} token should face {direction}")
    public void theTokenShouldFace(Token token, Direction direction) {
        assertEquals(direction, ((ITurnableToken) token).getDirection(), "Token should not change direction");
    }


    @Then("the token on the board at \\({int}, {int}) should be a {tokenType} token")
    public void theTokenOnTheBoardAtShouldBeAToken(int x, int y, Class<? extends Token> type) {
        Tile t = board.getTile(x, y);
        token = t.getToken();
        assertEquals(type, token.getClass(),
                "Token at (" + x + "," + y + ") should be " + type.getSimpleName());
        saveTokenAsType(token);
    }

    @And("the token should be turnable without direction")
    public void theTokenShouldBeTurnableWithoutDirection() {
        assertTrue(token.isTurnable(), "Token should be turnable");
        assertNull(((ITurnableToken) token).getDirection(),
                "Token should not have a direction");
    }


    @Then("the Cell Blocker token should not require touch")
    public void theCellBlockerTokenShouldNotRequireTouch() {
        assertFalse(cellBlocker.isTouchRequired(),
                "Cell Blocker should never require touch");
    }

    @Given("a completely mutable Laser token is created")
    public void aCompletelyMutableLaserTokenIsCreated() {
        // no position, no direction => isPlaced()==false, isTurned()==false
        laser = new LaserTokenBuilder()
                .withMutability(true, true)
                .build();
        token = laser;
    }

    @When("I try to activate the laser")
    public void iTryToActivateTheLaser() {
        try {
            laser.trigger(true);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("a completely mutable Laser token is placed on the board at \\({int}, {int}) without a direction")
    public void aCompletelyMutableLaserTokenIsPlacedOnTheBoardWithoutDirection(int x, int y) {
        // placed but never turned (direction==null)
        laser = new LaserTokenBuilder()
                .withMutability(true, true)
                .withPosition(x, y)
                .build();
        boardEngine.placeToken(board, laser, new Position(x, y));
    }
}