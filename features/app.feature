Feature: Search feature

  Scenario: As a user I can tap on the search icon and trigger a search after typing a text
    When I see the text "To begin"
    Then I press view with id "search"
    Then I enter text "art" into field with id "search_src_text"
    Then I press the enter button
    Then I wait for the view with id "list" to appear
    Then I wait for 10 seconds
    Then I take a picture