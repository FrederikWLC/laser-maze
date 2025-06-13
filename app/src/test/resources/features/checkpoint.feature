Feature: Checkpoint
  As a player, I want the checkpoint to be movable,
  but the laser beam to pass through it,
  only if it hits an opening side,
  which depends on direction.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a Checkpoint token is placed on the board at (2, 2) facing right


  # Checkpoint is movable and turnable
  Scenario: Checkpoint is movable
    Given I try to move the Checkpoint token to (3, 3)
    Then the Checkpoint token should be at (3, 3)

  Scenario: Checkpoint is turnable
    Given I try to turn the Checkpoint token to face down
    Then the Checkpoint token should face down

  # Laser travels in a straight line until it hits the opening side of the checkpoint
  Scenario: Laser hits non-opening side of a Checkpoint
    Given a Laser token is placed on the board at (2, 4) facing up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 3 | UP |
      # Hits non-opening side of Checkpoint and stops

  # Laser travels in a straight line through the opening side of the checkpoint
  Scenario: Laser hits opening side of a Checkpoint
    Given a Laser token is placed on the board at (2, 4) facing up
    And I try to turn the Checkpoint token to face down
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 3 | UP |
      # Hits opening side of Checkpoint and goes through it
      | 2 | 2 | UP |
      # continues straight
      | 2 | 1 | UP |
      | 2 | 0 | UP |

