Feature: Board setup

  #test for Board
  Scenario: Create an empty board
    Given a new game is started
    When the board is initialized with width 5 and height 5
    Then the board should have 25 empty tiles

  #test for Tile and isEmpty
  Scenario: Access a tile within bounds
    Given the board is initialized with width 5 and height 5
    When I get the tile at position (1, 2)
    Then the tile should exist and be empty

    #create these steps after adding Token Class
    #Scenario: Tile with a token is not empty
    #  Given the board is initialized with width 2 and height 2
    #  When I get the tile at position (1, 1)
    #  And I place a dummy token on the tile
    #  Then the tile should not be empty

  #test for getTile
  Scenario: Access a tile outside board bounds
    Given the board is initialized with width 5 and height 5
    When I try to get the tile at position (6, 6)
    Then an error should occur

  #test for getWidth and getHeight
  Scenario: Check board dimensions
    Given the board is initialized with width 6 and height 4
    Then the board width should be 6
    And the board height should be 4

  Scenario: Tile coordinates are set correctly
    Given the board is initialized with width 2 and height 2
    When I get the tile at position (1, 1)
    Then the tile's position should be (1, 1)