package com.restaurant.stepdefinitions;

import com.restaurant.utils.ConfigManager;
import com.restaurant.utils.SessionManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CommonStepDefinitions {

    private final SessionManager sessionManager;
    private Response response;

    public CommonStepDefinitions(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // 无参构造器
    public CommonStepDefinitions() {
        this.sessionManager = new SessionManager();
    }

    @Given("the base URL is configured")
    public void base_url_is_configured() {
        assertNotNull(ConfigManager.getBaseUrl());
        assertFalse(ConfigManager.getBaseUrl().isEmpty());
    }

    @When("I send a GET request to {string}")
    public void send_get_request(String endpoint) {
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + endpoint);
    }

    @When("I send a POST request to {string}")
    public void send_post_request(String endpoint) {
        response = sessionManager.getUnauthenticatedRequest()
                .post(ConfigManager.getBaseUrl() + endpoint);
    }

    @Then("the response status should be {int}")
    public void response_status_should_be(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Then("the response should contain {string}")
    public void response_should_contain(String expectedText) {
        response.then().body(containsString(expectedText));
    }

    @Then("the response should not contain {string}")
    public void response_should_not_contain(String unexpectedText) {
        response.then().body(not(containsString(unexpectedText)));
    }

    @Then("the response content type should be {string}")
    public void response_content_type_should_be(String contentType) {
        response.then().contentType(containsString(contentType));
    }
}