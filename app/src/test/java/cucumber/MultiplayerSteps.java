package cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.*;
import junit.time.test.TestTimeHelper;
import model.domain.engine.MultiplayerEngine;
import model.domain.level.Level;
import model.domain.level.builder.LevelBuilder;
import model.domain.multiplayer.Multiplayer;
import model.domain.multiplayer.PlayerScore;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplayerSteps {
    MultiplayerEngine multiplayerEngine = new MultiplayerEngine();
    Multiplayer multiplayer;
    TestTimeHelper timeHelper = new TestTimeHelper();
    Exception lastException = null;

    @ParameterType("[1-9]\\d*") // Matches integers from 1 to 9 followed by any number of digits
    public int index(String i) {
        return Integer.parseInt(i)-1; // Adjusting for 0-based index
    }

    @Given("a multiplayer error should occur")
    public void aMultiplayerErrorShouldOccur() {
        assertNotNull(lastException);
        assertInstanceOf(Exception.class, lastException);
    }

    @Given("the current time in milliseconds is {long}")
    public void theCurrentTimeIs(long millis) {
        this.timeHelper.setNowMillis(millis);
    }

    @And("a multiplayer level is initialized with {int} players")
    public void aMultiplayerLevelIsInitializedWithPlayers(int n) {
        Level level = new LevelBuilder(1).build();
        multiplayer = new Multiplayer(level, n);
    }

    @When("the next player starts their turn")
    public void theCurrentPlayerStartsTheirTurn() {
        multiplayerEngine.startTurn(multiplayer,timeHelper.nowMillis());
    }

    @And("the player completes their level")
    public void theCurrentPlayerCompletesTheirLevel() {
        multiplayer.getCurrentLevel().setComplete(true);
    }

    @And("the player does not complete their level")
     public void theCurrentPlayerDoesNotCompleteTheirLevel() {
        multiplayer.getCurrentLevel().setComplete(false);
    }

    @When("the player ends their turn")
    public void theCurrentPlayerEndsTheirTurn() {
        multiplayerEngine.endTurn(multiplayer,timeHelper.nowMillis());
    }


    @Then("all turns should be played")
    public void allTurnsShouldBePlayed() {
        assertTrue(multiplayer.allTurnsPlayed(),
                "Not all turns have been played. Current player index: " + multiplayer.getCurrentPlayerIndex());
    }

    @And("the player score times should be:")
    public void thePlayerScoreTimesShouldBe(DataTable dataTable) {
        List<PlayerScore> expectedScoreTimes = dataTable.asMaps().stream()
                .map(row -> {
                    int playerNumber = Integer.parseInt(row.get("player"));
                    Integer millis = row.get("time") != null ? Integer.parseInt(row.get("time")) : null;
                    return new PlayerScore(playerNumber, millis);
                })
                .toList();
        List<PlayerScore> actualScoreTimes = multiplayer.getSortedPlayerScoreTimes();
        assertEquals(expectedScoreTimes, actualScoreTimes, "Player scores do not match");
    }

    @Then("the current player should be player {index}")
    public void theCurrentPlayerIndexShouldBe(int expectedIndex) {
        assertEquals(expectedIndex, multiplayer.getCurrentPlayerIndex(),
                "Current player index does not match expected value");
    }

    @Then("the multiplayer turn should be active")
    public void theMultiplayerTurnShouldBeActive() {
        assertTrue(multiplayer.isTurnActive(), "Multiplayer turn should be active");
    }

    @Then("the multiplayer turn should not be active")
    public void theMultiplayerTurnShouldNotBeActive() {
        assertFalse(multiplayer.isTurnActive(), "Multiplayer turn should not be active");
    }

    @Then("their start timestamp should be {int} milliseconds")
    public void theirStartTimestampShouldBeMilliseconds(int millis) {
        assertEquals(millis, multiplayer.getPlayerStartStamps()[multiplayer.getCurrentPlayerIndex()],
                "Start time does not match expected value");
    }

    @Then("their end timestamp should be {int} milliseconds")
    public void theirEndTimestampShouldBeMilliseconds(int millis) {
        assertEquals(millis, multiplayer.getPlayerEndStamps()[multiplayer.getCurrentPlayerIndex()],
                "Start time does not match expected value");
    }

    @Then("the current player should not be set yet")
    public void theCurrentPlayerIsNotSetYet() {
        assertNull(multiplayer.getCurrentPlayerIndex(), "Current player index should not be set yet");
    }

    @Then("their end timestamp should not be set yet")
    public void theirEndTimestampShouldNotBeSetYet() {
        assertNull(multiplayer.getPlayerEndStamps()[multiplayer.getCurrentPlayerIndex()],
                "End timestamp should not be set yet");
    }

    @But("their time so far should be {int} milliseconds")
    public void theirTimeSoFarShouldBeMilliseconds(int expectedTimeSoFar) {
        Integer actualTimeSoFar = multiplayer.getPlayerTimeByEndStamp(multiplayer.getCurrentPlayerIndex(),timeHelper.nowMillis());
        assertEquals(expectedTimeSoFar, actualTimeSoFar.intValue(), "Player time does not match expected value");
    }

    @When("a next player tries to start their turn")
    public void aNextPlayerTriesToStartTheirTurn() {
        try {
            multiplayerEngine.startTurn(multiplayer, timeHelper.nowMillis());
        } catch (Exception e) {
            lastException = e; // Capture the exception for verification
        }
    }
}
