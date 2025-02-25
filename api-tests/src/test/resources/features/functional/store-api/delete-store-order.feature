Feature: Delete Store Order using DELETE request

  Background:
    Given the store API is available

  Scenario: Successfully delete an order using DELETE request
    Given an existing order with ID 102
    When a DELETE request is sent to remove the order
    Then the response status code should be 200
    And the order should no longer exist in the system

  Scenario Outline: Attempt to delete an invalid order using DELETE request
    Given an order ID "<orderId>" that does not exist
    When a DELETE request is sent to remove the order
    Then the response status code should be 404
    And an error message "Order not found" should be returned

    Examples:
      | orderId |
      | 99999   |
      | -1      |
      | xyz     |
