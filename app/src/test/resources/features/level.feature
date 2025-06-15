Feature: Level
  As a player in the process of completing a level,
  I want to interact with preplaced and required tokens.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the level is initialized with id 1 and a board with width 5 and height 5

    # Tokens fixed by the challenge card (must not move)
    And the level has the following tokens preplaced on its board:
      | token           | x | y | dir | turnable |
      | Target Mirror  | 4 | 0 | LEFT   | false   |
      | Checkpoint     | 2 | 2 | UP     | true    |

    # Tokens I must add (“ADD TO GRID”)
    And the level requires placement of:
      | token      |
      | Double Mirror |
      | Beam Splitter  |

    #  Number of target tokens I must hit to complete the level
    And the level's required target number is 3

    Then the level's id should be 1
    And the level's required target number should be 3
    And the level's board should have width 5 and height 5

    Scenario: I place a token (from the required tokens) on the board
      Given I place token 0 (from the required tokens) on the board at (0, 0)
      Then the token on the board at (0, 0) should be a Double Mirror token
      And the token should be turnable without direction
      And the remaining number of required tokens should be 1