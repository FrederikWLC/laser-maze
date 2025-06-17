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
import model.domain.token.*;
import model.domain.token.builder.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardSteps {
    Exception exception;
    Level level;
    Board board;
    Tile tile;
    Token token;
    LaserToken laser;
    CellBlockerToken cellBlocker;
    DoubleMirrorToken doubleMirror;
    TargetMirrorToken targetMirror;
    BeamSplitterToken beamSplitter;
    CheckpointToken checkpoint;
    List<PositionDirection> actualBeamPath;

    @ParameterType("(?i)laser|cell blocker|double mirror|target mirror|beam splitter|checkpoint")
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
            case "checkpoint":
                return checkpoint;
        }

        throw new IllegalArgumentException("Unknown token type: " + name);
    }

    @ParameterType("(?i)laser|cell blocker|double mirror|target mirror|beam splitter|checkpoint")
    public Class<? extends Token> tokenType(String name) {
        switch (name.toLowerCase()) {
            case "laser":
                return LaserToken.class;
            case "cell blocker":
                return CellBlockerToken.class;
            case "double mirror":
                return DoubleMirrorToken.class;
            case "target mirror":
                return TargetMirrorToken.class;
            case "beam splitter":
                return BeamSplitterToken.class;
            case "checkpoint":
                return CheckpointToken.class;
        }
        throw new IllegalArgumentException("Unknown token type: " + name);
    }

    @Given("^a new game is started$")
    public void aNewGameIsStarted() {

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
        laser.trigger(true);
    }

    @And("the laser forms a beam path")
    public void theLaserFormsABeamPath() {
        actualBeamPath = LaserEngine.fire(laser, board);
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
        BoardEngine.placeToken(board,cellBlocker, new Position(x, y));
    }

    public void placeTokenOnTheBoard(String tokenName, int x, int y, Direction direction, boolean movable, boolean turnable) {
        switch (tokenName.toLowerCase()) {
            case "laser":
                laser = new LaserTokenBuilder().withMutability(movable,turnable)
                        .withPosition(x,y)
                        .withDirection(direction).build();
                BoardEngine.placeToken(board, laser, new Position(x, y));
                break;
            case "double mirror":
                doubleMirror = new DoubleMirrorTokenBuilder().withMutability(movable,turnable)
                        .withPosition(x,y)
                        .withDirection(direction).build();
                BoardEngine.placeToken(board, doubleMirror, new Position(x, y));
                break;
            case "target mirror":
                targetMirror = new TargetMirrorTokenBuilder().withMutability(movable,turnable)
                        .withPosition(x,y)
                        .withDirection(direction).build();
                BoardEngine.placeToken(board, targetMirror, new Position(x, y));
                break;
            case "beam splitter":
                beamSplitter = new BeamSplitterTokenBuilder().withMutability(movable,turnable)
                        .withPosition(x,y)
                        .withDirection(direction).build();
                BoardEngine.placeToken(board, beamSplitter, new Position(x, y));
                break;
            case "checkpoint":
                checkpoint = new CheckpointTokenBuilder().withMutability(movable,turnable)
                        .withPosition(x,y)
                        .withDirection(direction).build();
                BoardEngine.placeToken(board,checkpoint, new Position(x, y)) ;
                break;
            default:
                throw new IllegalArgumentException("Unknown token type: " + tokenName);
        }
    }

    @Given("a completely mutable {tokenName} token is placed on the board at \\({int}, {int}) facing {direction}")
    public void aCompletelyMutableTokenIsPlacedOnTheBoardAtFacing(String tokenName,int x, int y, Direction direction) {
        placeTokenOnTheBoard(tokenName,x,y,direction,true,true);
    }

    @Given("a turnable {tokenName} token is preplaced on the board at \\({int}, {int}) facing {direction}")
    public void aTurnableTokenIsPreplacedOnTheBoardAtFacing(String tokenName,int x, int y, Direction direction) {
        placeTokenOnTheBoard(tokenName,x,y,direction,false,true);
    }

    @Given("an immutable {tokenName} token is preplaced on the board at \\({int}, {int}) facing {direction}")
    public void anImmutableTokenIsPreplacedOnTheBoardAtFacing(String tokenName,int x, int y, Direction direction) {
        placeTokenOnTheBoard(tokenName,x,y,direction,false,false);
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
            BoardEngine.moveToken(board, token, new Position(x, y));
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("I move the {token} token to \\({int}, {int})")
    public void iMoveTheTokenTo(Token token, int x, int y) {
        BoardEngine.moveToken(board, token, new Position(x, y));
    }

    @Then("the {token} token should be at \\({int}, {int})")
    public void theTokenShouldRemainAt(Token token, int x, int y) {
        Tile tokenTile = board.getTile(x, y);
        assertEquals(token,tokenTile.getToken(), "Token should be in position:"+ " (" + x + ", " + y + ")");
        assertEquals(token.getPosition(),new Position(x, y),"Token should not change position:" + " (" + x + ", " + y + ")");
    }

    @Given("I try to turn the {token} token to face {direction}")
    public void iTryToTurnTheTokenToFace(Token token, Direction direction) {
        try {
            BoardEngine.turnToken((MutableToken) token, direction);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("I turn the {token} token to face {direction}")
    public void iTurnTheTokenToFace(Token token, Direction direction) {
            BoardEngine.turnToken((MutableToken) token, direction);
    }

    @Then("the {token} token should face {direction}")
    public void theTokenShouldFace(Token token, Direction direction) {
        assertEquals(direction, ((ITurnableToken) token).getDirection(), "Token should not change direction");
    }

    public List<Token> getPreplacedTokensFromTable(DataTable table) {
        return table.asMaps(String.class, String.class)
                .stream()
                // keep only rows where preplaced == true
                .filter(row -> Boolean.parseBoolean(row.get("preplaced")))
                .map(row -> {
                    // Only consider turnable column if not immutable
                    Token preplacedToken;
                    Boolean turnable = Boolean.parseBoolean(row.get("turnable"));
                            switch (row.get("token").toLowerCase()) {
                                case "laser":
                                    preplacedToken = new LaserTokenBuilder().withMutability(false,turnable)
                                            .withPosition(Integer.parseInt(row.get("x")), Integer.parseInt(row.get("y")))
                                            .withDirection(Direction.valueOf(row.get("dir").toUpperCase())).build();
                                    return preplacedToken;
                                case "cell blocker":
                                    preplacedToken = new CellBlockerTokenBuilder()
                                            .withPosition(Integer.parseInt(row.get("x")), Integer.parseInt(row.get("y")))
                                            .build();
                                    return preplacedToken;
                                case "double mirror":
                                    preplacedToken = new DoubleMirrorTokenBuilder().withMutability(false,turnable)
                                            .withPosition(Integer.parseInt(row.get("x")), Integer.parseInt(row.get("y")))
                                            .withDirection(Direction.valueOf(row.get("dir").toUpperCase())).build();
                                    return preplacedToken;
                                case "target mirror":
                                    TargetMirrorTokenBuilder tempBuild = new TargetMirrorTokenBuilder().withMutability(false,turnable)
                                            .withPosition(Integer.parseInt(row.get("x")), Integer.parseInt(row.get("y")))
                                            .withDirection(Direction.valueOf(row.get("dir").toUpperCase()));
                                    if (row.containsKey("is required") && Boolean.parseBoolean(row.get("is required"))) {
                                        return tempBuild.withRequiredTarget().build();
                                    }
                                    return tempBuild.build();
                                case "beam splitter":
                                    preplacedToken = new BeamSplitterTokenBuilder().withMutability(false,turnable)
                                            .withPosition(Integer.parseInt(row.get("x")), Integer.parseInt(row.get("y")))
                                            .withDirection(Direction.valueOf(row.get("dir").toUpperCase())).build();
                                    return preplacedToken;
                                case "checkpoint":
                                    preplacedToken = new CheckpointTokenBuilder().withMutability(false,turnable)
                                            .withPosition(Integer.parseInt(row.get("x")), Integer.parseInt(row.get("y")))
                                            .withDirection(Direction.valueOf(row.get("dir").toUpperCase())).build();
                                    return preplacedToken;
                                default:
                                    throw new IllegalArgumentException("Unknown token type: " + row.get("tokenName"));
                            }

                        }
                ).toList();
    }

    public List<Token> getRequiredTokensFromTable(DataTable table) {
        return table.asMaps(String.class, String.class)
                .stream()
                // keep only rows where preplaced == false
                .filter(row -> !Boolean.parseBoolean(row.get("preplaced")))
                .map(row -> {
                            Token requiredToken;
                            Boolean turnable;
                            switch (row.get("token").toLowerCase()) {
                                case "laser":
                                    requiredToken = new LaserTokenBuilder().withMutability(true,true)
                                            .build();
                                    return requiredToken;
                                case "double mirror":
                                    requiredToken = new DoubleMirrorTokenBuilder().withMutability(true,true)
                                            .build();
                                    return requiredToken;
                                case "target mirror":
                                    TargetMirrorTokenBuilder tempBuild = new TargetMirrorTokenBuilder()
                                            .withMutability(true,true);
                                    if (row.containsKey("is required") && Boolean.parseBoolean(row.get("is required"))) {
                                        return tempBuild.withRequiredTarget().build();
                                    }
                                    return tempBuild.build();
                                case "beam splitter":
                                    requiredToken = new BeamSplitterTokenBuilder().withMutability(true,true).build();
                                    return requiredToken;
                                case "checkpoint":
                                    requiredToken = new CheckpointTokenBuilder().withMutability(true,true).build();
                                    return requiredToken;
                                default:
                                    throw new IllegalArgumentException("Invalid token type: " + row.get("tokenName"));
                            }

                        }
                ).toList();
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

    @Given("I place token {int} \\(from the required tokens) on the board at \\({int}, {int})")
    public void iPlaceTokenFromTheRequiredTokensOnTheBoardAt(int i, int x, int y) {
        token = level.getRequiredTokens().get(i);
        LevelEngine.placeRequiredToken(level, token, new Position(x, y));
        saveTokenAsType(token); // Save the token as its specific type for further checks
    }

    public void saveTokenAsType(Token token) {
        if (token instanceof LaserToken t) {
            laser = t;
        } else if (token instanceof CellBlockerToken t) {
            cellBlocker = t;
        } else if (token instanceof DoubleMirrorToken t) {
            doubleMirror = t;
        } else if (token instanceof TargetMirrorToken t) {
            targetMirror = t;
        } else if (token instanceof BeamSplitterToken t) {
            beamSplitter = t;
        } else if (token instanceof CheckpointToken t) {
            checkpoint = t;
        } else {
            throw new IllegalArgumentException("Unknown token type: " + token.getClass());
        }
    }

    @Then("the token on the board at \\({int}, {int}) should be a {tokenType} token")
    public void theTokenOnTheBoardAtShouldBeAToken(int x, int y, Class<? extends Token> tokenType) {
        Tile tile = board.getTile(x, y);
        // set token to the one on the board for further checks
        token = tile.getToken();
        assertEquals(tokenType,token.getClass(),
                "Token at (" + x + ", " + y + ") should be a Double Mirror token, but is: " + tile.getToken());
        // set token of specific type to the one on the board for further checks
        saveTokenAsType(token);
    }

    @And("the token should be turnable without direction")
    public void theTokenShouldBeTurnableWithoutDirection() {
            assertTrue(token.isTurnable(), "Token should be turnable, but is not");
            assertNull(((ITurnableToken) token).getDirection(),
                    "Token should not have a direction, but is: " + ((ITurnableToken) token).getDirection());
    }

    @And("the remaining number of required tokens to be placed should be {int}")
    public void theRemainingNumberOfRequiredTokensShouldBe(int i) {
        assertEquals(i, level.getRequiredTokens().size(),
                "Remaining required tokens to be placed should be " + i + ", but is: " + level.getRequiredTokens().size());
    }

    @And("the level is initialized with id {int}, required target number {int}, a board with width {int} and height {int}, and the following tokens:")
    public void theLevelIsInitializedWithIdRequiredTargetNumberABoardWithWidthAndHeightAndTheFollowingTokens(int id, int n, int width, int height, DataTable table) {
        level = new LevelBuilder(id)
                .withBoardDimensions(width, height)
                .withRequiredTargetNumber(n)
                .withPreplaced(getPreplacedTokensFromTable(table))
                .withRequired(getRequiredTokensFromTable(table))
                .build();
        board = level.getBoard();
    }

    @And("the level's tokens should be:")
    public void theLevelSTokensShouldBe(DataTable table) {
        List<? extends Class<? extends Token>> expectedTokenTypes = table.asMaps(String.class, String.class)
                .stream()
                .map(row -> {
                    Class<? extends Token> ttype = tokenType(row.get("token"));
                    return ttype;
                })
                .toList();

        List<? extends Class<? extends Token>> actualTokenTypes = level.getTokens()
                .stream()
                .map(tk -> {
                    Class<? extends Token> ttype = tk.getClass();
                    return ttype;
                })
                .toList();

        assertEquals(expectedTokenTypes, actualTokenTypes,
                "Level token types do not match expected token types");
    }



    @Then("the level should be incomplete")
    public void theLevelShouldBeIncomplete() {
        LevelEngine.updateAndCheckLevelCompletionState(level);
        assertFalse(level.isComplete(), "Level should not be complete, but is");
    }

    @Then("the level should be complete")
    public void theLevelShouldBeComplete() {
        LevelEngine.updateAndCheckLevelCompletionState(level);
        assertTrue(level.isComplete(), "Level should be complete, but is not");
    }

    @And("the number of targets hit by the beam path should be {int}")
    public void theNumberOfTargetsHitShouldBe(int n) {
        int actualHitCount = LaserEngine.getTargetHitNumber(actualBeamPath, level.getTokens());
        assertEquals(actualHitCount,n,
                "Number of targets hit should be " + n + ", but is: " + actualHitCount);
    }

    @And("the beam path should hit all the required targets")
    public void theBeamPathHitsAllTheRequiredTargets() {
        boolean allRequiredTargetsHit = LaserEngine.areAllRequiredTargetsHit(actualBeamPath, level.getTokens());
        assertTrue(allRequiredTargetsHit, "Beam path does not hit all required targets");
    }


    @And("the beam path should touch every touch-required token given by the level")
    public void theBeamPathTouchesEveryTokenOnTheBoardExceptTheOnesNotTouchRequired() {
        boolean allTouchRequiredTokensTouched = LaserEngine.areAllTouchRequiredTokensTouched(actualBeamPath, level.getTokens());
        assertTrue(allTouchRequiredTokensTouched, "Beam path does not touch all touch-required tokens");
    }

    @And("the beam path should pass through all checkpoints")
    public void theBeamPathShouldPassThroughAllCheckpoints() {
        boolean allCheckpointsPenetrated = LaserEngine.areAllCheckpointsPenetrated(actualBeamPath, level.getTokens());
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
        LevelEngine.triggerLaserToken(level, true);
        laser = level.getActiveLaser().get();
    }

    @And("the level's laser forms a beam path")
    public void theLevelsLaserFormsABeamPath() {
        actualBeamPath = LevelEngine.fireLaserToken(level);
    }
}
