Feature: Admin User Management
  As an administrator
  I want to view all users
  So that I can manage user accounts

  Background:
    Given the admin is logged in

  Scenario: View all users page
    When the admin accesses the all users page
    Then the all users page should be accessible

  Scenario: Users list contains user information
    When the admin accesses the all users page
    Then the response should contain "Users"
    And the response should contain user details

  Scenario: User management page accessibility
    When the admin accesses the all users page
    Then the response status should be 200
    And the all users page should be accessible

  Scenario: Admin privileges verification
    When the admin accesses the all users page
    Then the response should contain user information
    And the response should not contain "Access denied"

  @security
  Scenario: Regular user cannot view all users
    Given the user is logged in
    When the user accesses the all users page
    Then the user should see access denied for admin pages

  @smoke
  Scenario: Smoke test - Admin user management
    Given the admin is logged in
    When the admin accesses the all users page
    Then the response status should be 200
    And the all users page should be accessible