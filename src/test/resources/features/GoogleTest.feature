#@Author Gladson Antony
#@Date 14-Mar-2018 
#@Time 9:42:43 PM

 
@Google_Test

Feature: Google HomePage Test
  This is an example to test Google HomePage.


  Scenario: Verify Google Home Page
    Given I'm on Google
    When I would search for element header
    Then I might see this element
