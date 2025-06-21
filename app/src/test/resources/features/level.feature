Feature: Level
  As a player in the process of completing a level,
  I want to interact with preplaced and required tokens.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    # If no position, then token is not preplaced
    And the level is initialized with id 69, required target number 3, a board with width 5 and height 5, and the following tokens:
      | token           | x  | y  | dir   | turnable | movable | is required |
      | Target Mirror   | 4  | 0  | LEFT  | false    | false   | true        |
      | Checkpoint      | 2  | 2  | DOWN  | false    | false   |             |
      | Double Mirror   |    |    |       | true     | true    |             |
      | Beam Splitter   |    |    |       | true     | true    |             |
    Then the level's id should be 69
    And the level's required target number should be 3
    And the level's board should have width 5 and height 5
    And the level's tokens should be:
      | token           |
      | Target Mirror   |
      | Checkpoint      |
      | Double Mirror   |
      | Beam Splitter   |

    Scenario: I place a token (from the required tokens) on the board
      Given I place token 0 (from the required tokens) on the board at (0, 0)
      Then the token on the board at (0, 0) should be a Double Mirror token
      And the token should be turnable without direction
      And the remaining number of required tokens to be placed should be 1

  Scenario: Place all tokens and verify all tokens are placed
    Given I place token 0 (from the required tokens) on the board at (0, 0)
    And I place token 0 (from the required tokens) on the board at (1, 1)
    Then all tokens should be placed

  Scenario: Triggering level's laser with no laser present throws
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And the level is initialized with id 10, required target number 0, a board with width 5 and height 5, and the following tokens:
      | token | x | y | dir | movable | turnable |
    When I try to trigger the level's laser
    Then an error should occur

  Scenario: Firing level's laser with no lasers throws
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And the level is initialized with id 11, required target number 0, a board with width 5 and height 5, and the following tokens:
      | token    | x | y | dir | movable | turnable |
      | portal   | 0 | 0 | UP  | false   | false    |
    When I try to fire the level's laser
    Then an error should occur


  Scenario: Incomplete level without trigger resets state
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And the level is initialized with id 12, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token  | x | y | dir | movable | turnable |
      | portal | 0 | 0 | UP  | false   | false    |
    Then the level should be incomplete
    And the level's current target number should be 0

  Scenario: Setting and then getting the inventory
    Given a new game is started
    And the level is initialized with id 0, required target number 0, a board with width 2 and height 2, and the following tokens:
      | token | x | y | dir | movable | turnable |
    When I set the level's inventory to the current board
    Then the level's inventory should be the current board