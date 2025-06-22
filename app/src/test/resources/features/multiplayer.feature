Feature: Multiplayer Level
  As a player, when entering a Multiplayer Level,
  I want a timed and turn-based gameplay
  to be played on the same computer.

  Background:
    # We always start with a fresh 2 player multiplayer level in these scenarios
    Given a multiplayer level is initialized with 3 players
    And the current time in milliseconds is 0
    Then the current player should not be set yet

  Scenario: Starting and ending a turn
    # Starting a turn
    When the next player starts their turn
    # Dev: The player number is subtracted in steps by 1 to match the index
    Then the current player should be player 1
    And the multiplayer turn should be active
    And their start timestamp should be 0 milliseconds

    # Before ending a turn
    When the current time in milliseconds is 5000
    Then their end timestamp should not be set yet
    But their time so far should be 5000 milliseconds

    # Ending a turn
    When the current time in milliseconds is 10000
    And the player ends their turn
    Then the multiplayer turn should not be active
    And their end timestamp should be 10000 milliseconds
    And the current player should be player 1

  Scenario: Starting too many turns
    # Player 1 starts their turn
    When the next player starts their turn
    And the player ends their turn
    Then the current player should be player 1

    # Player 2 starts their turn
    When the next player starts their turn
    And the player ends their turn
    Then the current player should be player 2

    # Player 3 starts their turn
    When the next player starts their turn
    And the player ends their turn
    Then the current player should be player 3

    # An imaginary third player tries to start their turn
    # but fails to do so because there are only 2 players
    When a next player tries to start their turn
    Then a multiplayer error should occur


  Scenario: Completion times after all turns
    # Player 1 starts and finishes their turn while completing their level
    When the next player starts their turn
    And the current time in milliseconds is 70000
    And the player completes their level
    And the player ends their turn

   # Player 2 starts and finishes their turn but doesn't complete their level
    When the current time in milliseconds is 90000
    And the next player starts their turn
    And the current time in milliseconds is 180000
    And the player does not complete their level
    And the player ends their turn

    # Player 3 starts and finishes their turn while completing their level
    When the current time in milliseconds is 200000
    And the next player starts their turn
    And the current time in milliseconds is 250000
    And the player completes their level
    And the player ends their turn

    Then all turns should be played
    And the player score times should be:
    | place | player | time  |
    | 1     | 3      | 50000 |
    | 2     | 1      | 70000 |
    | 3     | 2      |       |




