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
      | token           | x  | y  | dir   | turnable | movable |
      | Laser           | 2  | 3  | UP    | false    | false   |
      | Portal          | 2  | 1  | DOWN  | true     | false   |
      | Portal          | 1  | 3  | UP    | false    | false   |
    Then the first pair of the level's Portal tokens should be each other's twins
    
    Given the token on the board at (1, 3) should be a Portal token
    Then the Portal token's blue opening side should face up
    And the Portal token's red opening side should face down

  # Laser travels from blue opening side of a Portal A and exits on blue opening side of Portal B
  Scenario: Teleport through the Blue opening
    Given the token on the board at (2, 1) should be a Portal token
    And the Portal token's blue opening side should face down
    And the Portal token's red opening side should face up
    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 2 | UP |
      # Hits blue opening side of Portal at (2,1) and goes through it, spawning at other Portal
      | 1 | 2 | UP |
      | 1 | 1 | UP |
      | 1 | 0 | UP |
      # Hits wall and stops


  # Laser travels from red opening side of a Portal A and exits on red opening side of Portal B
  Scenario: Teleport through the Red opening
    Given the token on the board at (2, 1) should be a Portal token
    When I turn the Portal token to face up
    Then the Portal token's red opening side should face down
    And the Portal token's blue opening side should face up
    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 2 | UP |
      # Hits blue opening side of Portal at (2,1) and goes through it, spawning at other Portal
      | 1 | 4 | DOWN |


  # Laser travels until hitting the non-opening side of a Portal A and stops
  Scenario: Teleportation through non-opening side of the portal
    Given the token on the board at (2, 1) should be a Portal token
    When I turn the Portal token to face right
    Then the Portal token's blue opening side should face right
    And the Portal token's red opening side should face left
    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |
      | 2 | 2 | UP |
      # Hits non-opening side of Portal and stops
