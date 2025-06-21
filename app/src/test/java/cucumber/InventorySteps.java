package cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.*;
import model.domain.board.Board;
import model.domain.board.Inventory;
import model.domain.board.Position;
import model.domain.board.Tile;
import model.domain.board.builder.BoardBuilder;
import model.domain.engine.BoardEngine;
import model.domain.level.builder.LevelBuilder;
import model.domain.token.base.Token;
import model.domain.token.builder.base.TokenBuilder;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InventorySteps extends BaseSteps {

    @Given("a level is initialized with:")
    public void aLevelIsInitializedWith(DataTable table) {

        List<Token> tokens = table.asMaps(String.class, String.class)
                .stream()
                .flatMap(row -> {
                    int count = Integer.parseInt(row.get("count"));
                    String tokenName = row.get("token");
                    return java.util.stream.IntStream.range(0, count)
                            .mapToObj(i -> buildToken(tokenName, null, null, null, true, true));
                })
                .toList();

        inventory = new Inventory(tokens);

        level = new LevelBuilder(0)
                .withTokens(tokens)
                .withRequiredTargetNumber(1)
                .build();

        board = level.getBoard();
    }

    @Then("the inventory should contain {int} tokens total")
    public void theInventoryShouldContainTokensTotal(int expectedCount) {
        assertEquals(expectedCount, inventory.getTotalTokenCount(),
                "Inventory token count mismatch");
    }

    @When("I get a {string} from the inventory")
    public void iGetATokenFromTheInventory(String tokenName) {
        assertNotNull(inventory, "Inventory is not initialized.");
        token = inventory.getTokenByType(tokenName);
    }

    @Then("it should not be null")
    public void itShouldNotBeNull() {
        assertNotNull(token, "Expected a token but got null");
    }


    @When("I place a {string} from inventory at board position \\({int}, {int})")
    public void iPlaceATokenFromInventoryAtBoardPosition(String tokenName, int x, int y) {
        token = inventory.getTokenByType(tokenName);
        assertNotNull(token, "No " + tokenName + " found in inventory");
        BoardEngine.placeToken(board, token, new Position(x, y));
        inventory.removeToken(token);
    }
    @Given("a {int} by {int} board")
    public void aByBoard(int x, int y) {
        board = new BoardBuilder()
                .withDimensions(x, y)
                .build();

    }
    @Then("the board should have a {string} at \\({int}, {int})")
    public void theBoardShouldHaveATokenAt(String tokenName, int x, int y) {
        Tile tile = board.getTile(x, y);
        assertNotNull(tile.getToken(), "No token found at board position");
        Class<? extends Token> expectedType = getTokenType(tokenName);
        assertTrue(expectedType.isInstance(tile.getToken()),
                "Expected " + expectedType.getSimpleName() + " but found: " + tile.getToken().getClass().getSimpleName());
    }

    @Then("the inventory should have {int} {string} tokens remaining")
    public void theInventoryShouldHaveTokensRemaining(int expectedCount, String tokenName) {
        int actual = inventory.countTokensByType(tokenName);
        assertEquals(expectedCount, actual,
                "Expected " + expectedCount + " " + tokenName + " tokens, but found " + actual);
    }


}
