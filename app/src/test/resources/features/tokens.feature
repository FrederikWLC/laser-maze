# to be implemented...
#Feature: Token placement

#Scenario: Place a laser token on an empty tile
#  Given the board is initialized
#  When I place a Laser token at position (2, 3)
#  Then the tile at position (2, 3) should contain a Laser token

#  @validation @immovable

#Scenario: Prevent placing a token on an occupied tile
#  Given the board has a Laser token at (1, 1)
#  When I try to place a Mirror token at (1, 1)
#  Then an error should occur