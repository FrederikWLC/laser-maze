Feature: Target Mirror token
  As a player, I want the Target mirror to be mutable,
  and the laser beam to be reflected 90 degrees by it
  depending on direction.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the board is initialized with width 5 and height 5
    # Here facing down means the target faces down
      # and the mirror only reflects beams changes their directions from right to up and vice versa (like a backslash)
    # Facing up means the target faces up
      # and the mirror only reflects beams changes their directions from left to down and vice versa (like a backslash)
    # Facing right means the target faces right
        # and the mirror only reflects beams changes their directions from down to left and vice versa (like a slash)
    # Facing left means the target faces left
        # and the mirror only reflects beams and changes their directions from up to right and vice versa (like a slash)
    And a Target Mirror token is placed on the board at (2, 2) facing down

  # Target Mirror is movable and turnable
  Scenario: Target Mirror is movable
    Given I try to move the Target Mirror token to (3, 3)
    Then the Target Mirror token should be at (3, 3)

  Scenario: Target Mirror is turnable
    Given I try to turn the Target Mirror token to face right
    Then the Target Mirror token should face right

  # Hits mirror side: Laser travels in a straight line until it hits the target mirror that faces down
  Scenario: Laser hits mirror side
    Given a Laser token is placed on the board at (2, 0) facing down
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 1 | DOWN |
      | 3 | 2 | RIGHT |
      | 4 | 2 | RIGHT |

  # Hits target side: Laser travels in a straight line until it hits the target that faces down
  Scenario: Laser hits target side
    Given a Laser token is placed on the board at (2, 4) facing up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 3 | UP |

  # Hits bare side: Laser travels in a straight line until it hits the target mirror that faces down
  Scenario: Laser hits bare side
    Given a Laser token is placed on the board at (0, 2) facing right
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 1 | 2 | RIGHT |