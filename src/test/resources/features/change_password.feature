Feature: Change Password
  As a registered user
  I want to change my password
  So that I can maintain account security

  Background:
    Given the user is logged in

  Scenario: Change password successfully
    When the user changes password from "test123" to "newtest123"
    Then the password change should be successful
    And the user changes password from "newtest123" to "test123"

  Scenario: Change password with incorrect current password
    When the user changes password with wrong current password
    Then the system should display password mismatch error

  Scenario: Password change form availability
    Given the user is on the profile page
    Then the change password form should be available

  Scenario: Update address information
    When the user updates address to "123 New Street"
    Then the address should be updated successfully

  Scenario: Update contact information
    When the user updates contact information
    Then the contact information should be updated successfully

  @security
  Scenario: Security - Password change requires authentication
    When an unauthenticated user accesses the profile page
    Then the user should be redirected to login page