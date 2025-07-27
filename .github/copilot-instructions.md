# Copilot Instructions for AI Agents

## Project Overview

- This is a Maven-based Java project. The main source code is under `src/main/java/com/shiyamtj/` and tests are under `src/test/java/com/shiyamtj/`.
- The project appears to be a simple Java application, currently with a single test class (`AppTest.java`).

## Build & Test Workflows

- **Build the project:**
  ```sh
  mvn clean compile
  ```
- **Run tests:**
  ```sh
  mvn test
  ```
- **Compiled classes:**
  - Main classes: `target/classes/com/shiyamtj/`
  - Test classes: `target/test-classes/com/shiyamtj/`
- **Test reports:**
  - Located in `target/surefire-reports/`

## Conventions & Patterns

**Testing:**

- Uses JUnit 5 (`org.junit.jupiter.api.Test`).
- Test classes are named `*Test.java` and placed in the corresponding package under `src/test/java/`.
- Example test: see `AppTest.java` for structure (use `org.junit.jupiter.api.Test` and `org.junit.jupiter.api.Assertions`).
- **Package structure:**
  - All code is under the `com.shiyamtj` package.
- **No custom build or test scripts** are present; use standard Maven commands.

## Integration & Dependencies

- **Dependencies:**
  - Managed via `pom.xml`. Add new dependencies there and run `mvn install` to update.
  - **Rest Assured** (for API testing):
    ```xml
    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>5.4.0</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>json-path</artifactId>
      <version>5.4.0</version>
      <scope>test</scope>
    </dependency>
    <!-- Optional: for XML API testing -->
    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>xml-path</artifactId>
      <version>5.4.0</version>
      <scope>test</scope>
    </dependency>
    ```
- **Cucumber BDD (Behavior-Driven Development):**
  - Cucumber is integrated for BDD-style API and integration tests.
  - Feature files: `src/test/resources/com/shiyamtj/*.feature` (Gherkin syntax).
  - Step definitions: `src/test/java/com/shiyamtj/StepDefinitions.java` (or similar).
  - Test runner: `RunCucumberTest.java` uses JUnit 5 suite annotations and configures Cucumber plugins.
  - Built-in Cucumber reports:
    - JSON: `target/cucumber-report.json`
    - HTML: `target/cucumber-report.html` (open in browser after test run)
  - Example runner config:
    ```java
    @Suite
    @SelectClasspathResource("com/shiyamtj")
    @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.shiyamtj")
    @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber-report.json, html:target/cucumber-report.html")
    public class RunCucumberTest {}
    ```

## Dependencies Overview

- **JUnit 5 (`org.junit.jupiter:junit-jupiter`)**: Main unit testing framework. All new tests should use JUnit 5 annotations and assertions.
- **Rest Assured (`io.rest-assured:rest-assured`, `json-path`, `xml-path`)**: For API (HTTP/REST) testing. Use `rest-assured` for JSON APIs, `json-path` for JSON parsing, and `xml-path` for XML API testing if needed.
- **Cucumber (`io.cucumber:cucumber-java`, `cucumber-junit-platform-engine`)**: Enables BDD-style tests with Gherkin feature files and Java step definitions. Integrates with JUnit 5 for running scenarios as tests.
- **JUnit Platform Suite (`org.junit.platform:junit-platform-suite`)**: Allows running Cucumber features with JUnit 5 suite annotations.

All dependencies are declared in `pom.xml` with `<scope>test</scope>`, so they are only included for testing and not in production builds.

Example dependency block from `pom.xml`:

```xml
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.10.2</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>io.rest-assured</groupId>
  <artifactId>rest-assured</artifactId>
  <version>5.4.0</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>io.cucumber</groupId>
  <artifactId>cucumber-java</artifactId>
  <version>7.16.1</version>
  <scope>test</scope>
</dependency>
<!-- ...other dependencies... -->
```

To add or update dependencies, edit `pom.xml` and run `mvn install` to fetch them.

## Key Files & Directories

- `pom.xml`: Maven configuration and dependencies.
- `src/main/java/com/shiyamtj/`: Main application code.
- `src/test/java/com/shiyamtj/`: Test code.
- `target/`: Build output and reports.

## Example: Adding a New Test

1. **JUnit 5 test:**
   - Create a new file in `src/test/java/com/shiyamtj/`, e.g., `MyFeatureTest.java`.
   - Use the following template:
     ```java
     package com.shiyamtj;
     import org.junit.jupiter.api.Test;
     import static org.junit.jupiter.api.Assertions.*;
     public class MyFeatureTest {
         @Test
         public void testSomething() {
             assertTrue(true);
         }
     }
     ```
   - Run `mvn test` to execute.
2. **Cucumber BDD test:**
   - Add a feature file in `src/test/resources/com/shiyamtj/`, e.g., `example.feature`:
     ```gherkin
     Feature: Example API
       Scenario: Get a todo by id
         Given the API is available
         When I GET "/todos/1"
         Then the response status should be 200
         And the response should contain id 1
     ```
   - Add step definitions in `src/test/java/com/shiyamtj/StepDefinitions.java`:
     ```java
     package com.shiyamtj;
     import static io.restassured.RestAssured.*;
     import static org.hamcrest.Matchers.*;
     import io.cucumber.java.en.*;
     import io.restassured.response.Response;
     public class StepDefinitions {
         private Response response;
         @Given("the API is available")
         public void the_api_is_available() {}
         @When("I GET \"/todos/1\"")
         public void i_get_todos_1(String resourcePath) {
             response = given().baseUri("https://jsonplaceholder.typicode.com").when().get(resourcePath);
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
     ```
   - Run `mvn test` or `mvn verify` to execute and generate reports.

---

If you add new conventions or workflows, update this file to help future AI agents and developers.
