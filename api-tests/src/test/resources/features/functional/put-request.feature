Feature: Update Pet Details in Petstore API

  Background:
    Given API base URL is set
    And Authorization token is provided

  @functional @put
  Scenario: Update pet details successfully
    Given an existing pet with ID "1"
    And the pet's status is changed to "sold"
    When a PUT request is sent to "/pet"
    Then the response status code should be 200
    And the response should contain the updated status "sold"

  @negative @put
  Scenario: Update a pet that does not exist
    Given a pet with ID "99999" does not exist
    When a PUT request is sent to "/pet"
    Then the response status code should be 404
    And the response should contain "Pet not found"
