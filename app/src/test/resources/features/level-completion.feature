Feature: Level Completion
  As a player, I want the level to be marked as completed
  only when the laser beam:
  • activates AT LEAST the number of targets specified,
  • activates all targets marked as required,
  • passes through all checkpoints,
  • touches every token shown on the challenge card (excluding Cell Blockers),
  AND as natural consequence of the above:
  • I have placed all required “ADD TO GRID” tokens
  • all the turnable tokens given by the level have a direction

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the level is initialized with id 1, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token           | preplaced | x  | y  | dir   | turnable | is required |
      | Target Mirror   | true      | 3  | 3  | LEFT  | false    | true        |
      | Laser           | true      | 1  | 1  | DOWN  | false    |             |
      | Double Mirror   | false     |    |    |       |          |             |

  Scenario: Level is incomplete if any requirement is unmet
    Given I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 2 | DOWN |
      | 1 | 3 | DOWN |
      | 1 | 4 | DOWN |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete


  Scenario: Level is completed when all requirements are fulfilled
    Given I place token 0 (from the required tokens) on the board at (1, 3)
    Then the token on the board at (1, 3) should be a Double Mirror token
    # We turn the Double Mirror token to be a backslash mirror
    And I turn the Double Mirror token to face up
    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 2 | DOWN |
      | 2 | 3 | RIGHT |
    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 1
    And the beam path should hit all the required targets
    # todo: add a test of level completion with checkpoints
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board

