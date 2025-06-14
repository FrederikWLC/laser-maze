Feature: Cell blocker
  As a player, I want the cell blocker to be immutable,
  but the laser beam to not be affected by it.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a Cell Blocker token is preplaced on the board at (2, 2)


  # Cell blocker is immovable and unturnable
  Scenario: Cell blocker is immovable
    Given I try to move the Cell Blocker token to (3, 3)
    Then an error should occur
    And the Cell Blocker token should be at (2, 2)

  Scenario: Cell blocker is unturnable
    Given I try to turn the Cell Blocker token to face right
    Then an error should occur

  # Laser travels in an unobstructed straight line through the cell blocker
  Scenario: Fire laser in an open horizontal line
    Given a completely mutable Laser token is placed on the board at (2, 4) facing up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 3 | UP |
      # Hits and goes through cell blocker
      | 2 | 2 | UP |
      # continues straight
      | 2 | 1 | UP |
      | 2 | 0 | UP |

