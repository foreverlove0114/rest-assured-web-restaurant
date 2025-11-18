Feature: Admin Menu Management
  As an administrator
  I want to manage the restaurant menu
  So that I can keep the menu up to date

  Background:
    Given the admin is logged in

  Scenario: Add new menu item successfully
    When the admin accesses the add position page
    Then the add position page should be accessible
    When the admin adds a new menu item with valid data
    Then the menu item should be added successfully

  Scenario: Add menu item without image
    When the admin accesses the add position page
    Then the add position page should be accessible
    When the admin tries to add a menu item without image
    Then the system should show no file selected error

  Scenario: Edit existing menu item
    When the admin accesses the edit position page for item 2
    Then the edit position page should be accessible
    When the admin edits menu item 2 with new data
    Then the menu item should be edited successfully

  Scenario: Access menu check page
    When the admin accesses the menu check page
    Then the menu check page should be accessible

  Scenario: Change menu item status
    When the admin accesses the menu check page
    Then the menu check page should be accessible
    When the admin changes status of menu item 10
    Then the menu item status should be changed

  Scenario: Delete menu item
    When the admin accesses the menu check page
    Then the menu check page should be accessible
    When the admin deletes menu item 10
    Then the menu item should be deleted

  @security
  Scenario: Regular user cannot access admin menu functions
    Given the user is logged in
    When the user accesses the add position page
    Then the user should see access denied for admin pages