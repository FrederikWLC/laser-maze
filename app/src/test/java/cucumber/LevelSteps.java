package cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.domain.board.Direction;
import model.domain.board.Position;
import model.domain.board.PositionTurn;
import model.domain.board.Tile;
import model.domain.engine.LevelEngine;
import model.domain.level.Level;
import model.domain.level.builder.LevelBuilder;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.MutableToken;
import model.domain.token.base.MutableTwinToken;
import model.domain.token.base.Token;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class LevelSteps extends BaseSteps {

    LevelEngine levelEngine;

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

    @And("the level is initialized with id {int}, required target number {int}, a board with width {int} and height {int}, and the following tokens:")
    public void theLevelIsInitializedWithIdRequiredTargetNumberABoardWithWidthAndHeightAndTheFollowingTokens(
            int id, int reqTargets, int w, int h, DataTable table) {
            Level level = new LevelBuilder(id)
                    .withBoardDimensions(w, h)
                    .withRequiredTargetNumber(reqTargets)
                    .withTokens(getTokensFromTable(table))
                    .build();
        levelEngine = new LevelEngine(level);
    }

    @Then("the level's id should be {int}")
    public void theLevelsIdShouldBe(int id) {
        assertEquals(id, levelEngine.getLevel().getId(),
                "Expected level id: " + id + ", but was: " + levelEngine.getLevel().getId());
    }

    @And("the level's required target number should be {int}")
    public void theLevelsRequiredTargetNumberShouldBe(int n) {
        assertEquals(n, levelEngine.getLevel().getRequiredTargetNumber(),
                "Expected required target number: " + n + ", but was: " + levelEngine.getLevel().getRequiredTargetNumber());
    }

    @And("the level's board should have width {int} and height {int}")
    public void theLevelsBoardShouldHaveWidthAndHeight(int width, int height) {
        assertEquals(width, levelEngine.getLevel().getBoard().getWidth(),
                "Expected board width: " + width + ", but was: " + levelEngine.getLevel().getBoard().getWidth());
        assertEquals(height, levelEngine.getLevel().getBoard().getHeight(),
                "Expected board height: " + height + ", but was: " + levelEngine.getLevel().getBoard().getHeight());
    }

    @And("the level's tokens should be:")
    public void theLevelSTokensShouldBe(DataTable table) {
        List<Class<? extends Token>> expected = table.asMaps(String.class, String.class).stream()
                .map(row -> getTokenType(row.get("token")))
                .collect(java.util.stream.Collectors.toList());

        List<Class<? extends Token>> actual = levelEngine.getLevel().getTokens().stream()
                .map(Token::getClass)
                .collect(java.util.stream.Collectors.toList());

        assertEquals(expected, actual, "Level token types do not match expected");
    }

    @Given("I place token {int} \\(from the required tokens) on the level's board at \\({int}, {int})")
    public void iPlaceTokenFromTheRequiredTokensOnTheBoardAt(int idx, int x, int y) {
        token = levelEngine.getLevel().getRequiredTokens().get(idx);
        boardEngine.placeToken(levelEngine.getLevel().getBoard(), token, new Position(x, y));
        saveTokenAsType(token);
    }

    @And("the remaining number of required tokens to be placed should be {int}")
    public void theRemainingNumberOfRequiredTokensShouldBe(int count) {
        assertEquals(count, levelEngine.getLevel().getRequiredTokens().size(),
                "Remaining required tokens to be placed should be " + count);
    }
    @Then("the level should be incomplete")
    public void theLevelShouldBeIncomplete() {
        levelEngine.updateAndCheckLevelCompletionState();
        assertFalse(levelEngine.getLevel().isComplete(), "Level should not be complete, but is");
    }

    @Then("the level should be complete")
    public void theLevelShouldBeComplete() {
        levelEngine.updateAndCheckLevelCompletionState();
        assertTrue(levelEngine.getLevel().isComplete(), "Level should be complete, but is not");
    }

    @Then("all tokens should be placed")
    public void allTokensShouldBePlaced() {
        assertTrue(levelEngine.getLevel().areAllTokensPlaced(), "Expected all tokens to be placed");
    }

    @Then("not all tokens should be placed")
    public void notAllTokensShouldBePlaced() {
        assertFalse(levelEngine.getLevel().areAllTokensPlaced(), "Expected not all tokens to be placed");
    }

    @When("I try to trigger the level's laser")
    public void iTryToTriggerTheLevelsLaser() {
        try {
            levelEngine.getLevel().getLaserToken().trigger(true);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("I try to fire the level's laser")
    public void iTryToFireTheLevelsLaser() {
        try {
            levelEngine.getLaserEngine().fire();
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the level's current target number should be {int}")
    public void theLevelsCurrentTargetNumberShouldBe(int n) {
        assertEquals(n, levelEngine.getLevel().getCurrentTargetNumber(),
                "Expected current target number to be " + n);
    }

    @When("I set the level's inventory to the current board")
    public void iSetTheLevelsInventoryToTheCurrentBoard() {
        // This calls Level.setInventory(...)
        levelEngine.getLevel().setInventory(board);
    }

    @Then("the level's inventory should be the current board")
    public void theLevelsInventoryShouldBeTheCurrentBoard() {
        // getInventory() must return exactly what we set
        assertSame(board, levelEngine.getLevel().getInventory(),
                "Expected level.getInventory() to return the board we just set");
    }

    @And("the level's laser forms a beam path")
    public void theLevelsLaserFormsABeamPath() {
        actualBeamPath = levelEngine.getLaserEngine().getLastBeamPath();
    }

    @And("the beam path should hit all the required targets")
    public void theBeamPathHitsAllTheRequiredTargets() {
        boolean allRequiredTargetsHit = levelEngine.areAllRequiredTargetsHit();
        assertTrue(allRequiredTargetsHit, "Beam path does not hit all required targets");
    }

    @And("the beam path should touch every touch-required token given by the level")
    public void theBeamPathTouchesEveryTokenOnTheBoardExceptTheOnesNotTouchRequired() {
        boolean areAllTouchRequiredTokensHit = levelEngine.areAllTouchRequiredTokensHit();
        assertTrue(areAllTouchRequiredTokensHit, "Beam path does not touch all touch-required tokens");
    }

    @And("the beam path should pass through all checkpoints")
    public void theBeamPathShouldPassThroughAllCheckpoints() {
        boolean allCheckpointsPenetrated = levelEngine.areAllCheckpointsChecked();
        assertTrue(allCheckpointsPenetrated, "Beam path does not pass through all checkpoints");
    }

    @And("all turnable tokens should have a direction")
    public void allTurnableTokensShouldHaveADirection() {
        for (Token token : levelEngine.getLevel().getTokens()) {
            if (token instanceof ITurnableToken turnableToken) {
                assertNotNull(turnableToken.getDirection(),
                        "Token " + token.getClass().getSimpleName() + " should have a direction, but does not");
            }
        }
    }

    @And("all tokens required to be placed should be placed on the board")
    public void allTokensRequiredToBePlacedShouldBePlacedOnTheBoard() {
        for (Token token : levelEngine.getLevel().getRequiredTokens()) {
            assertTrue(token.isPlaced(),
                    "Token " + token.getClass().getSimpleName() + " should be placed on the board, but is not");
        }
    }

    @Given("I fire the level's laser")
    public void iFireTheLevelsLaser() {
        levelEngine.getLevel().getLaserToken().trigger(true);
        levelEngine.getLaserEngine().fire();
    }

    @And("the number of targets hit by the beam path should be {int}")
    public void theNumberOfTargetsHitShouldBe(int n) {
        int actualHitCount = levelEngine.getLaserEngine().getTargetLitNumber();
        assertEquals(n,actualHitCount,
                "Number of targets hit should be " + n + ", but is: " + actualHitCount);
    }

    @And("the first pair of the level's {tokenType} tokens should be each other's twins")
    public void theFirstPairOfTheLevelsTokensShouldBeEachOthersTwins(Class<? extends MutableTwinToken> tokenType) {
        List<MutableTwinToken> pair = getTwinPairOfType(tokenType, levelEngine.getLevel().getTokens());
        MutableTwinToken first  = pair.get(0);
        MutableTwinToken second = pair.get(1);
        assertEquals(second, first.getTwin(),
                "First " + tokenType.getSimpleName() + "'s twin should be the second token of same type, but is: " + first.getTwin());
        assertEquals(first, second.getTwin(),
                "Second " + tokenType.getSimpleName() +"token's twin should be the first token of same type, but is: " + second.getTwin());
    }

    @Then("the token on the level's board at \\({int}, {int}) should be a {tokenType} token")
    public void theTokenOnTheBoardAtShouldBeAToken(int x, int y, Class<? extends Token> type) {
        Tile t = levelEngine.getLevel().getBoard().getTile(x, y);
        token = t.getToken();
        assertEquals(type, token.getClass(),
                "Token at (" + x + "," + y + ") should be " + type.getSimpleName());
        saveTokenAsType(token);
    }

    @And("the given token in the level should be turnable without direction")
    public void theTokenShouldBeTurnableWithoutDirection() {
        assertTrue(token.isTurnable(), "Token should be turnable");
        assertNull(((ITurnableToken) token).getDirection(),
                "Token should not have a direction");
    }

    @Given("I turn the given token in the level to face {direction}")
    public void iTurnTheTokenToFace(Direction direction) {
        boardEngine.turnToken((MutableToken) token, direction);
    }

    @Then("the Portal token's blue opening side should face {direction}")
    public void thePortalTokenSBlueOpeningSideShouldFaceUp(Direction direction) {
        assertEquals(direction, portal.getBluePortalDirection());
    }

    @Then("the Portal token's red opening side should face {direction}")
    public void thePortalTokenSRedOpeningSideShouldFaceUp(Direction direction) {
        assertEquals(direction, portal.getRedPortalDirection());
    }

    @Then("the level's laser beam should pass through the following position directions:")
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

}
