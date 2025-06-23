Feature: Beam Splitter token
  As a player,
  I want the Beam Splitter to turn and be moved only if it's mutable,
  and the laser beam to be split in two by it depending on direction;
  creating one beam that continues straight and another that is reflected 90 degrees.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the board is initialized with width 5 and height 5
    # Here facing down or up means the mirror spans top left to bottom right (like a backslash)
    # Facing right or left means the mirror spans bottom left to top right (like a slash)
    And a completely mutable Beam Splitter token is placed on the board at (2, 2) facing right
    Then the Beam Splitter token should be movable
    And the Beam Splitter token should be turnable


  # Beam Splitter is movable and turnable
  Scenario: Beam Splitter is movable
    Given I move the Beam Splitter token to (3, 3)
    Then the Beam Splitter token should be at (3, 3)

  Scenario: Beam Splitter is turnable
    Given I turn the Beam Splitter token to face right
    Then the Beam Splitter token should face right

  # Laser travels in a straight line until it hits the backslash beam splitter that faces down/up
  Scenario: Fire laser through Backslash Beam Splitter
    Given an immutable Laser token is preplaced on the board at (4, 2) facing left
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in   | out  |
      | 3 | 2 | LEFT | LEFT |
      # Hits and gets split by beam splitter, and forward beam goes straight through its tile (2, 2))
      | 2 | 2 | LEFT | LEFT |
      # The forward beam continues straight (always first one calculated)
      | 1 | 2 | LEFT | LEFT |
      | 0 | 2 | LEFT | LEFT |
      # First position of reflected beam turns within its tile (2, 2))
      | 2 | 2 | LEFT | DOWN |
      # The reflected beam (always second one calculated) continues down
      | 2 | 3 | DOWN   | DOWN |
      | 2 | 4 | DOWN   | DOWN |


  # Laser travels in a straight line until it hits the mirror that faces left/right
  Scenario: Fire laser through Slash Beam Splitter
    Given a completely mutable Laser token is placed on the board at (4, 2) facing left
    And I turn the Beam Splitter token to face up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out   |
      | 3 | 2 | LEFT  | LEFT  |
      # Hits and gets split by beam splitter, and forward beam goes straight through its tile (2, 2))
      | 2 | 2 | LEFT | LEFT |
      # The forward beam (always first one calculated) continues straight
      | 1 | 2 | LEFT  | LEFT  |
      | 0 | 2 | LEFT  | LEFT  |
      # First position of reflected beam turns within its tile (2, 2))
      | 2 | 2 | LEFT | UP |
      # The reflected beam (always second one calculated) continues up
      | 2 | 1 | UP   | UP |
      | 2 | 0 | UP   | UP |