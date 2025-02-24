Feature: Add a new pet to Petstore API

  Background:
    Given API base URL is set
    And Authorization token is provided

  @functional @post
  Scenario: Add a new pet successfully
    Given the following pet details:
      | name  | status     |
      | Rocky | available  |
    When a POST request is sent to "/pet"
    Then the response status code should be 200
    And the response should contain the pet name "Rocky"

  @negative @post
  Scenario: Try to add a new pet with invalid data
    Given the following invalid pet details:
      | name | status  |
      | ""   | unknown |
    When a POST request is sent to "/pet"
    Then the response status code should be 400
    And the response should contain "Invalid input"
