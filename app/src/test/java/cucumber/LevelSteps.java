package cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.domain.board.Position;
import model.domain.board.PositionDirection;
import model.domain.board.Tile;
import model.domain.engine.LaserEngine;
import model.domain.engine.LevelEngine;
import model.domain.level.builder.LevelBuilder;
import model.domain.token.base.ITargetToken;
import model.domain.token.base.ITurnableToken;
import model.domain.token.base.Token;
import model.domain.token.impl.*;

import java.util.List;
import java.util.Map;

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
        board = new model.domain.board.builder.BoardBuilder()
                .withDimensions(width, height)
                .build();

        level = new LevelBuilder(1)
                .withBoard(board)
                .withRequiredTargetNumber(0)
                .build();
    }

    @And("the inventory contains:")
    public void theInventoryContains(DataTable table) {
        List<Token> tokens = table.asMaps(String.class, String.class)
                .stream()
                .flatMap(row -> {
                    int count = Integer.parseInt(row.get("count"));
                    String tokenName = row.get("token");
                    return java.util.stream.IntStream.range(0, count)
                            .mapToObj(i -> buildToken(tokenName, null, null, null, true, true));
                })
                .toList();

        inventory = new model.domain.board.Inventory(tokens);

        level = new LevelBuilder(level.getId())
                .withBoard(board)
                .withTokens(tokens)
                .withRequiredTargetNumber(level.getRequiredTargetNumber())
                .build();
    }

    @Then("the level should have a board of width {int} and height {int}")
    public void theLevelShouldHaveABoardOfWidthAndHeight(int width, int height) {
        assertNotNull(level.getBoard());
        assertEquals(width, level.getBoard().getWidth());
        assertEquals(height, level.getBoard().getHeight());
    }

    @And("the inventory should have {int} TargetMirrorTokens")
    public void theInventoryShouldHaveTargetMirrorTokens(int count) {
        int actual = inventory.countTokensByType("TargetMirrorToken");
        assertEquals(count, actual,
                "Expected " + count + " TargetMirrorTokens, but found " + actual);
    }

    @Given("the board contains:")
    public void theBoardContains(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            String tokenType = row.get("token");
            int x = Integer.parseInt(row.get("x"));
            int y = Integer.parseInt(row.get("y"));
            String dir = row.get("dir");

            Token token = buildToken(tokenType, null, null,
                    dir == null || dir.isEmpty() ? null : direction(dir), true, true);

            Position pos = new Position(x, y);
            model.domain.engine.BoardEngine.placeToken(board, token, pos);

            saveTokenAsType(token);
        }
    }

    @When("I place a TargetMirrorToken at board position \\({int}, {int})")
    public void iPlaceATargetMirrorTokenAtBoardPosition(int x, int y) {
        token = inventory.getTokenByType("TargetMirrorToken");
        assertNotNull(token, "No TargetMirrorToken available in inventory");
        Position pos = new Position(x, y);
        model.domain.engine.BoardEngine.placeToken(board, token, pos);
        inventory.removeToken(token);
    }

    @Then("the target at \\({int}, {int}) should be activated")
    public void theTargetAtShouldBeActivated(int x, int y) {
        Tile tile = board.getTile(x, y);
        Token token = tile.getToken();

        assertNotNull(token, "No token found at (" + x + "," + y + ")");
        assertTrue(token instanceof ITargetToken, "Expected a target token at (" + x + "," + y + ")");

        // Get beam path using LevelEngine (safe wrapper)
        List<PositionDirection> beamPath = LevelEngine.fireLaserToken(level);

        boolean activated = beamPath.stream()
                .filter(posDir -> posDir.getPosition().getX() == x && posDir.getPosition().getY() == y)
                .anyMatch(posDir -> ((ITargetToken) token).isHit(posDir));

        assertTrue(activated, "Target at (" + x + "," + y + ") was not activated");
    }
}
