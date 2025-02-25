@non-functional
Feature: Load testing for order deletion using DELETE request

  Background:
    Given the store order API is available
    And 100 valid orders exist in the system

  Scenario: Ensure the API handles concurrent deletion requests efficiently
    When 100 DELETE requests are sent simultaneously for different order IDs
    Then all responses should return status 200
    And there should be no residual order data in the system
