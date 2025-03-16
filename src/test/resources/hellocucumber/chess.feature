Feature: Play Chess
  Scenario: Play a game until check
    Given I have a new game of chess
    When I play the game to a check
    Then I should be notified of a check
    And a white piece should threaten the black king
  Scenario: Play a game until checkmate
    Given I have a new game of chess
    When I play the game to a checkmate
    Then I should be notified of a checkmate
    And a white piece should threaten the black king
    And the black king should have no valid moves
  Scenario: Move a single piece
    Given I have a new game of chess
    When I move a pawn
    Then I should be notified of a piece move
  Scenario: Take a single piece
    Given I have a new game of chess
    When I move opposing pawns and capture the black one
    Then I should be notified of a piece taken