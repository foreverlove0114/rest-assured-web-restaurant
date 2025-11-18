Feature: Main Routes Accessibility
  As a website visitor
  I want to access main pages
  So that I can learn about the restaurant

  Scenario: Home page accessibility
    When I send a GET request to "/"
    Then the response status should be 200
    And the response should contain "Delicious dishes delivered straight to your home"

  Scenario: About page accessibility
    When I send a GET request to "/about"
    Then the response status should be 200
    And the response should contain "About Flask & Feats"

  Scenario: Contact page GET
    When I send a GET request to "/contact"
    Then the response status should be 200
    And the response should contain "Send Us a Message"

  Scenario: Contact page POST
    When I send a POST request to "/contact"
    Then the response status should be 200
    And the response should contain "We are always happy to hear from you"

  @smoke
  Scenario: Smoke test - Main routes
    Given the base URL is configured
    When I send a GET request to "/"
    Then the response status should be 200
    And the response should contain "Delicious dishes"