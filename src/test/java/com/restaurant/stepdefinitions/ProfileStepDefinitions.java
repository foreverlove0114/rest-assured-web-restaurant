package com.restaurant.stepdefinitions;

import com.restaurant.utils.ConfigManager;
import com.restaurant.utils.SessionManager;
import com.restaurant.utils.CSRFTokenExtractor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ProfileStepDefinitions {

    private final SessionManager sessionManager;
    private Response response;

    public ProfileStepDefinitions(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // 无参构造器
    public ProfileStepDefinitions() {
        this.sessionManager = new SessionManager();
    }

    @Given("the user is on the profile page")
    public void user_on_profile_page() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/profile");
    }

    @When("the user accesses the profile page")
    public void user_accesses_profile_page() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/profile");
    }

    @When("an unauthenticated user accesses the profile page")
    public void unauthenticated_user_accesses_profile_page() {
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/profile");
    }

    @When("the user changes password from {string} to {string}")
    public void user_changes_password(String oldPassword, String newPassword) {
        String csrfToken = sessionManager.getCsrfToken();

        response = sessionManager.getAuthenticatedRequest()
                .formParam("oldpassword", oldPassword)
                .formParam("newpassword", newPassword)
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/change-password");
    }

    @When("the user changes password with wrong current password")
    public void user_changes_password_wrong_current() {
        String csrfToken = sessionManager.getCsrfToken();

        response = sessionManager.getAuthenticatedRequest()
                .formParam("oldpassword", "wrongpassword")
                .formParam("newpassword", "newtest123")
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/change-password");
    }

    @When("the user updates address to {string}")
    public void user_updates_address(String newAddress) {
        String csrfToken = sessionManager.getCsrfToken();

        response = sessionManager.getAuthenticatedRequest()
                .formParam("new_address", newAddress)
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/change-address");
    }

    @When("the user updates contact information")
    public void user_updates_contact_info() {
        String csrfToken = sessionManager.getCsrfToken();

        response = sessionManager.getAuthenticatedRequest()
                .formParam("new_contact", "0987654321")
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/change-contact");
    }

    @Then("the profile page should contain personal information")
    public void profile_page_contains_personal_info() {
        response.then()
                .statusCode(200)
                .body(containsString("Personal information"))
                .body(containsString("Manage your personal information and settings"))
                .body(containsString("Your Profile"));
    }

    @Then("the user should be redirected to login page")
    public void user_redirected_to_login_page() {
        response.then()
                .statusCode(200)
                .body(anyOf(
                        containsString("Account Login"),
                        containsString("Please log in to access this page")
                ));
    }

    @Then("the password change should be successful")
    public void password_change_successful() {
        response.then()
                .statusCode(200)
                .body(containsString("Password changed successfully!"));
    }

    @Then("the system should display password mismatch error")
    public void password_mismatch_error() {
        response.then()
                .statusCode(200)
                .body(containsString("Passwords do not match!"));
    }

    @Then("the address should be updated successfully")
    public void address_updated_successfully() {
        response.then()
                .statusCode(200)
                .body(containsString("Address updated successfully!"));
    }

    @Then("the contact information should be updated successfully")
    public void contact_info_updated_successfully() {
        response.then()
                .statusCode(200)
                .body(containsString("Contact information updated successfully!"));
    }

    @Then("the user profile information should be displayed")
    public void user_profile_info_displayed() {
        response.then()
                .body(containsString("Nickname"))
                .body(containsString("Email"))
                .body(containsString("Contact"))
                .body(containsString("Address"));
    }

    @Then("the change password form should be available")
    public void change_password_form_available() {
        response.then()
                .body(containsString("Change Password"))
                .body(containsString("oldpassword"))
                .body(containsString("newpassword"));
    }
}