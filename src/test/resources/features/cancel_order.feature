Feature: Cancel Order
  As a customer
  I want to cancel my orders
  So that I can manage my purchases

  Background:
    Given the user is logged in

  Scenario: Cancel order successfully
    Given the user has a placed order
    When the user cancels the order
    Then the order should be cancelled successfully

  Scenario: Cancel non-existent order
    When the user tries to cancel non-existent order "999999"
    Then the system should show order not found error

  Scenario: Cancel order that doesn't belong to user
    When the user tries to cancel non-existent order "999"
    Then the system should show order not found error

  Scenario: Order cancellation feedback
    Given the user has a placed order
    When the user cancels the order
    Then the response should contain "Order deleted!"

  @smoke
  Scenario: Smoke test - Order cancellation
    Given the user has a placed order
    When the user cancels the order
    Then the response status should be 200
    And the order should be cancelled successfully