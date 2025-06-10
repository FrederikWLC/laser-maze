Feature: Fire Laser

  Scenario: Fire laser in an open line
    Given the board has a Laser token at (0, 0) facing right
    When I activate the laser
    Then the laser beam should travel through positions (1,0), (2,0), (3,0), (4,0)