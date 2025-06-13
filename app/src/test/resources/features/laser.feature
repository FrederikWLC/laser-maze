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
    Given a Laser token is placed on the board at (0, 0) facing right
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 0 | RIGHT |
      | 2 | 0 | RIGHT |
      | 3 | 0 | RIGHT |
      | 4 | 0 | RIGHT |
