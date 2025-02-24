Feature: Retrieve Pet Details from Petstore API

  Background:
    Given API base URL is set
    And Authorization token is provided

  @functional @get
  Scenario Outline: Retrieve pet details successfully
    Given a pet with ID "<petId>" exists
    When a GET request is sent to "/pet/<petId>"
    Then the response status code should be 200
    And the response should contain pet ID "<petId>"

    Examples:
      | petId |
      | 1     |
      | 5     |
      | 10    |

  @negative @get
  Scenario Outline: Retrieve pet details for non-existing ID
    When a GET request is sent to "/pet/<petId>"
    Then the response status code should be 404
    And the response should contain "Pet not found"

    Examples:
      | petId  |
      | 99999  |
      | 88888  |
