package cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InventorySteps extends BaseSteps{
    @Given("a level with an inventory containing:")
    public void aLevelWithAnInventoryContaining() {
    }

    @Then("the inventory should contain {int} tokens total")
    public void theInventoryShouldContainTokensTotal(int arg0) {
    }

    @When("I get a TargetMirrorToken from the inventory")
    public void iGetATargetMirrorTokenFromTheInventory() {
    }

    @Then("it should not be null")
    public void itShouldNotBeNull() {
    }

    @Given("a {int} by {int} board")
    public void aByBoard(int arg0, int arg1) {
    }

    @When("I place a TargetMirrorToken from inventory at board position \\({int}, {int})")
    public void iPlaceATargetMirrorTokenFromInventoryAtBoardPosition(int arg0, int arg1) {
    }

    @Then("the board should have a TargetMirrorToken at \\({int}, {int})")
    public void theBoardShouldHaveATargetMirrorTokenAt(int arg0, int arg1) {
    }

    @And("the inventory should have {int} TargetMirrorTokens remaining")
    public void theInventoryShouldHaveTargetMirrorTokensRemaining(int arg0) {
    }
}
