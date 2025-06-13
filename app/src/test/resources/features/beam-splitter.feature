Feature: Beam Splitter token
  As a player, I want the Beam Splitter to be mutable,
  and the laser beam to be split in two by it depending on direction;
  creating one beam that continues straight and another that is reflected 90 degrees.

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the board is initialized with width 5 and height 5
    # Here facing down or up means the mirror spans top left to bottom right (like a backslash)
    # Facing right or left means the mirror spans bottom left to top right (like a slash)
    And a Beam Splitter token is placed on the board at (2, 2) facing right


  # Beam Splitter is movable and turnable
  Scenario: Beam Splitter is movable
    Given I try to move the Beam Splitter token to (3, 3)
    Then the Beam Splitter token should be at (3, 3)

  Scenario: Beam Splitter is turnable
    Given I try to turn the Beam Splitter token to face right
    Then the Beam Splitter token should face right

  # Laser travels in a straight line until it hits the beam splitter that faces down/up
  Scenario: Fire laser in an open horizontal line
    Given a Laser token is placed on the board at (4, 2) facing left
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 3 | 2 | LEFT |
      # The forward beam continues straight
      | 1 | 2 | LEFT |
      | 0 | 2 | LEFT |
      # The reflected beam goes down
      | 2 | 3 | DOWN |
      | 2 | 4 | DOWN |


  # Laser travels in a straight line until it hits the mirror that faces left/right
  Scenario: Fire laser in an open horizontal line
    Given a Laser token is placed on the board at (4, 2) facing left
    And I try to turn the Beam Splitter token to face up
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 3 | 2 | LEFT |
      # The forward beam continues straight
      | 1 | 2 | LEFT |
      | 0 | 2 | LEFT |
      # The reflected beam goes up
      | 2 | 1 | UP |
      | 2 | 0 | UP |