Feature: Double Mirror token
  As a player,
  I want the Double mirror to turn and be moved only if it's mutable,
  and the laser beam to be reflected 90 degrees by it
  depending on direction.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the board is initialized with width 5 and height 5
    # Here facing down or up means the mirror spans top left to bottom right (like a backslash)
    # Facing right or left means the mirror spans bottom left to top right (like a slash)
    And a completely mutable Double Mirror token is placed on the board at (2, 2) facing right


  # Double Mirror is movable and turnable
  Scenario: Double Mirror is movable
    Given I move the Double Mirror token to (3, 3)
    Then the Double Mirror token should be at (3, 3)

  Scenario: Double Mirror is turnable
    Given I turn the Double Mirror token to face right
    Then the Double Mirror token should face right

  # Laser travels in a straight line until it hits the double mirror that faces down/up
  Scenario: Laser reflects through Backslash Double Mirror
    Given a completely mutable Laser token is placed on the board at (4, 2) facing left
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in   | out  |
      | 3 | 2 | LEFT | LEFT |
      # Hits and gets reflected by double mirror, and turns within its tile (2, 2)
      | 2 | 2 | LEFT | DOWN |
      # The reflected beam continues straight down
      | 2 | 3 | DOWN | DOWN |
      | 2 | 4 | DOWN | DOWN |

  # Laser travels in a straight line until it hits the mirror that faces left/right
  Scenario: Laser reflects through Slash Double Mirror
    Given a completely mutable Laser token is placed on the board at (4, 2) facing left
    And I turn the Double Mirror token to face up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in   | out  |
      | 3 | 2 | LEFT | LEFT |
      # Hits and gets reflected by double mirror, and turns within its tile (2, 2)
      | 2 | 2 | LEFT | UP   |
      # The reflected beam continues straight up
      | 2 | 1 | UP   | UP   |
      | 2 | 0 | UP   | UP   |