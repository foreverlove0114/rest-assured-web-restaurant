Feature: User Profile Management
  As a registered user
  I want to manage my profile information
  So that I can keep my details up to date

  Background:
    Given the user is logged in

  Scenario: View user profile when authenticated
    When the user accesses the profile page
    Then the response status should be 200
    And the profile page should contain personal information
    And the user profile information should be displayed

  Scenario: Access profile without authentication
    When an unauthenticated user accesses the profile page
    Then the user should be redirected to login page

  Scenario: Profile page contains all user information
    Given the user is on the profile page
    Then the user profile information should be displayed
    And the change password form should be available

  @smoke
  Scenario: Smoke test - Profile access
    Given the user is logged in
    When the user accesses the profile page
    Then the response status should be 200
    And the profile page should contain personal information