Feature: User Logout
  As a logged-in user
  I want to logout from the system
  So that I can securely end my session

  Background:
    Given the user is logged in

  Scenario: Successful logout
    When I logout
    Then I should see login and register options

  Scenario: Access after logout
    Given the user is logged in
    When I logout
    And I access the profile page
    Then I should be redirected to login page

  Scenario: Session cleanup after logout
    Given the user is logged in
    When I logout
    And I send a GET request to "/profile"
    Then the response should contain "Login"
    And the response should contain "Register"

  @smoke
  Scenario: Smoke test - User logout
    Given the user is logged in
    When I logout
    Then the response status should be 200
    And I should see login and register options