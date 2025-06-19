Feature: Inventory Token Management
  Scenario: Inventory contains available tokens
    Given a level with an inventory containing:
      | token              | count |
      | TargetMirrorToken  | 2     |
      | DoubleMirrorToken  | 1     |
    Then the inventory should contain 3 tokens total

  Scenario: Getting a token by type
    When I get a TargetMirrorToken from the inventory
    Then it should not be null

  Scenario: Placing a token from inventory to board
    Given a 5 by 5 board
    And a level with an inventory containing:
      | token             | count |
      | TargetMirrorToken | 1     |
    When I place a TargetMirrorToken from inventory at board position (2, 3)
    Then the board should have a TargetMirrorToken at (2, 3)
    And the inventory should have 0 TargetMirrorTokens remaining
