Feature: Add to Cart
  As a customer
  I want to add items to my cart
  So that I can prepare for ordering

  Scenario: Add item to cart without login
    Given there is a product available in the menu
    When I try to add product 2 to cart without logging in
    Then I should be prompted to log in

  Scenario: Add item to cart with login
    Given the user is logged in
    And there is a product available in the menu
    When I add product 2 to cart
    Then the product should be added to cart successfully

  Scenario: Cart functionality requires authentication
    When I try to add product 2 to cart without logging in
    Then the response should contain "please log in first"

  Scenario: Successful add to cart feedback
    Given the user is logged in
    When I add product 2 to cart
    Then the response should contain "Item added to cart!"

  Scenario: Multiple items to cart
    Given the user is logged in
    When I add product 2 to cart
    Then the product should be added to cart successfully
    When I add product 3 to cart
    Then the product should be added to cart successfully

  @regression
  Scenario: Regression - Cart persistence
    Given the user is logged in
    When I add product 2 to cart
    Then the product should be added to cart successfully
    And the user accesses the create order page
    Then the cart items should be displayed