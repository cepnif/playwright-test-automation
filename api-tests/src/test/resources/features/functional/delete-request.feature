Feature: Delete a pet from Petstore API

  Background:
    Given API base URL is set
    And Authorization token is provided

  @functional @delete
  Scenario Outline: Delete a pet successfully
    Given a pet with ID "<petId>" exists
    When a DELETE request is sent to "/pet/<petId>"
    Then the response status code should be 200
    And the response should contain message "Pet deleted"

    Examples:
      | petId |
      | 2     |
      | 3     |

  @negative @delete
  Scenario Outline: Try to delete a pet that does not exist
    When a DELETE request is sent to "/pet/<petId>"
    Then the response status code should be 404
    And the response should contain "Pet not found"

    Examples:
      | petId  |
      | 77777  |
      | 66666  |
