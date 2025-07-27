package com.shiyamtj.steps;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

public class StepDefinitions {
  private Response response;

  @Given("the API is available")
  public void the_api_is_available() {
    // Optionally check API health here
  }

  @When("I GET {string}")
  public void i_get_todos_1(String resourcePath) {
    response = given()
        .baseUri("https://jsonplaceholder.typicode.com")
        .when()
        .get(resourcePath);
  }

  @Then("the response status should be 200")
  public void the_response_status_should_be_200() {
    response.then().statusCode(200);
  }

  @Then("the response should contain id 1")
  public void the_response_should_contain_id_1() {
    response.then().body("id", equalTo(1));
  }
}
