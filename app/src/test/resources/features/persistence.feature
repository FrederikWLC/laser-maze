Feature: Save management and restart
  As a player
  In single-player mode
  I want the game to
  • be able to list every available level,
  • store my current state when I save, and
  • restore the original level configuration when I restart,

  Background:
    Given a default level file with id 1 exists
    And no saved level exists for id 1

  Scenario: Loading a level with no save falls back to default
    When the player loads level 1
    Then the level should be equivalent to the one loaded from the default source
    And the completion state should be false

  Scenario: Saving a modified level
    Given the player loads level 1
    And the player marks the level as complete
    When the player saves the level
    And the player loads it again
    Then the level should not be equivalent to the one loaded from the default source
    And the completion state should be true

  Scenario: Restarting a saved level resets it to default
    Given the player loads level 1
    And the player marks the level as complete
    When the player saves the level
    And the player restarts the level
    Then the level should be equivalent to the one loaded from the default source
    And the completion state should be false

  Scenario: getAllAvailableLevelIds() on empty folder
    Given the resource folder has no JSON files
    When I retrieve all available level IDs
    Then the list of available level IDs should be empty

  Scenario: getAllAvailableLevelIds() returns parsed IDs
    Given the resource folder contains files:
      | filename |
      | 5.json   |
      | abc.txt  |
      | 12.json  |
      | 007.json |
    When I retrieve all available level IDs
    Then the list of available level IDs should be:
      | id |
      | 5  |
      | 7  |
      | 12 |