Feature: Create Order
  As a customer
  I want to create orders from my cart
  So that I can purchase food items

  Scenario: View empty order page when cart is empty
    When an unauthenticated user accesses the create order page
    Then the empty cart message should be displayed
    And no order form should be displayed

  Scenario: Authenticated user with empty cart
    Given the user is logged in
    When the user accesses the create order page
    Then the empty cart message should be displayed

  Scenario: Create order with items in cart
    Given the user is logged in
    And the user has items in the cart
    When the user accesses the create order page
    Then the order form should be displayed
    And the cart items should be displayed
    When the user creates an order
    Then the order should be created successfully

  Scenario: Order creation process
    Given the user is logged in
    And the user has items in the cart
    When the user creates an order
    Then the order details should be displayed

  @smoke
  Scenario: Smoke test - Order creation
    Given the user is logged in
    And the user has items in the cart
    When the user creates an order
    Then the response status should be 200
    And the order should be created successfully