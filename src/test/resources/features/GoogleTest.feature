@Google Test
@other_meta_tag
Feature: Google HomePage Test
  This is an example to test Google HomePage.


  Scenario: Verify Google Home Page
    Given I'm on https://www.google.co.in/
    When I would search for element header
    Then I might see this element
