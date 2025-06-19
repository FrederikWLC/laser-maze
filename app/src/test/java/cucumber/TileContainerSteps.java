package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TileContainerSteps {
    @Given("a {int} by {int} inventory is initialized")
    public void aByInventoryIsInitialized(int arg0, int arg1) {

    }

    @When("I place a TargetMirrorToken at position \\({int}, {int})")
    public void iPlaceATargetMirrorTokenAtPosition(int arg0, int arg1) {
    }

    @Then("the tile at \\({int}, {int}) should contain a TargetMirrorToken")
    public void theTileAtShouldContainATargetMirrorToken(int arg0, int arg1) {
    }
}
