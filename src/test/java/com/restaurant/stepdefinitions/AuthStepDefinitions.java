package com.restaurant.stepdefinitions;

import com.restaurant.utils.ConfigManager;
import com.restaurant.utils.SessionManager;
import com.restaurant.models.User;
import com.restaurant.utils.TestDataGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

public class AuthStepDefinitions {

    private final SessionManager sessionManager;
    private Response response;
    private User testUser;

    public AuthStepDefinitions(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Given("I am on the registration page")
    public void on_registration_page() {
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/register");
    }

    @Given("I am on the login page")
    public void on_login_page() {
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/login");
    }

    @When("I register with valid user details")
    public void register_with_valid_details() {
        testUser = TestDataGenerator.generateUniqueUser();
        String csrfToken = sessionManager.getCsrfToken();

        response = sessionManager.getUnauthenticatedRequest()
                .formParam("nickname", testUser.getNickname())
                .formParam("email", testUser.getEmail())
                .formParam("contact", testUser.getContact())
                .formParam("fullAddress", testUser.getFullAddress())
                .formParam("password", testUser.getPassword())
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/register");
    }

    @When("I login with username {string} and password {string}")
    public void login_with_credentials(String username, String password) {
        sessionManager.login(username, password);
    }

    @When("I logout")
    public void logout() {
        sessionManager.logout();
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/");
    }

    @Then("I should be redirected to the home page")
    public void should_be_redirected_to_home() {
        response.then()
                .body(containsString("Delicious dishes"))
                .body(containsString("delivered straight to your home"));
    }

    @Then("I should see the profile page")
    public void should_see_profile_page() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/profile");

        response.then()
                .body(containsString("Personal information"))
                .body(containsString("Your Profile"));
    }

    @Then("I should see login and register options")
    public void should_see_login_register_options() {
        response.then()
                .body(containsString("Login"))
                .body(containsString("Register"));
    }
}