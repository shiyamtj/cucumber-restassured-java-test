Feature: Example API

  Scenario: Get a todo by id
    Given the API is available
    When I GET "/todos/1"
    Then the response status should be 200
    And the response should contain id 1
