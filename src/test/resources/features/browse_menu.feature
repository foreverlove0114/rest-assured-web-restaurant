Feature: Browse Menu
  As a customer
  I want to browse the restaurant menu
  So that I can see available food options

  Scenario: View menu page
    When I view the menu
    Then the menu page should be displayed

  Scenario: Menu page contains food items
    When I view the menu
    Then the response should contain "Our Menu"
    And the product price should be displayed

  Scenario: View product details
    Given there is a product available in the menu
    When I view the details of a random product
    Then the product details page should be displayed
    And the product image should be displayed
    And the product price should be displayed

  Scenario: Product details page content
    When I view the details of product 2
    Then the product details page should be displayed
    And the response should contain "Ingredients:"
    And the response should contain "Description:"

  Scenario: Menu accessibility without login
    When I view the menu
    Then the response status should be 200
    And the menu page should be displayed

  @smoke
  Scenario: Smoke test - Menu browsing
    Given the base URL is configured
    When I view the menu
    Then the response status should be 200
    And the menu page should be displayed