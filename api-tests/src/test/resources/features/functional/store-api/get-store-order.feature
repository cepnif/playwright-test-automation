Feature: Retrieve Store Order using GET request

  Background:
    Given the store API is available

  Scenario: Successfully retrieve an order by ID using GET request
    Given an existing order with ID 101
    When a GET request is sent to retrieve the order
    Then the response status code should be 200
    And the order details should be displayed

  Scenario Outline: Attempt to retrieve a non-existing order using GET request
    Given an order ID "<orderId>" that does not exist
    When a GET request is sent to retrieve the order
    Then the response status code should be 404
    And an error message "Order not found" should be returned

    Examples:
      | orderId |
      | 99999   |
      | abc     |
