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

  Scenario: retrieving the available-tokens map
    Given a level is initialized with:
      | token         | count |
      | target mirror | 2     |
      | portal        | 1     |
    When I retrieve the available tokens map
    Then the available tokens map should contain:
      | tokenClass       | count |
      | TargetMirrorToken| 2     |
      | PortalToken      | 1     |

  Scenario: getting a missing token returns null
    Given a level is initialized with:
      | token | count |
      | laser | 1     |
    When I get a "mirror" from the inventory
    Then it should be null

  Scenario: adding a token to the inventory
    Given a level is initialized with:
      | token      | count |
      | checkpoint | 1     |
    When I add a "checkpoint" to the inventory
    Then the inventory should contain 2 tokens total
    And the inventory should have 2 "checkpoint" tokens remaining

  Scenario: getTokenAtPosition returns first token
    Given a level is initialized with:
      | token         | count |
      | target mirror | 2     |
      | double mirror | 1     |
    When I get the token at inventory position 0
    Then it should not be null

  Scenario: getTokenAtPosition returns null for out‐of‐bounds
    Given a level is initialized with:
      | token | count |
      | portal| 1     |
    When I get the token at inventory position 1
    Then it should be null

  Scenario: getTokenAtPosition returns null for negative index
    Given a level is initialized with:
      | token | count |
      | portal| 1     |
    When I get the token at inventory position -1
    Then it should be null