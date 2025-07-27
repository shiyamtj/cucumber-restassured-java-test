package com.shiyamtj.steps;

import static org.hamcrest.Matchers.*;
import io.cucumber.java.en.*;

public class StepDefinitions {
  @Given("I set path param {string} to {string}")
  public void i_set_path_param(String name, String value) {
    apiHelper.setPathParam(name, value);
  }

  @Given("I set query param {string} to {string}")
  public void i_set_query_param(String name, String value) {
    apiHelper.setQueryParam(name, value);
  }

  @Given("I set header {string} to {string}")
  public void i_set_header(String name, String value) {
    apiHelper.setHeader(name, value);
  }

  @Given("I set body to:")
  public void i_set_body(String body) {
    apiHelper.setBody(body);
  }

  private final ApiRequestHelper apiHelper = new ApiRequestHelper();

  @Given("the API is available")
  public void the_api_is_available() {
    // apiHelper.sendRequest("GET", "/health");
    // apiHelper.getLastResponse().then().statusCode(200);
  }

  @When("I send a {string} request to {string}")
  public void i_send_request(String method, String resourcePath) {
    apiHelper.sendRequest(method, resourcePath);
  }

  @When("I send a {string} request to {string} with body:")
  public void i_send_request_with_body(String method, String resourcePath, String body) {
    apiHelper.setBody(body);
    apiHelper.sendRequest(method, resourcePath);
  }

  @Then("the response status should be {int}")
  public void the_response_status_should_be(int statusCode) {
    apiHelper.getLastResponse().then().statusCode(statusCode);
  }

  @Then("the response should contain {string} {string}")
  public void the_response_should_contain_key_value(String key, String value) {
    apiHelper.getLastResponse().then().body(key, equalTo(parseValue(value)));
  }

  // Helper to parse value as int if possible, else as string
  private Object parseValue(String value) {
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return value;
    }
  }
}
