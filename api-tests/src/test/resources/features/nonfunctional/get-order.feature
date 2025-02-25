Feature: Validate response time for retrieving an order using GET request

  Background:
    Given the store order API is available

  Scenario: Ensure order retrieval completes within 500ms
    Given an order exists with order ID "105"
    When a GET request is sent to retrieve the order
    Then the response status should be 200
    And the response time should be less than 500ms
