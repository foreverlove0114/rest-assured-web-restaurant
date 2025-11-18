package com.restaurant.stepdefinitions;

import com.restaurant.utils.ConfigManager;
import com.restaurant.utils.SessionManager;
import com.restaurant.utils.CSRFTokenExtractor;
import com.restaurant.utils.TestDataGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ReservationStepDefinitions {

    private final SessionManager sessionManager;
    private Response response;

    public ReservationStepDefinitions(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // 无参构造器
    public ReservationStepDefinitions() {
        this.sessionManager = new SessionManager();
    }

    @Given("the user is on the reservation page")
    public void user_on_reservation_page() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/reserved");
    }

    @When("an unauthenticated user accesses the reservation page")
    public void unauthenticated_user_accesses_reservation_page() {
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/reserved");
    }

    @When("the user accesses the reservation page")
    public void user_accesses_reservation_page() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/reserved");
    }

    @When("the user makes a reservation for a {string} table")
    public void user_makes_reservation(String tableType) {
        String csrfToken = sessionManager.getCsrfToken();
        String futureTime = TestDataGenerator.generateReservation(tableType, 1)
                .getReservationTime().toString().replace("T", " ") + ":00";

        response = sessionManager.getAuthenticatedRequest()
                .formParam("table_type", tableType)
                .formParam("time", futureTime)
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/reserved");
    }

    @When("the user makes a reservation with current time")
    public void user_makes_reservation_current_time() {
        String csrfToken = sessionManager.getCsrfToken();
        String currentTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        response = sessionManager.getAuthenticatedRequest()
                .formParam("table_type", "2")
                .formParam("time", currentTime)
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/reserved");
    }

    @When("the user tries to make multiple reservations")
    public void user_makes_multiple_reservations() {
        // 第一次预约
        String csrfToken = sessionManager.getCsrfToken();
        String time1 = TestDataGenerator.generateReservation("2", 1)
                .getReservationTime().toString().replace("T", " ") + ":00";

        Response response1 = sessionManager.getAuthenticatedRequest()
                .formParam("table_type", "2")
                .formParam("time", time1)
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/reserved");

        // 第二次预约（应该失败）
        String time2 = TestDataGenerator.generateReservation("4", 2)
                .getReservationTime().toString().replace("T", " ") + ":00";

        response = sessionManager.getAuthenticatedRequest()
                .formParam("table_type", "4")
                .formParam("time", time2)
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/reserved");
    }

    @Then("the reservation page should require login")
    public void reservation_page_requires_login() {
        response.then()
                .statusCode(200)
                .body(containsString("Please log in to access this page."));
    }

    @Then("the reservation page should be displayed")
    public void reservation_page_displayed() {
        response.then()
                .statusCode(200)
                .body(containsString("Table Reservation"))
                .body(containsString("Table Type:"))
                .body(containsString("Reservation Time:"))
                .body(containsString("Make Reservation"));
    }

    @Then("the reservation should be created successfully")
    public void reservation_created_successfully() {
        response.then()
                .statusCode(200)
                .body(containsString("successfully created!"));
    }

    @Then("the system should show only one active reservation message")
    public void only_one_active_reservation_message() {
        response.then()
                .statusCode(200)
                .body(containsString("You can have only one active reservation."));
    }

    @Then("the system should show table not available message")
    public void table_not_available_message() {
        response.then()
                .statusCode(200)
                .body(anyOf(
                        containsString("not available"),
                        containsString("already booked")
                ));
    }

    @Then("the reservation form should contain table type options")
    public void reservation_form_contains_table_options() {
        response.then()
                .body(containsString("table_type"))
                .body(containsString("option"));
    }

    @Then("the reservation form should contain datetime picker")
    public void reservation_form_contains_datetime_picker() {
        response.then()
                .body(anyOf(
                        containsString("type=\"datetime-local\""),
                        containsString("name=\"time\"")
                ));
    }
}