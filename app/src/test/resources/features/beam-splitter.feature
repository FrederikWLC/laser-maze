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

  # Laser travels in a straight line until it hits the beam splitter that faces down/up
  Scenario: Fire laser in an open horizontal line
    Given an immutable Laser token is preplaced on the board at (4, 2) facing left
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 3 | 2 | LEFT |
      # Hits and gets split by beam splitter, but skips its tile (2, 2))
      # The forward beam continues straight (always first one calculated)
      | 1 | 2 | LEFT |
      | 0 | 2 | LEFT |
      # The reflected beam (always second one calculated) goes down
      | 2 | 3 | DOWN |
      | 2 | 4 | DOWN |


  # Laser travels in a straight line until it hits the mirror that faces left/right
  Scenario: Fire laser in an open horizontal line
    Given a completely mutable Laser token is placed on the board at (4, 2) facing left
    And I turn the Beam Splitter token to face up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 3 | 2 | LEFT |
      # Hits and gets split by beam splitter, but skips its tile (2, 2))
      # The forward beam (always first one calculated) continues straight
      | 1 | 2 | LEFT |
      | 0 | 2 | LEFT |
      # The reflected beam (always second one calculated) goes up
      | 2 | 1 | UP |
      | 2 | 0 | UP |

  Scenario: Fire laser through Beam Splitter facing LEFT
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a completely mutable Beam Splitter token is placed on the board at (2, 2) facing left
    Then the Beam Splitter token should be movable
    And the Beam Splitter token should be turnable
    Given a completely mutable Laser token is placed on the board at (0, 2) facing right
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 2 | RIGHT |
      | 3 | 2 | RIGHT |
      | 4 | 2 | RIGHT |
      | 2 | 1 | UP    |
      | 2 | 0 | UP    |

  Scenario: Fire laser through Beam Splitter facing DOWN
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a completely mutable Beam Splitter token is placed on the board at (2, 2) facing down
    Then the Beam Splitter token should be movable
    And the Beam Splitter token should be turnable
    Given a completely mutable Laser token is placed on the board at (2, 0) facing down
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 2 | 1 | DOWN  |
      | 2 | 3 | DOWN  |
      | 2 | 4 | DOWN  |
      | 3 | 2 | RIGHT |
      | 4 | 2 | RIGHT |

  Scenario: Fire laser through Beam Splitter from below (inner‐case UP)
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a completely mutable Beam Splitter token is placed on the board at (2, 2) facing right
    Given a completely mutable Laser token is placed on the board at (2, 4) facing up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 2 | 3 | UP    |
      | 2 | 1 | UP    |
      | 2 | 0 | UP    |
      | 3 | 2 | RIGHT |
      | 4 | 2 | RIGHT |

  Scenario: Fire laser through Beam Splitter from above (inner‐case DOWN)
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a completely mutable Beam Splitter token is placed on the board at (2, 2) facing right
    Given a completely mutable Laser token is placed on the board at (2, 0) facing down
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 2 | 1 | DOWN  |
      | 2 | 3 | DOWN  |
      | 2 | 4 | DOWN  |
      | 1 | 2 | LEFT  |
      | 0 | 2 | LEFT  |

  Scenario: Fire laser through Beam Splitter facing UP
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a completely mutable Beam Splitter token is placed on the board at (2, 2) facing right
    And I turn the Beam Splitter token to face UP
    Given a completely mutable Laser token is placed on the board at (0, 2) facing RIGHT
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 2 | RIGHT |
      | 3 | 2 | RIGHT |
      | 4 | 2 | RIGHT |
      | 2 | 3 | DOWN  |
      | 2 | 4 | DOWN  |

  Scenario: Fire laser through Beam Splitter facing RIGHT
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a completely mutable Beam Splitter token is placed on the board at (2, 2) facing right
    Given a completely mutable Laser token is placed on the board at (2, 0) facing DOWN
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir  |
      | 2 | 1 | DOWN |
      | 2 | 3 | DOWN |
      | 2 | 4 | DOWN |
      | 1 | 2 | LEFT |
      | 0 | 2 | LEFT |

  Scenario: Fire laser through Beam Splitter facing UP from the left
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a completely mutable Beam Splitter token is placed on the board at (2, 2) facing RIGHT
    And I turn the Beam Splitter token to face UP
    And a completely mutable Laser token is placed on the board at (0, 2) facing RIGHT
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 2 | RIGHT |
      | 3 | 2 | RIGHT |
      | 4 | 2 | RIGHT |
      | 2 | 3 | DOWN  |
      | 2 | 4 | DOWN  |

  Scenario: Fire laser through Beam Splitter facing UP from below
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a completely mutable Beam Splitter token is placed on the board at (2, 2) facing RIGHT
    And I turn the Beam Splitter token to face UP
    And a completely mutable Laser token is placed on the board at (2, 4) facing UP
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 2 | 3 | UP    |
      | 2 | 1 | UP    |
      | 2 | 0 | UP    |
      | 1 | 2 | LEFT  |
      | 0 | 2 | LEFT  |