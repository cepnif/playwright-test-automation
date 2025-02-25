Feature: Place an Order using POST request

  Background:
    Given the store API is available

  Scenario: Successfully place an order using POST request
    Given the following order details:
      | petId  | quantity | shipDate            | status  | complete |
      | 1001   | 2        | 2025-02-25T10:00:00 | placed  | true     |
    When a POST request is sent to place the order
    Then the response status code should be 200
    And the response should contain order details with status "placed"

  Scenario Outline: Attempt to place an order with invalid details using POST request
    Given an order request with invalid "<field>" and "<value>"
    When a POST request is sent to place the order
    Then the response status code should be 400
    And an error message "<errorMessage>" should be returned

    Examples:
      | field    | value | errorMessage                 |
      | petId    | null  | Pet ID is required          |
      | quantity | -5    | Quantity must be positive   |
      | status   | xyz   | Invalid status value        |
