Feature: Base Token behaviors
  As a game engine
  I want every token type to have correct mutability,
  default-interact and touch logic

  Background:
    Given a new game is started
    And the board is initialized with width 5 and height 5

  Scenario: Default mutability
    Given a raw Token is created
    Then token should not be movable
    And token should not be turnable
    And token should require touch

  Scenario: Default Token.interact stops the beam
    Given a raw Token is created
    And the raw Token is placed on the board at (1, 1)
    And a completely mutable Laser token is placed on the board at (0, 1) facing RIGHT
    When I activate the laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir |

  Scenario Outline: isTouched logic
    Given a raw Token is created
    And the raw Token is placed on the board at (1, 1)
    When I check if the beam at (<x>, <y>) coming from <dir> touches it
    Then the result should be <touch>

    Examples:
      | x | y | dir   | touch |
      | 1 | 1 | UP    | false |
      | 1 | 0 | DOWN  | true  |
      | 0 | 1 | RIGHT | true  |
      | 2 | 1 | LEFT  | true  |
      | 1 | 2 | UP    | true  |