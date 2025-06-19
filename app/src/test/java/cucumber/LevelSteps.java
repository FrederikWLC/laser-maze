package cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.domain.board.Position;
import model.domain.board.Tile;
import model.domain.engine.LevelEngine;
import model.domain.level.builder.LevelBuilder;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.Token;
import model.domain.token.impl.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LevelSteps extends BaseSteps {

    @Then("the level's id should be {int}")
    public void theLevelsIdShouldBe(int id) {
        assertEquals(id, level.getId(), "Expected level id: " + id + ", but was: " + level.getId());
    }

    @And("the level's required target number should be {int}")
    public void theLevelsRequiredTargetNumberShouldBe(int n) {
        assertEquals(n, level.getRequiredTargetNumber(), "Expected required target number: " + n + ", but was: " + level.getRequiredTargetNumber());
    }

    @And("the level's board should have width {int} and height {int}")
    public void theLevelsBoardShouldHaveWidthAndHeight(int width, int height) {
        assertEquals(width, level.getBoard().getWidth());
        assertEquals(height, level.getBoard().getHeight());
    }

    @Given("I place token {int} \\(from the required tokens) on the board at \\({int}, {int})")
    public void iPlaceTokenFromTheRequiredTokensOnTheBoardAt(int i, int x, int y) {
        token = level.getRequiredTokens().get(i);
        LevelEngine.placeRequiredToken(level, token, new Position(x, y));
        saveTokenAsType(token);
    }

    @Then("the token on the board at \\({int}, {int}) should be a {tokenType} token")
    public void theTokenOnTheBoardAtShouldBeAToken(int x, int y, Class<? extends Token> tokenType) {
        Tile tile = board.getTile(x, y);
        token = tile.getToken();
        assertEquals(tokenType, token.getClass());
        saveTokenAsType(token);
    }

    @And("the token should be turnable without direction")
    public void theTokenShouldBeTurnableWithoutDirection() {
        assertTrue(token.isTurnable());
        assertNull(((ITurnableToken) token).getDirection());
    }

    @And("the remaining number of required tokens to be placed should be {int}")
    public void theRemainingNumberOfRequiredTokensShouldBe(int i) {
        assertEquals(i, level.getRequiredTokens().size());
    }

    @And("the level is initialized with id {int}, required target number {int}, a board with width {int} and height {int}, and the following tokens:")
    public void theLevelIsInitializedWithIdRequiredTargetNumberABoardWithWidthAndHeightAndTheFollowingTokens(int id, int n, int width, int height, DataTable table) {
        level = new LevelBuilder(id)
                .withBoardDimensions(width, height)
                .withRequiredTargetNumber(n)
                .withTokens(getTokensFromTable(table))
                .build();
        board = level.getBoard();
    }

    @And("the level's tokens should be:")
    public void theLevelSTokensShouldBe(DataTable table) {
        List<? extends Class<? extends Token>> expectedTokenTypes = table.asMaps(String.class, String.class)
                .stream()
                .map(row -> tokenType(row.get("token")))
                .toList();

        List<? extends Class<? extends Token>> actualTokenTypes = level.getTokens()
                .stream()
                .map(Token::getClass)
                .toList();

        assertEquals(expectedTokenTypes, actualTokenTypes);
    }

    @Then("the level should be incomplete")
    public void theLevelShouldBeIncomplete() {
        LevelEngine.updateAndCheckLevelCompletionState(level);
        assertFalse(level.isComplete());
    }

    @Then("the level should be complete")
    public void theLevelShouldBeComplete() {
        LevelEngine.updateAndCheckLevelCompletionState(level);
        assertTrue(level.isComplete());
    }

    @Given("a level is created with board size {int} by {int}")
    public void aLevelIsCreatedWithBoardSizeBy(int width, int height) {
        // TODO: Implement this
    }

    @And("the inventory contains:")
    public void theInventoryContains() {
        // TODO: Implement this
    }

    @Then("the level should have a board of width {int} and height {int}")
    public void theLevelShouldHaveABoardOfWidthAndHeight(int width, int height) {
        // TODO: Implement this
    }

    @And("the inventory should have {int} TargetMirrorTokens")
    public void theInventoryShouldHaveTargetMirrorTokens(int count) {
        // TODO: Implement this
    }

    @Given("the board contains:")
    public void theBoardContains() {
        // TODO: Implement this
    }

    @When("I place a TargetMirrorToken at board position \\({int}, {int})")
    public void iPlaceATargetMirrorTokenAtBoardPosition(int x, int y) {
        // TODO: Implement this
    }
}
