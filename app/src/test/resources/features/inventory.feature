Feature: Inventory Token Management

  Background:
    Given a level is initialized with:
      | token          | count |
      | target mirror  | 2     |
      | double mirror  | 1     |

  Scenario: Level initializes with inventory tokens
    Then the inventory should contain 3 tokens total

  Scenario: Getting a token by type from level inventory
    When I get a "target mirror" from the inventory
    Then it should not be null

  Scenario: Placing a token from level inventory to the board
    Given a 5 by 5 board
    When I place a "target mirror" from inventory at board position (2, 3)
    Then the board should have a "target mirror" at (2, 3)
    And the inventory should have 1 "target mirror" tokens remaining
