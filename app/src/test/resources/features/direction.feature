Feature: Direction.rotateClockwise
  As a game engine
  I want each Direction to rotate correctly clockwise

  Scenario Outline: rotate <dir> clockwise
    Given the current direction is <dir>
    When I rotate it clockwise
    Then the rotated direction should be <result>

    Examples:
      | dir   | result |
      | UP    | RIGHT  |
      | RIGHT | DOWN   |
      | DOWN  | LEFT   |
      | LEFT  | UP     |

  Scenario Outline: isParallel <dir> vs <other>
    Given the current direction is <dir>
    When I check if the current direction is parallel to <other>
    Then the result should be <parallel>

    Examples:
      | dir   | other | parallel |
      | UP    | DOWN  | true     |
      | DOWN  | UP    | true     |
      | LEFT  | RIGHT | true     |
      | RIGHT | LEFT  | true     |
      | UP    | LEFT  | false    |
      | LEFT  | UP    | false    |
      | DOWN  | RIGHT | false    |
      | RIGHT | DOWN  | false    |