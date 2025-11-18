Feature: User Login
  As a registered user
  I want to login to the system
  So that I can access my account features

  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I login with username "testuser_1234" and password "test123"
    Then I should see the profile page

  Scenario: Failed login with invalid credentials
    Given I am on the login page
    When I login with username "wronguser" and password "wrongpass"
    Then the response should contain "Incorrect nickname or password!"

  Scenario: Login page accessibility
    Given I am on the login page
    Then the response status should be 200
    And the response should contain "Glad to see you again!"

  Scenario: Redirect after login
    Given I am on the login page
    When I login with username "testuser_1234" and password "test123"
    Then I should be redirected to the home page

  @smoke
  Scenario: Smoke test - User login
    Given the base URL is configured
    And I am on the login page
    When I login with username "testuser_1234" and password "test123"
    Then the response status should be 200
    And I should see the profile page