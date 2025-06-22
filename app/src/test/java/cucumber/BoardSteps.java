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
import model.domain.level.Level;
import model.domain.level.builder.LevelBuilder;
import model.domain.token.base.*;
import model.domain.token.builder.base.*;
import model.domain.token.builder.impl.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardSteps extends BaseSteps {

    LevelEngine levelEngine = new LevelEngine();
    LaserEngine laserEngine = new LaserEngine();
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
        assertNotNull(laser, "Laser was not initialized. Ensure it's placed and saved via saveTokenAsType()");
        laser.trigger(true);
    }

    @And("the laser forms a beam path")
    public void theLaserFormsABeamPath() {
        actualBeamPath = laserEngine.fire(laser, board);
    }

    @And("the level's laser forms a beam path")
    public void theLevelsLaserFormsABeamPath() {
        actualBeamPath = levelEngine.fireLaserToken(level);
    }

    @Then("a laser should be present on the board")
    public void aLaserShouldBePresent() {
        assertNotNull(laser, "No laser assigned via saveTokenAsType()");
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

    public List<Token> getTokensFromTable(DataTable table) {
        return table.asMaps(String.class, String.class)
                .stream()
                // keep only rows where preplaced == true
                .map(row -> {
                    Boolean turnable = Boolean.parseBoolean(row.get("turnable"));
                    Boolean movable = Boolean.parseBoolean(row.get("movable"));
                    Integer x = row.get("x") != null ? Integer.parseInt(row.get("x")) : null;
                    Integer y = row.get("y") != null ? Integer.parseInt(row.get("y")) : null;
                    Direction dir = row.get("dir") != null ? Direction.valueOf(row.get("dir").toUpperCase()) : null;
                    return buildToken(row.get("token"),
                            x,y,dir,
                            movable, turnable);
                    }
                ).toList();
    }

    @And("the number of targets hit by the beam path should be {int}")
    public void theNumberOfTargetsHitShouldBe(int n) {
        int actualHitCount = laserEngine.getTargetHitNumber(actualBeamPath, level.getTokens());
        assertEquals(n,actualHitCount,
                "Number of targets hit should be " + n + ", but is: " + actualHitCount);
    }

    @And("the beam path should hit all the required targets")
    public void theBeamPathHitsAllTheRequiredTargets() {
        boolean allRequiredTargetsHit = laserEngine.areAllRequiredTargetsHit(actualBeamPath, level.getTokens());
        assertTrue(allRequiredTargetsHit, "Beam path does not hit all required targets");
    }

    @And("the beam path should touch every touch-required token given by the level")
    public void theBeamPathTouchesEveryTokenOnTheBoardExceptTheOnesNotTouchRequired() {
        boolean allTouchRequiredTokensTouched = laserEngine.areAllTouchRequiredTokensTouched(actualBeamPath, level.getTokens());
        assertTrue(allTouchRequiredTokensTouched, "Beam path does not touch all touch-required tokens");
    }

    @And("the beam path should pass through all checkpoints")
    public void theBeamPathShouldPassThroughAllCheckpoints() {
        boolean allCheckpointsPenetrated = laserEngine.areAllCheckpointsPenetrated(actualBeamPath, level.getTokens());
        assertTrue(allCheckpointsPenetrated, "Beam path does not pass through all checkpoints");
    }

    @And("all turnable tokens should have a direction")
    public void allTurnableTokensShouldHaveADirection() {
        for (Token token : level.getTokens()) {
            if (token instanceof ITurnableToken turnableToken) {
                assertNotNull(turnableToken.getDirection(),
                        "Token " + token.getClass().getSimpleName() + " should have a direction, but does not");
            }
        }
    }

    @And("all tokens required to be placed should be placed on the board")
    public void allTokensRequiredToBePlacedShouldBePlacedOnTheBoard() {
        for (Token token : level.getRequiredTokens()) {
            assertTrue(token.isPlaced(),
                    "Token " + token.getClass().getSimpleName() + " should be placed on the board, but is not");
        }
    }

    @Given("I activate the level's laser")
    public void iActivateTheLevelsLaser() {
        levelEngine.triggerLaserToken(level, true);
        laser = level.getActiveLaser().get();
    }


    @And("the first pair of the level's {tokenType} tokens are each other's twins")
    public void theFirstPairOfTheLevelsTokensAreEachOthersTwins(Class<? extends MutableTwinToken> tokenType) {
        List<MutableTwinToken> pair = getTwinPairOfType(tokenType, level.getTokens());
        MutableTwinToken first  = pair.get(0);
        MutableTwinToken second = pair.get(1);
        first.setTwin(second);
        second.setTwin(first);
    }

    @And("the first pair of the level's {tokenType} tokens should be each other's twins")
    public void theFirstPairOfTheLevelsTokensShouldBeEachOthersTwins(Class<? extends MutableTwinToken> tokenType) {
        List<MutableTwinToken> pair = getTwinPairOfType(tokenType, level.getTokens());
        MutableTwinToken first  = pair.get(0);
        MutableTwinToken second = pair.get(1);
        assertEquals(second, first.getTwin(),
                "First " + tokenType.getSimpleName() + "'s twin should be the second token of same type, but is: " + first.getTwin());
        assertEquals(first, second.getTwin(),
                "Second " + tokenType.getSimpleName() +"token's twin should be the first token of same type, but is: " + second.getTwin());
    }

    @Then("the Portal token's blue opening side should face {direction}")
    public void thePortalTokenSBlueOpeningSideShouldFaceUp(Direction direction) {
        assertEquals(direction, portal.getBluePortalDirection());
    }

    @Then("the Portal token's red opening side should face {direction}")
    public void thePortalTokenSRedOpeningSideShouldFaceUp(Direction direction) {
        assertEquals(direction, portal.getRedPortalDirection());
    }

    //Step definitions that should be  moved to a separate file (but it might be impossible):

    @And("the level is initialized with id {int}, required target number {int}, a board with width {int} and height {int}, and the following tokens:")
    public void theLevelIsInitializedWithIdRequiredTargetNumberABoardWithWidthAndHeightAndTheFollowingTokens(
            int id, int reqTargets, int w, int h, DataTable table) {
        level = new LevelBuilder(id)
                .withBoardDimensions(w, h)
                .withRequiredTargetNumber(reqTargets)
                .withTokens(getTokensFromTable(table))
                .build();
        board = level.getBoard();
    }

    @Then("the level's id should be {int}")
    public void theLevelsIdShouldBe(int id) {
        assertEquals(id, level.getId(),
                "Expected level id: " + id + ", but was: " + level.getId());
    }

    @And("the level's required target number should be {int}")
    public void theLevelsRequiredTargetNumberShouldBe(int n) {
        assertEquals(n, level.getRequiredTargetNumber(),
                "Expected required target number: " + n + ", but was: " + level.getRequiredTargetNumber());
    }

    @And("the level's board should have width {int} and height {int}")
    public void theLevelsBoardShouldHaveWidthAndHeight(int width, int height) {
        assertEquals(width, level.getBoard().getWidth(),
                "Expected board width: " + width + ", but was: " + level.getBoard().getWidth());
        assertEquals(height, level.getBoard().getHeight(),
                "Expected board height: " + height + ", but was: " + level.getBoard().getHeight());
    }

    @And("the level's tokens should be:")
    public void theLevelSTokensShouldBe(DataTable table) {
        List<Class<? extends Token>> expected = table.asMaps(String.class, String.class).stream()
                .map(row -> getTokenType(row.get("token")))
                .collect(java.util.stream.Collectors.toList());  // 👈 fix here

        List<Class<? extends Token>> actual = level.getTokens().stream()
                .map(Token::getClass)
                .collect(java.util.stream.Collectors.toList());  // 👈 fix here

        assertEquals(expected, actual, "Level token types do not match expected");
    }

    @Given("I place token {int} \\(from the required tokens) on the board at \\({int}, {int})")
    public void iPlaceTokenFromTheRequiredTokensOnTheBoardAt(int idx, int x, int y) {
        token = level.getRequiredTokens().get(idx);
        levelEngine.placeRequiredToken(level, token, new Position(x, y));
        saveTokenAsType(token);
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

    @And("the remaining number of required tokens to be placed should be {int}")
    public void theRemainingNumberOfRequiredTokensShouldBe(int count) {
        assertEquals(count, level.getRequiredTokens().size(),
                "Remaining required tokens to be placed should be " + count);
    }
    @Then("the level should be incomplete")
    public void theLevelShouldBeIncomplete() {
        levelEngine.updateAndCheckLevelCompletionState(level);
        assertFalse(level.isComplete(), "Level should not be complete, but is");
    }

    @Then("the level should be complete")
    public void theLevelShouldBeComplete() {
        levelEngine.updateAndCheckLevelCompletionState(level);
        assertTrue(level.isComplete(), "Level should be complete, but is not");
    }

    @Then("all tokens should be placed")
    public void allTokensShouldBePlaced() {
        assertTrue(level.areAllTokensPlaced(), "Expected all tokens to be placed");
    }

    @Then("not all tokens should be placed")
    public void notAllTokensShouldBePlaced() {
        assertFalse(level.areAllTokensPlaced(), "Expected not all tokens to be placed");
    }

    @When("I try to trigger the level's laser")
    public void iTryToTriggerTheLevelsLaser() {
        try {
            levelEngine.triggerLaserToken(level, true);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("I try to fire the level's laser")
    public void iTryToFireTheLevelsLaser() {
        try {
            levelEngine.fireLaserToken(level);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the level's current target number should be {int}")
    public void theLevelsCurrentTargetNumberShouldBe(int n) {
        assertEquals(n, level.getCurrentTargetNumber(),
                "Expected current target number to be " + n);
    }

    @When("I set the level's inventory to the current board")
    public void iSetTheLevelsInventoryToTheCurrentBoard() {
        // This calls Level.setInventory(...)
        level.setInventory(board);
    }

    @Then("the level's inventory should be the current board")
    public void theLevelsInventoryShouldBeTheCurrentBoard() {
        // getInventory() must return exactly what we set
        assertSame(board, level.getInventory(),
                "Expected level.getInventory() to return the board we just set");
    }

    @Then("the Cell Blocker token should not require touch")
    public void theCellBlockerTokenShouldNotRequireTouch() {
        assertFalse(cellBlocker.isTouchRequired(),
                "Cell Blocker should never require touch");
    }
}