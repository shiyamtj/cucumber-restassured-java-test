Feature: Example API

  Scenario: Get a todo by id
    Given the API is available
    When I send a "GET" request to "/todos/1"
    Then the response status should be 200
    And the response should contain "id" "1"

  Scenario: Get all posts
    Given the API is available
    When I send a "GET" request to "/posts"
    Then the response status should be 200

  Scenario: Create a new post
    Given the API is available
    When I send a "POST" request to "/posts" with body:
      """
      {"title": "foo", "body": "bar", "userId": 1}
      """
    Then the response status should be 201
    And the response should contain "id" "101"

  Scenario: Update a post
    Given the API is available
    When I send a "PUT" request to "/posts/1" with body:
      """
      {"id": 1, "title": "updated", "body": "baz", "userId": 1}
      """
    Then the response status should be 200
    And the response should contain "id" "1"

  Scenario: Patch a post
    Given the API is available
    When I send a "PATCH" request to "/posts/1" with body:
      """
      {"title": "patched"}
      """
    Then the response status should be 200
    And the response should contain "userId" "1"

  Scenario: Delete a post
    Given the API is available
    When I send a "DELETE" request to "/posts/1"
    Then the response status should be 200
