Feature: User Registration
  As a new user
  I want to register for an account
  So that I can use the restaurant services

  Scenario: Successful registration with valid data
    Given I am on the registration page
    When I register with valid user details
    Then I should be redirected to the home page

  Scenario: Registration page accessibility
    Given I am on the registration page
    Then the response status should be 200
    And the response should contain "Create Account"

  Scenario: Registration with existing user
    Given I am on the registration page
    When I register with valid user details
    Then the response should contain "already exists"

  Scenario: CSRF token protection
    Given I am on the registration page
    Then the response should contain "csrf_token"

  @smoke
  Scenario: Smoke test - User registration
    Given the base URL is configured
    And I am on the registration page
    Then the response status should be 200
    And the response should contain "Create Account"