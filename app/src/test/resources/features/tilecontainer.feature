Feature: TileContainer common behavior
  Scenario: Accessing a tile in any tile container
    Given a 3 by 3 inventory is initialized
    When I get the tile at position (1, 1)
    Then the tile should exist and be empty

  Scenario: Setting a tile in the container
    Given a 3 by 3 inventory is initialized
    When I place a TargetMirrorToken at position (1, 2)
    Then the tile at (1, 2) should contain a TargetMirrorToken