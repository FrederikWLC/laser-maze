Feature: Portal
  As a player,
  I want the portal to turn and be moved only if it's mutable,
  but the laser beam to be passed to its paired twin,
  only if it hits a matching red or blue opening side,
  which depends on direction. Otherwise, the beam should stop.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    ## Here, a Portal token pair starting with the first one and ending with the next in the list
    # will be built in the test as twins.
    # without an even amount of Portal tokens, the test will throw an error.
    And the level is initialized with id 69, required target number 0, a board with width 5 and height 5, and the following tokens:
      | token           | preplaced | x  | y  | dir   | turnable |
      | Portal          | true      | 4  | 0  | LEFT  | false    |
      | Portal          | true      | 0  | 4  | RIGHT | false    |
      | Laser           | false     |    |    |       |          |

  # Laser travels from blue opening side of a Portal A and exits on blue opening side of Portal B
  Scenario: Laser hits non-opening side of a Portal
    Given a completely mutable Laser token is placed on the board at (2, 4) facing up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 3 | UP |
      # Hits non-opening side of Portal and stops

  # Laser travels in a straight line through the opening side of the portal
  Scenario: Laser hits opening side of a Portal
    Given a completely mutable Laser token is placed on the board at (2, 4) facing up
    And I turn the Portal token to face down
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 3 | UP |
      # Hits opening side of Portal and goes through it
      | 2 | 2 | UP |
      # continues straight
      | 2 | 1 | UP |
      | 2 | 0 | UP |

