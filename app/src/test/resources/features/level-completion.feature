
Feature: Level Completion
  As a player, I want the level to be marked as completed
  only when the laser beam:
  • activates AT LEAST the number of targets specified,
  • activates all targets marked as required,
  • passes through all checkpoints,
  • touches every token shown on the challenge card (excluding Cell Blockers),
  AND as natural consequence of the above:
  • I have placed all required “ADD TO GRID” tokens
  • all the turnable tokens given by the level have a direction

  Background:
    # We always start from a fresh 5 x 5 board in these scenarios
    Given a new game is started
    And the level is initialized with id 1, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token           | preplaced | x  | y  | dir   | turnable | is required |
      | Target Mirror   | true      | 3  | 3  | LEFT  | false    | true        |
      | Laser           | true      | 1  | 1  | DOWN  | false    |             |
      | Double Mirror   | false     |    |    |       |          |             |

  Scenario: Level is incomplete if any requirement is unmet
    Given I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 2 | DOWN |
      | 1 | 3 | DOWN |
      | 1 | 4 | DOWN |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete


  Scenario: Level is completed when all requirements are fulfilled
    Given I place token 0 (from the required tokens) on the board at (1, 3)
    Then the token on the board at (1, 3) should be a Double Mirror token
    # We turn the Double Mirror token to be a backslash mirror
    And I turn the Double Mirror token to face up
    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 2 | DOWN |
      | 2 | 3 | RIGHT |
    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 1
    And the beam path should hit all the required targets
    # todo: add a test of level completion with checkpoints
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board


    #level 2

  Scenario: Level 2 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 2, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 0 | 4 |        | true     |             |
      | Target Mirror | true      | 0 | 3 | LEFT   | false    | false       |
      | Target Mirror | true      | 4 | 0 | DOWN   | false    | true        |
      | Target Mirror | false     |   |   |        |          |             |

    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 4 | RIGHT |
      | 2 | 4 | RIGHT |
      | 3 | 4 | RIGHT |
      | 4 | 4 | RIGHT |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 2 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 2, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 0 | 4 |        | true     |             |
      | Target Mirror | true      | 0 | 3 | LEFT   | false    | false       |
      | Target Mirror | true      | 4 | 0 | DOWN   | false    | true        |
      | Target Mirror | false     |   |   |        |          |             |
    Given I place token 0 (from the required tokens) on the board at (4, 3)
    Then the token on the board at (4, 3) should be a Target Mirror token
    And I turn the Target Mirror token to face right
    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 3 | RIGHT |
      | 2 | 3 | RIGHT |
      | 3 | 3 | RIGHT |
      | 4 | 2 | UP    |
      | 4 | 1 | UP    |
    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 1
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board


      #level 3

  Scenario: Level 3 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 3, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | false     |   |   |        |          |             |
      | Target Mirror | true      | 0 | 3 |        | true     |  false      |
      | Target Mirror | true      | 0 | 4 |        | true     |  false      |
      | Target Mirror | true      | 1 | 4 |        | true     |  true       |
      | Target Mirror | true      | 4 | 3 |   UP   | false    |  false      |

    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 3 | 3 | LEFT  |
      | 2 | 3 | LEFT  |
      | 1 | 3 | LEFT  |
      | 0 | 2 | UP    |
      | 0 | 1 | UP    |
      | 0 | 1 | UP    |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 3 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 3, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | false     |   |   |        |          |             |
      | Target Mirror | true      | 0 | 3 |        | true     |  false      |
      | Target Mirror | true      | 0 | 4 |        | true     |  false      |
      | Target Mirror | true      | 1 | 4 |        | true     |  true       |
      | Target Mirror | true      | 4 | 3 |   UP   | false    |  false      |
    Given I place token 0 (from the required tokens) on the board at (4, 4)
    Then the token on the board at (4, 4) should be a Laser token
    And I turn the Laser token to face up
    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 3 | 3 | LEFT  |
      | 2 | 3 | LEFT  |
      | 1 | 3 | LEFT  |
    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 1
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board


      #level 4

  Scenario: Level 4 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 4, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 1 | 0 | DOWN   | false    |             |
      | Target Mirror | true      | 0 | 4 |        | true     | false       |
      | Target Mirror | false     |   |   |        |          | true        |
      | Target Mirror | false     |   |   |        |          |             |
      | Checkpoint    | true      | 0 | 1 |  UP    | false    | false       |

    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 1 | DOWN  |
      | 1 | 2 | DOWN  |
      | 1 | 3 | DOWN  |
      | 1 | 4 | DOWN  |

    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 4 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 4, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 1 | 0 | DOWN   | false    |             |
      | Target Mirror | true      | 0 | 4 |        | true     | false       |
      | Target Mirror | false     |   |   |        |          | true        |
      | Target Mirror | false     |   |   |        |          |             |
      | Checkpoint    | true      | 0 | 1 |  UP    | false    | false       |
  # place token 0
    Given I place token 0 (from the required tokens) on the board at (1, 4)
    Then the token on the board at (1, 4) should be a Target Mirror token
    And I turn the Target Mirror token to face RIGHT

  # place token 1
    And I place token 1 (from the required tokens) on the board at (0, 0)
    Then the token on the board at (0, 0) should be a Target Mirror token
    And I turn the Target Mirror token to face DOWN

    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 1 | DOWN  |
      | 1 | 2 | DOWN  |
      | 1 | 3 | DOWN  |
      | 0 | 3 | UP    |
      | 0 | 2 | UP    |

    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 1
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board

          #level 5

  Scenario: Level 5 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 5, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 4 | 2 |        | true     |             |
      | Target Mirror | true      | 1 | 2 | RIGHT  | false    | true        |
      | Target Mirror | true      | 3 | 3 | RIGHT  | false    | false       |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Checkpoint    | false     |   |   |        |          |             |
      | Double Mirror | true      | 2 | 2 | LEFT   | false    |  false      |
      | Cell Blocker  | true      | 3 | 1 |        | false    |  false      |

    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 3 | 2 | LEFT  |
      | 2 | 3 | DOWN  |
      | 2 | 4 | DOWN  |

    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 5 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 5, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 4 | 2 |        | true     |             |
      | Target Mirror | true      | 1 | 2 | RIGHT  | false    | true        |
      | Target Mirror | true      | 3 | 3 | RIGHT  | false    | false       |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Checkpoint    | false     |   |   |        |          |             |
      | Double Mirror | true      | 2 | 2 | LEFT   | false    |  false      |
      | Cell Blocker  | true      | 3 | 1 |        | false    |  false      |
  # place token 0
    Given I place token 0 (from the required tokens) on the board at (2, 3)
    Then the token on the board at (2, 3) should be a Target Mirror token
    And I turn the Target Mirror token to face DOWN

  # place token 1
    And I place token 1 (from the required tokens) on the board at (2, 0)
    Then the token on the board at (2, 0) should be a Target Mirror token
    And I turn the Target Mirror token to face LEFT

  # place token 2
    And I place token 2 (from the required tokens) on the board at (3, 0)
    Then the token on the board at (3, 0) should be a Target Mirror token
    And I turn the Target Mirror token to face UP

  # place token 3
    And I place token 3 (from the required tokens) on the board at (2, 1)
    Then the token on the board at (2, 1) should be a Checkpoint token
    And I turn the Checkpoint token to face DOWN

    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 3 | 2 | LEFT  |
      | 3 | 2 | UP    |


    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 1
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board


     #level 6
  Scenario: Level 6 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 6, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | false     |   |   |        |          |             |
      | Target Mirror | true      | 2 | 0 |        | true     |  true       |
      | Target Mirror | true      | 1 | 1 |        | true     |  false      |
      | Target Mirror | true      | 3 | 1 |        | true     |  false      |
      | Target Mirror | true      | 1 | 3 |        | true     |  true       |
      | Target Mirror | true      | 3 | 3 |        | true     |  false      |
      | Checkpoint    | true      | 1 | 2 |        | true     |  false      |
      | Beam Splitter | true      | 2 | 3 |  LEFT  | false    |  false      |


    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 4 | RIGHT |
      | 2 | 4 | RIGHT |
      | 3 | 4 | RIGHT |
      | 4 | 4 | RIGHT |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 6 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 6, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | false     |   |   |        |          |             |
      | Target Mirror | true      | 2 | 0 |        | true     |  true       |
      | Target Mirror | true      | 1 | 1 |        | true     |  false      |
      | Target Mirror | true      | 3 | 1 |        | true     |  false      |
      | Target Mirror | true      | 1 | 3 |        | true     |  true       |
      | Target Mirror | true      | 3 | 3 |        | true     |  false      |
      | Checkpoint    | true      | 1 | 2 |        | true     |  false      |
      | Beam Splitter | true      | 2 | 3 |  LEFT  | false    |  false      |
    Given I place token 0 (from the required tokens) on the board at (2, 4)
    Then the token on the board at (2, 4) should be a Laser token
    And I turn the Laser token to face UP
    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 2 | 2 | UP    |
      | 2 | 1 | UP    |
      | 3 | 2 | UP    |
      | 2 | 1 | LEFT  |
    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 2
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board

     #level 7
  Scenario: Level 7 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 7, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 0 | 0 |        | true     |             |
      | Target Mirror | true      | 4 | 0 |        | true     |  true       |
      | Double Mirror | true      | 3 | 1 |  UP    | false    |  false      |
      | Target Mirror | false     |   |   |        |          |  true       |
      | Beam Splitter | false     |   |   |        |          |             |


    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 0 | 1 | DOWN  |
      | 0 | 2 | DOWN  |
      | 0 | 3 | DOWN  |
      | 0 | 4 | DOWN  |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 7 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 7, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 0 | 0 |        | true     |             |
      | Target Mirror | true      | 4 | 0 |        | true     |  true       |
      | Double Mirror | true      | 3 | 1 |  UP    | false    |  false      |
      | Target Mirror | false     |   |   |        |          |  true       |
      | Beam Splitter | false     |   |   |        |          |             |
  # place token 0
    Given I place token 0 (from the required tokens) on the board at (4, 1)
    Then the token on the board at (4, 1) should be a Target Mirror token
    And I turn the Target Mirror token to face LEFT

  # place token 1
    And I place token 1 (from the required tokens) on the board at (3, 0)
    Then the token on the board at (3, 0) should be a Beam Splitter token
    And I turn the Beam Splitter token to face UP

    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 0 | RIGHT |
      | 2 | 0 | RIGHT |
    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 2
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board

   #level 8
  Scenario: Level 8 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 8, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | false     |   |   |        |          |             |
      | Target Mirror | true      | 1 | 3 |        | true     |  false      |
      | Target Mirror | true      | 1 | 4 |        | true     |  false      |
      | Target Mirror | true      | 2 | 4 |        | true     |  true       |
      | Target Mirror | true      | 3 | 3 |        | true     |  false      |
      | Target Mirror | true      | 3 | 4 |        | true     |  false      |
      | Checkpoint    | true      | 2 | 1 |        | true     |  false      |


    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 0 | RIGHT |
      | 2 | 0 | RIGHT |
      | 3 | 0 | RIGHT |
      | 4 | 0 | RIGHT |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 8 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 8, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | false     |   |   |        |          |             |
      | Target Mirror | true      | 1 | 3 |        | true     |  false      |
      | Target Mirror | true      | 1 | 4 |        | true     |  false      |
      | Target Mirror | true      | 2 | 4 |        | true     |  true       |
      | Target Mirror | true      | 3 | 3 |        | true     |  false      |
      | Target Mirror | true      | 3 | 4 |        | true     |  false      |
      | Checkpoint    | true      | 2 | 1 |        | true     |  false      |
    Given I place token 0 (from the required tokens) on the board at (2, 0)
    Then the token on the board at (2, 0) should be a Laser token
    And I turn the Laser token to face DOWN
    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 2 | 2 | DOWN  |
      | 2 | 3 | DOWN  |
      | 2 | 3 | RIGHT |
    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 1
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board

         #level 9
  Scenario: Level 9 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 9, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 3 | 1 |        | true     |             |
      | Target Mirror | true      | 2 | 0 |        | true     |  true       |
      | Target Mirror | true      | 0 | 1 |        | true     |  false      |
      | Target Mirror | true      | 0 | 3 |        | true     |  false      |
      | Target Mirror | true      | 1 | 2 |        | true     |  true       |
      | Target Mirror | true      | 2 | 3 |        | true     |  false      |
      | Double Mirror | false     |   |   |        |          |             |
      | Beam Splitter | true      | 2 | 2 |        | true     |  false      |

    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 3 | 2 | DOWN  |
      | 3 | 3 | DOWN  |
      | 3 | 4 | DOWN  |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 9 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 9, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 3 | 1 |        | true     |             |
      | Target Mirror | true      | 2 | 0 |        | true     |  true       |
      | Target Mirror | true      | 0 | 1 |        | true     |  false      |
      | Target Mirror | true      | 0 | 3 |        | true     |  false      |
      | Target Mirror | true      | 1 | 2 |        | true     |  true       |
      | Target Mirror | true      | 2 | 3 |        | true     |  false      |
      | Double Mirror | false     |   |   |        |          |             |
      | Beam Splitter | true      | 2 | 2 |        | true     |  false      |

    Given I place token 0 (from the required tokens) on the board at (2, 1)
    Then the token on the board at (2, 1) should be a Double Mirror token
    And I turn the Double Mirror token to face LEFT

    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 0 | 2 | UP    |
      | 1 | 3 | LEFT  |
    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 2
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board


   #level 10
  Scenario: Level 10 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 10, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 0 | 0 |        | true     |             |
      | Target Mirror | true      | 4 | 3 | LEFT   | false    |  true       |
      | Target Mirror | true      | 3 | 4 | UP     | false    |  true       |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Double Mirror | true      | 1 | 2 | UP     | false    |  false      |
      | Beam Splitter | false     |   |   |        |          |             |
      | Checkpoint    | true      | 2 | 1 | UP     | false    |  false      |

    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 0 | 1 | DOWN  |
      | 0 | 2 | DOWN  |
      | 0 | 3 | DOWN  |
      | 0 | 4 | DOWN  |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 10 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 10, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 0 | 0 |        | true     |             |
      | Target Mirror | true      | 4 | 3 | LEFT   | false    |  true       |
      | Target Mirror | true      | 3 | 4 | UP     | false    |  true       |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Double Mirror | true      | 1 | 2 | UP     | false    |  false      |
      | Beam Splitter | false     |   |   |        |          |             |
      | Checkpoint    | true      | 2 | 1 | UP     | false    |  false      |

  # place token 0
    Given I place token 0 (from the required tokens) on the board at (2, 0)
    Then the token on the board at (2, 0) should be a Target Mirror token
    And I turn the Target Mirror token to face UP

  # place token 1
    And I place token 1 (from the required tokens) on the board at (2, 3)
    Then the token on the board at (2, 3) should be a Target Mirror token
    And I turn the Target Mirror token to face DOWN

  # place token 2
    Given I place token 2 (from the required tokens) on the board at (3, 2)
    Then the token on the board at (3, 2) should be a Target Mirror token
    And I turn the Target Mirror token to face UP

  # place token 3
    And I place token 3 (from the required tokens) on the board at (1, 0)
    Then the token on the board at (1, 0) should be a Beam Splitter token
    And I turn the Beam Splitter token to face UP

    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 1 | DOWN  |
      | 2 | 2 | DOWN  |
      | 2 | 2 | RIGHT |
      | 3 | 3 | DOWN  |
      | 3 | 3 | RIGHT |
    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 2
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board

       #level 11
  Scenario: Level 11 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 11, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 2 | 0 | DOWN   | false    |             |
      | Target Mirror | true      | 3 | 1 | LEFT   | false    |  true       |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Checkpoint    | true      | 3 | 2 |        | true     |  false      |
      | Double Mirror | true      | 0 | 3 | UP     | false    |  false      |


    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 2 | 1 | DOWN  |
      | 2 | 2 | DOWN  |
      | 2 | 3 | DOWN  |
      | 2 | 4 | DOWN  |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 11 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 11, required target number 1, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 2 | 0 | DOWN   | false    |             |
      | Target Mirror | true      | 3 | 1 | LEFT   | false    |  true       |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Checkpoint    | true      | 3 | 2 |        | true     |  false      |
      | Double Mirror | true      | 0 | 3 | UP     | false    |  false      |
  # place token 0
    Given I place token 0 (from the required tokens) on the board at (0, 1)
    Then the token on the board at (0, 1) should be a Target Mirror token
    And I turn the Target Mirror token to face LEFT

  # place token 1
    And I place token 1 (from the required tokens) on the board at (2, 2)
    Then the token on the board at (2, 2) should be a Target Mirror token
    And I turn the Target Mirror token to face DOWN

  # place token 2
    Given I place token 2 (from the required tokens) on the board at (4, 2)
    Then the token on the board at (4, 2) should be a Target Mirror token
    And I turn the Target Mirror token to face UP

  # place token 3
    And I place token 3 (from the required tokens) on the board at (4, 3)
    Then the token on the board at (4, 3) should be a Target Mirror token
    And I turn the Target Mirror token to face RIGHT

    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 0 | 2 | UP    |
      | 1 | 1 | RIGHT |
      | 2 | 1 | RIGHT |
      | 2 | 1 | DOWN  |
      | 1 | 3 | LEFT  |
      | 2 | 3 | LEFT  |
      | 3 | 3 | LEFT  |


    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 1
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board


       #level 12
  Scenario: Level 12 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 12, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 0 | 3 |        | true     |             |
      | Target Mirror | true      | 4 | 1 |        | true     |  true       |
      | Target Mirror | true      | 3 | 4 |        | true     |  true       |
      | Double Mirror | true      | 2 | 1 |        | true     |  false      |
      | Target Mirror | false     |   |   |        |          |             |
      | Beam Splitter | false     |   |   |        |          |             |
      | Cell Blocker  | true      | 2 | 3 |        |  false   |  false      |

    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 0 | 4 | DOWN  |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 10 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 10, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 0 | 3 |        | true     |             |
      | Target Mirror | true      | 4 | 1 |        | true     |  true       |
      | Target Mirror | true      | 3 | 4 |        | true     |  true       |
      | Double Mirror | true      | 2 | 1 |        | true     |  false      |
      | Target Mirror | false     |   |   |        |          |             |
      | Beam Splitter | false     |   |   |        |          |             |
      | Cell Blocker  | true      | 2 | 3 |        |  false   |  false      |

  # place token 0
    Given I place token 0 (from the required tokens) on the board at (0, 4)
    Then the token on the board at (0, 4) should be a Target Mirror token
    And I turn the Target Mirror token to face DOWN

  # place token 1
    And I place token 1 (from the required tokens) on the board at (2, 4)
    Then the token on the board at (2, 4) should be a Beam Splitter token
    And I turn the Beam Splitter token to face LEFT

    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 4 | RIGHT |
      | 2 | 2 | UP    |
      | 3 | 1 | RIGHT |

    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 2
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board


   #level 13
  Scenario: Level 13 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 13, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 2 | 3 |        | true     |             |
      | Target Mirror | true      | 0 | 2 | DOWN   | false    |  true       |
      | Target Mirror | true      | 0 | 3 |        | true     |  true       |
      | Double Mirror | true      | 0 | 4 | DOWN   | false    |  false      |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Beam Splitter | false     |   |   |        |          |             |
      | Double Mirror | true      | 4 | 4 | LEFT   |  false   |  false      |
      | Checkpoint    | true      | 2 | 1 |        |  true    |  false      |


    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 2 | 4 | DOWN  |
    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 13 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 13, required target number 2, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 2 | 3 |        | true     |             |
      | Target Mirror | true      | 0 | 2 | DOWN   | false    |  true       |
      | Target Mirror | true      | 0 | 3 |        | true     |  true       |
      | Double Mirror | true      | 0 | 4 | DOWN   | false    |  false      |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Beam Splitter | false     |   |   |        |          |             |
      | Double Mirror | true      | 4 | 4 | LEFT   |  false   |  false      |
      | Checkpoint    | true      | 2 | 1 |        |  true    |  false      |

  # place token 0
    Given I place token 0 (from the required tokens) on the board at (1, 1)
    Then the token on the board at (1, 1) should be a Target Mirror token
    And I turn the Target Mirror token to face LEFT

  # place token 1
    Given I place token 1 (from the required tokens) on the board at (4, 1)
    Then the token on the board at (4, 1) should be a Target Mirror token
    And I turn the Target Mirror token to face UP

  # place token 2
    And I place token 2 (from the required tokens) on the board at (1, 3)
    Then the token on the board at (1, 3) should be a Beam Splitter token
    And I turn the Beam Splitter token to face UP

    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 2 | UP    |
      | 3 | 1 | RIGHT |
      | 1 | 4 | LEFT  |
      | 2 | 4 | LEFT  |
      | 3 | 4 | LEFT  |
      | 4 | 2 | DOWN  |
      | 4 | 3 | DOWN  |

    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 2
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board

 #level 14
  Scenario: Level 14 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 14, required target number 3, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 4 | 2 | LEFT   | false    |             |
      | Target Mirror | true      | 4 | 1 |        | true     |  true       |
      | Target Mirror | false     |   |   |        |          |  true       |
      | Target Mirror | false     |   |   |        |          |  true       |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Beam Splitter | true      | 2 | 1 |        | true     |  false      |
      | Beam Splitter | true      | 1 | 4 |        | true     |  false      |


    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 3 | 2 | LEFT  |
      | 2 | 2 | LEFT  |
      | 1 | 2 | LEFT  |
      | 0 | 2 | LEFT  |

    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 14 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 14, required target number 3, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | true      | 4 | 2 | LEFT   | false    |             |
      | Target Mirror | true      | 4 | 1 |        | true     |  true       |
      | Target Mirror | false     |   |   |        |          |  true       |
      | Target Mirror | false     |   |   |        |          |  true       |
      | Target Mirror | false     |   |   |        |          |             |
      | Target Mirror | false     |   |   |        |          |             |
      | Beam Splitter | true      | 2 | 1 |        | true     |  false      |
      | Beam Splitter | true      | 1 | 4 |        | true     |  false      |

  # place token 0
    Given I place token 0 (from the required tokens) on the board at (1, 1)
    Then the token on the board at (1, 1) should be a Target Mirror token
    And I turn the Target Mirror token to face LEFT

  # place token 1
    Given I place token 1 (from the required tokens) on the board at (1, 2)
    Then the token on the board at (1, 2) should be a Target Mirror token
    And I turn the Target Mirror token to face DOWN

  # place token 2
    Given I place token 2 (from the required tokens) on the board at (0, 4)
    Then the token on the board at (0, 4) should be a Target Mirror token
    And I turn the Target Mirror token to face RIGHT

  # place token 3
    Given I place token 3 (from the required tokens) on the board at (2, 4)
    Then the token on the board at (2, 4) should be a Target Mirror token
    And I turn the Target Mirror token to face RIGHT

    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 1 | 3 | UP    |
      | 2 | 2 | DOWN  |
      | 2 | 3 | DOWN  |
      | 3 | 1 | RIGHT |
      | 3 | 2 | LEFT  |
      | 2 | 2 | LEFT  |


    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 3
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board


   #level 15
  Scenario: Level 15 is incomplete if any requirement is unmet
    Given a new game is started
    And the level is initialized with id 15, required target number 3, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | false     |   |   |        |          |             |
      | Target Mirror | true      | 1 | 3 | UP     | false    |  true       |
      | Target Mirror | true      | 2 | 2 | DOWN   | false    |  true       |
      | Target Mirror | true      | 4 | 0 |        | true     |  true       |
      | Target Mirror | true      | 3 | 4 |        | true     |  false      |
      | Target Mirror | false     |   |   |        |          |             |
      | Beam Splitter | true      | 1 | 0 |        | true     |  false      |
      | Beam Splitter | false     |   |   |        |          |             |


    When I activate the level's laser
    And the laser forms a beam path
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 0 | 1 | DOWN  |
      | 0 | 2 | DOWN  |
      | 0 | 3 | DOWN  |
      | 0 | 4 | DOWN  |


    And the number of targets hit by the beam path should be 0
    And the level should be incomplete

  Scenario: Level 15 is completed when all requirements are fulfilled
    Given a new game is started
    And the level is initialized with id 15, required target number 3, a board with width 5 and height 5, and the following tokens:
      | token         | preplaced | x | y | dir    | turnable | is required |
      | Laser         | false     |   |   |        |          |             |
      | Target Mirror | true      | 1 | 3 | UP     | false    |  true       |
      | Target Mirror | true      | 2 | 2 | DOWN   | false    |  true       |
      | Target Mirror | true      | 4 | 0 |        | true     |  true       |
      | Target Mirror | true      | 3 | 4 |        | true     |  false      |
      | Target Mirror | false     |   |   |        |          |             |
      | Beam Splitter | true      | 1 | 0 |        | true     |  false      |
      | Beam Splitter | false     |   |   |        |          |             |

  # place token 0
    Given I place token 0 (from the required tokens) on the board at (0, 0)
    Then the token on the board at (0, 0) should be a Laser token
    And I turn the Laser token to face RIGHT

  # place token 1
    Given I place token 1 (from the required tokens) on the board at (2, 4)
    Then the token on the board at (2, 4) should be a Target Mirror token
    And I turn the Target Mirror token to face DOWN

  # place token 2
    Given I place token 2 (from the required tokens) on the board at (1, 1)
    Then the token on the board at (1, 1) should be a Beam Splitter token
    And I turn the Beam Splitter token to face UP

    When I activate the level's laser
    And the laser forms a beam path
    Then the level should be complete
    Then the laser beam should pass through the following position directions:
      | x | y | dir   |
      | 2 | 0 | RIGHT |
      | 3 | 0 | RIGHT |
      | 2 | 1 | RIGHT |
      | 1 | 2 | DOWN  |
      | 2 | 3 | UP    |
      | 3 | 2 | DOWN  |
      | 3 | 3 | DOWN  |


    And the beam path should touch every touch-required token given by the level
    And the number of targets hit by the beam path should be 3
    And the beam path should hit all the required targets
    And the beam path should pass through all checkpoints
    And the level should be complete
    And all turnable tokens should have a direction
    And all tokens required to be placed should be placed on the board