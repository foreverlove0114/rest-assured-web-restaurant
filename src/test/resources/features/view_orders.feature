Feature: View Orders
  As a customer
  I want to view my order history
  So that I can track my purchases

  Background:
    Given the user is logged in

  Scenario: View order list
    When the user views order list
    Then the order list should be displayed

  Scenario: View order details
    Given the user has a placed order
    When the user views order details for order "1"
    Then the order details should be displayed

  Scenario: Order list contains order information
    When the user views order list
    Then the response should contain "Your Orders"
    And the response should contain "View Details"

  Scenario: Order details contain complete information
    Given the user has a placed order
    When the user views order details for order "1"
    Then the response should contain "Items List:"
    And the response should contain "Date & Time"
    And the response should contain "Cancel Order"

  @regression
  Scenario: Regression - Order history persistence
    Given the user has a placed order
    When the user views order list
    Then the order list should be displayed
    And the response should contain "View Details"