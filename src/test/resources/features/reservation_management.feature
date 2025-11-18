Feature: Admin Reservation Management
  As an administrator
  I want to manage table reservations
  So that I can optimize table usage

  Background:
    Given the admin is logged in

  Scenario: View reservations check page
    When the admin accesses the reservations check page
    Then the reservations check page should be accessible

  Scenario: Delete reservation
    When the admin accesses the reservations check page
    Then the reservations check page should be accessible
    When the admin deletes a reservation
    Then the reservation should be deleted successfully

  Scenario: Reservations page shows booking information
    When the admin accesses the reservations check page
    Then the response should contain "Checking reservations"

  Scenario: Reservation management interface
    When the admin accesses the reservations check page
    Then the response should contain table elements
    And the response should contain reservation details

  @security
  Scenario: Regular user cannot manage reservations
    Given the user is logged in
    When the user accesses the reservations check page
    Then the user should see access denied for admin pages