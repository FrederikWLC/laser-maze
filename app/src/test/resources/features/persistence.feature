Feature: Save management and restart
  As a player
  In single-player mode
  I want the game to
  • be able to list every available save,
  • store my current state when I save, and
  • restore the original level configuration when I restart,

  Background:
    Given saves exist for the following level ids:
      | id |
      | 4  |
      | 9  |
      | 31 |
    Then saves should exist for the following level ids:
      | id |
      | 4  |
      | 9  |
      | 31 |

 # An error should occur if the player tries to load a level without existing save
  Scenario: Loading a level without existing save
    Given I try to load a level with id 5
    Then an error should occur

  Scenario: Load a level with existing save
    Given I load a level with id 4
    Then the level loaded should be with id 4

  # ------------------------------------------------------------
  # Saving current progress
  # ------------------------------------------------------------
  @save-game
  Scenario: Storing the current game state
    Given the map currently contains 12 placed tokens
    And the laser has already hit 2 targets
    When the player chooses "Save" into slot "C"
    Then a new save file for slot "C" should be created
    And the file must contain the exact board state at the time of saving
    And slot "C" appears in the "Load Game" menu

  # ------------------------------------------------------------
  # Restarting a level from scratch
  # ------------------------------------------------------------
  @restart-level
  Scenario: Restoring the original map layout on restart
    Given the player has modified the board by adding 5 tokens
    And at least one save exists for this level
    When the player selects "Restart Level"
    Then the game clears all player-placed tokens
    And the map layout matches the original design of "Laser Maze"
    And move count is reset to zero
    And no saved files are deleted or altered
