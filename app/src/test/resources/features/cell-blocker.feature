Feature: Cell blocker
  As a player, I want the cell blocker to be immovable,
  but the laser beam to not be affected by it.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And the board contains a Cell Blocker token at (2, 2)

  # Cell blocker is immovable and unturnable
  Scenario: Cell blocker is immovable
    Given I try to move the Cell Blocker token to (3, 3)
    Then the Cell Blocker token should remain at (2, 2)

  Scenario: Cell blocker is unturnable
    Given I try to turn the Cell Blocker token to face right
    Then the Cell Blocker token should still face down

  # Laser travels in an unobstructed straight line through the cell blocker
  Scenario: Fire laser in an open horizontal line
    Given the board contains a Laser token at (2, 4) facing up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 3 | UP |
      | 2 | 2 | UP |
      | 2 | 1 | UP |
      | 2 | 0 | UP |

