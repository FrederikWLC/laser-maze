Feature: Fire Laser
  As a player,
  I want the laser to travel through the board
  with respect to its direction
  when it's turned on.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the board is initialized with width 5 and height 5

    # Laser travels in an unobstructed straight line
  Scenario: Fire laser in an open horizontal line
    Given a completely mutable Laser token is placed on the board at (0, 0) facing right
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in     | out    |
      | 1 | 0 | RIGHT  | RIGHT  |
      | 2 | 0 | RIGHT  | RIGHT  |
      | 3 | 0 | RIGHT  | RIGHT  |
      | 4 | 0 | RIGHT  | RIGHT  |

  Scenario: Laser cannot fire before placement
    Given a completely mutable Laser token is created
    When I try to activate the laser
    Then an error should occur

  Scenario: Laser cannot fire before turning
    Given a completely mutable Laser token is placed on the board at (2, 2) without a direction
    When I try to activate the laser
    Then an error should occur