package com.shiyamtj.steps;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import java.io.PrintStream;
import java.io.FileOutputStream;

public class ApiRequestHelper {
  public void setPathParam(String name, String value) {
    requestSpec.pathParam(name, value);
  }

  public void setQueryParam(String name, String value) {
    requestSpec.queryParam(name, value);
  }

  public void setHeader(String name, String value) {
    requestSpec.header(name, value);
  }

  public void setParam(String name, String value) {
    requestSpec.param(name, value);
  }

  public void setBody(String body) {
    requestSpec.body(body);
  }

  private static PrintStream logStream;
  static {
    try {
      logStream = new PrintStream(new FileOutputStream("restassured-http.log", false)); // overwrite each run
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static final String BASE_URI = loadBaseUri();
  private Response lastResponse;
  private RequestSpecification requestSpec = given()
      .baseUri(BASE_URI)
      .filter(new RequestLoggingFilter(logStream))
      .filter(new ResponseLoggingFilter(logStream));

  private static String loadBaseUri() {
    Properties props = new Properties();
    try (InputStream input = ApiRequestHelper.class.getClassLoader()
        .getResourceAsStream("com/shiyamtj/config.properties")) {
      if (input != null) {
        props.load(input);
        String url = props.getProperty("base.url");
        if (url != null && !url.isEmpty()) {
          return url;
        } else {
          throw new IllegalStateException("base.url property must be set in config.properties");
        }
      } else {
        throw new IllegalStateException("config.properties file not found in resources");
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to load config.properties", e);
    }
  }

  public Response sendRequest(String method, String resourcePath) {
    switch (method.toUpperCase()) {
      case "GET":
        lastResponse = requestSpec.when().get(resourcePath);
        break;
      case "POST":
        lastResponse = requestSpec.when().post(resourcePath);
        break;
      case "PUT":
        lastResponse = requestSpec.when().put(resourcePath);
        break;
      case "PATCH":
        lastResponse = requestSpec.when().patch(resourcePath);
        break;
      case "DELETE":
        lastResponse = requestSpec.when().delete(resourcePath);
        break;
      default:
        throw new IllegalArgumentException("Unsupported HTTP method: " + method);
    }
    // Reset requestSpec and logs for next request
    requestSpec = given()
        .baseUri(BASE_URI)
        .filter(new RequestLoggingFilter(logStream))
        .filter(new ResponseLoggingFilter(logStream));
    return lastResponse;
  }

  public Response getLastResponse() {
    return lastResponse;
  }
}
