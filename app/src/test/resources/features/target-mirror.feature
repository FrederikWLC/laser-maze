Feature: Target Mirror token
  As a player, I want the Target mirror to be mutable,
  and the laser beam to be reflected 90 degrees by it
  depending on direction.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the board is initialized with width 5 and height 5
    # Here facing down means the target faces down
      # and the mirror only reflects beams from right to up and vice versa (like a backslash)
    # Facing up means the target faces up
      # and the mirror only reflects beams from left to down and vice versa (like a backslash)
    # Facing right means the target faces right
        # and the mirror only reflects beams from down to left and vice versa (like a slash)
    # Facing left means the target faces left
        # and the mirror only reflects beams from up to right and vice versa (like a slash)
    And a completely mutable Target Mirror token is placed on the board at (2, 2) facing down

  # Target Mirror is movable and turnable
  Scenario: Target Mirror is movable
    Given I move the Target Mirror token to (3, 3)
    Then the Target Mirror token should be at (3, 3)

  Scenario: Target Mirror is turnable
    Given I turn the Target Mirror token to face right
    Then the Target Mirror token should face right

  # Hits mirror side: Laser travels in a straight line until it hits the target mirror that faces down
  Scenario: Laser hits mirror side
    Given a completely mutable Laser token is placed on the board at (2, 0) facing down
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out   |
      | 2 | 1 | DOWN  | DOWN |
      | 2 | 2 | DOWN  | RIGHT |
      | 3 | 2 | RIGHT | RIGHT |
      | 4 | 2 | RIGHT | RIGHT |

  # Hits target side: Laser travels in a straight line until it hits the target that faces down
  Scenario: Laser hits target side
    Given a completely mutable Laser token is placed on the board at (2, 4) facing up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in   | out  |
      | 2 | 3 | UP   | UP   |
      # Hits target side of target/mirror token, and skips its tile (2, 2))


  # Hits bare side: Laser travels in a straight line until it hits the target mirror that faces down
  Scenario: Laser hits bare side
    Given a completely mutable Laser token is placed on the board at (0, 2) facing right
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out    |
      | 1 | 2 | RIGHT | RIGHT  |
      # Hits non-target non-mirror side of target/mirror token, but skips its tile (2, 2))

  Scenario: Laser mirror-side from down when facing UP (Reflects to the left)
    Given I turn the Target Mirror token to face up
    And a completely mutable Laser token is placed on the board at (2, 4) facing up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in   | out  |
      | 2 | 3 | UP   | UP   |
      | 2 | 2 | UP   | LEFT |
      | 1 | 2 | LEFT | LEFT |
      | 0 | 2 | LEFT | LEFT |

  Scenario: Laser target-side when facing UP (Stops the beam)
    Given I turn the Target Mirror token to face up
    And a completely mutable Laser token is placed on the board at (2, 0) facing down
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out   |
      | 2 | 1 | DOWN  | DOWN  |

  Scenario: Laser bare-side when facing UP (Stops the beam)
    Given I turn the Target Mirror token to face up
    And a completely mutable Laser token is placed on the board at (4, 2) facing left
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out   |
      | 3 | 2 | LEFT  | LEFT  |

  Scenario: Laser hits mirror side when facing RIGHT (Reflects upwards)
    Given I turn the Target Mirror token to face right
    And a completely mutable Laser token is placed on the board at (0, 2) facing right
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out   |
      | 1 | 2 | RIGHT | RIGHT |
      | 2 | 2 | RIGHT | UP    |
      | 2 | 1 | UP    | UP    |
      | 2 | 0 | UP    | UP    |

  Scenario: Laser target-side when facing RIGHT (reflects to the left)
    Given I turn the Target Mirror token to face right
    And a completely mutable Laser token is placed on the board at (2, 0) facing down
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out   |
      | 2 | 1 | DOWN  | DOWN  |
      | 2 | 2 | DOWN  | LEFT |
      | 1 | 2 | LEFT  | LEFT  |
      | 0 | 2 | LEFT  | LEFT  |

  Scenario: Laser bare-side when facing RIGHT (Stops the beam)
    Given I turn the Target Mirror token to face right
    And a completely mutable Laser token is placed on the board at (4, 2) facing left
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out   |
      | 3 | 2 | LEFT  | LEFT  |

  Scenario: Laser mirror-side when facing LEFT (Reflects downwards)
    Given I turn the Target Mirror token to face left
    And a completely mutable Laser token is placed on the board at (4, 2) facing left
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out   |
      | 3 | 2 | LEFT  | LEFT  |
      | 2 | 2 | LEFT  | DOWN  |
      | 2 | 3 | DOWN  | DOWN  |
      | 2 | 4 | DOWN  | DOWN  |

  Scenario: Laser target-side when facing LEFT (reflects to the right)
    Given I turn the Target Mirror token to face left
    And a completely mutable Laser token is placed on the board at (2, 4) facing up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out    |
      | 2 | 3 | UP    | UP     |
      | 2 | 2 | UP    | RIGHT  |
      | 3 | 2 | RIGHT | RIGHT  |
      | 4 | 2 | RIGHT | RIGHT  |

  Scenario: Laser mirror-side when facing DOWN (Reflects upwards)
    Given a completely mutable Target Mirror token is placed on the board at (2, 2) facing down
    And a completely mutable Laser token is placed on the board at (4, 2) facing LEFT
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in   | out  |
      | 3 | 2 | LEFT | LEFT |
      | 2 | 2 | LEFT | UP   |
      | 2 | 1 | UP   | UP   |
      | 2 | 0 | UP   | UP   |

  Scenario: Laser bare-side from below when facing RIGHT (Stops the beam)
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a completely mutable Target Mirror token is placed on the board at (2, 2) facing right
    And a completely mutable Laser token is placed on the board at (2, 4) facing up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in   | out  |
      | 2 | 3 | UP   | UP   |

  Scenario: Laser bare-side from above when facing LEFT (Stops the beam)
    Given a new game is started
    And the board is initialized with width 5 and height 5
    And a completely mutable Target Mirror token is placed on the board at (2, 2) facing left
    And a completely mutable Laser token is placed on the board at (2, 0) facing down
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | in    | out   |
      | 2 | 1 | DOWN  | DOWN  |