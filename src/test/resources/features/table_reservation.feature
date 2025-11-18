Feature: Table Reservation
  As a customer
  I want to reserve a table
  So that I can ensure seating availability

  Scenario: Access reservation page without login
    When an unauthenticated user accesses the reservation page
    Then the reservation page should require login

  Scenario: Access reservation page with login
    Given the user is logged in
    When the user accesses the reservation page
    Then the reservation page should be displayed

  Scenario: Make successful reservation
    Given the user is logged in
    When the user makes a reservation for a "2" table
    Then the reservation should be created successfully

  Scenario: Reservation form elements
    Given the user is logged in
    When the user accesses the reservation page
    Then the reservation form should contain table type options
    And the reservation form should contain datetime picker

  Scenario: Prevent multiple active reservations
    Given the user is logged in
    When the user tries to make multiple reservations
    Then the system should show only one active reservation message

  Scenario: Table availability check
    Given the user is logged in
    When the user makes a reservation with current time
    Then the system should show table not available message

  Scenario: Reservation for different table types
    Given the user is logged in
    When the user makes a reservation for a "4" table
    Then the reservation should be created successfully

  @smoke
  Scenario: Smoke test - Table reservation
    Given the user is logged in
    When the user accesses the reservation page
    Then the response status should be 200
    And the reservation page should be displayed