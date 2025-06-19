package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.domain.board.Position;
import model.domain.board.Tile;
import model.domain.engine.BoardEngine;
import model.domain.token.builder.impl.TargetMirrorTokenBuilder;
import model.domain.token.impl.TargetMirrorToken;

import static org.junit.jupiter.api.Assertions.*;

public class TileContainerSteps extends BaseSteps {
    @Given("a {int} by {int} inventory is initialized")
    public void aByInventoryIsInitialized(int width, int height) {
        board = new model.domain.board.Board(width, height);
    }

    @When("I place a TargetMirrorToken at position \\({int}, {int})")
    public void iPlaceATargetMirrorTokenAtPosition(int x, int y) {
        targetMirror = new TargetMirrorTokenBuilder().withPosition(x, y).build();
        BoardEngine.placeToken(board, targetMirror, new Position(x, y));
    }

    @Then("the tile at \\({int}, {int}) should contain a TargetMirrorToken")
    public void theTileAtShouldContainATargetMirrorToken(int x, int y) {
        Tile tile = board.getTile(x, y);
        assertNotNull(tile.getToken(), "Tile should contain a token but is empty.");
        assertTrue(tile.getToken() instanceof TargetMirrorToken,
                "Expected a TargetMirrorToken but got " + tile.getToken().getClass().getSimpleName());
    }

}
