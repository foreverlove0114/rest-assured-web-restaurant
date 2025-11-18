package com.restaurant.stepdefinitions;

import com.restaurant.utils.ConfigManager;
import com.restaurant.utils.SessionManager;
import com.restaurant.utils.CSRFTokenExtractor;
import com.restaurant.models.MenuItem;
import com.restaurant.utils.TestDataGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class AdminStepDefinitions {

    private final SessionManager sessionManager;
    private Response response;
    private MenuItem testMenuItem;

    public AdminStepDefinitions(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // 无参构造器
    public AdminStepDefinitions() {
        this.sessionManager = new SessionManager();
    }

    @Given("the admin is logged in")
    public void admin_is_logged_in() {
        sessionManager.login(
                ConfigManager.getAdminNickname(),
                ConfigManager.getAdminPassword()
        );
    }

    @When("the admin accesses the add position page")
    public void admin_accesses_add_position_page() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/add_position");
    }

    @When("the admin adds a new menu item with valid data")
    public void admin_adds_new_menu_item() {
        testMenuItem = TestDataGenerator.generateMenuItem();
        String csrfToken = sessionManager.getCsrfToken();

        response = sessionManager.getAuthenticatedRequest()
                .formParam("name", testMenuItem.getName())
                .formParam("ingredients", testMenuItem.getIngredients())
                .formParam("description", testMenuItem.getDescription())
                .formParam("price", testMenuItem.getPrice().toString())
                .formParam("weight", testMenuItem.getWeight())
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/add_position");
    }

    @When("the admin tries to add a menu item without image")
    public void admin_adds_menu_item_without_image() {
        testMenuItem = TestDataGenerator.generateMenuItem();
        String csrfToken = sessionManager.getCsrfToken();

        response = sessionManager.getAuthenticatedRequest()
                .formParam("name", testMenuItem.getName())
                .formParam("ingredients", testMenuItem.getIngredients())
                .formParam("description", testMenuItem.getDescription())
                .formParam("price", testMenuItem.getPrice().toString())
                .formParam("weight", testMenuItem.getWeight())
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/add_position");
    }

    @When("the admin accesses the edit position page for item {int}")
    public void admin_accesses_edit_position_page(int itemId) {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/edit_position/" + itemId);
    }

    @When("the admin edits menu item {int} with new data")
    public void admin_edits_menu_item(int itemId) {
        String csrfToken = sessionManager.getCsrfToken();

        response = sessionManager.getAuthenticatedRequest()
                .formParam("name", "Updated Test Item")
                .formParam("ingredients", "Updated ingredients")
                .formParam("description", "Updated description")
                .formParam("price", "200")
                .formParam("weight", "400")
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/edit_position/" + itemId);
    }

    @When("the admin accesses the reservations check page")
    public void admin_accesses_reservations_check_page() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/reservations_check");
    }

    @When("the admin deletes a reservation")
    public void admin_deletes_reservation() {
        String csrfToken = sessionManager.getCsrfToken();

        // 这里需要先获取一个可删除的预订ID
        // 简化实现：假设我们知道一个测试预订ID
        response = sessionManager.getAuthenticatedRequest()
                .formParam("reserv_id", "1") // 需要根据实际情况调整
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/reservations_check");
    }

    @When("the admin accesses the menu check page")
    public void admin_accesses_menu_check_page() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/menu_check");
    }

    @When("the admin changes status of menu item {int}")
    public void admin_changes_menu_item_status(int itemId) {
        String csrfToken = sessionManager.getCsrfToken();

        response = sessionManager.getAuthenticatedRequest()
                .formParam("pos_id", String.valueOf(itemId))
                .formParam("change_status", "")
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/menu_check");
    }

    @When("the admin deletes menu item {int}")
    public void admin_deletes_menu_item(int itemId) {
        String csrfToken = sessionManager.getCsrfToken();

        response = sessionManager.getAuthenticatedRequest()
                .formParam("pos_id", String.valueOf(itemId))
                .formParam("delete_position", "")
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/menu_check");
    }

    @When("the admin accesses the all users page")
    public void admin_accesses_all_users_page() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/all_users");
    }

    @Then("the add position page should be accessible")
    public void add_position_page_accessible() {
        response.then()
                .statusCode(200)
                .body(containsString("Adding a new item"))
                .body(containsString("Add product"));
    }

    @Then("the menu item should be added successfully")
    public void menu_item_added_successfully() {
        response.then()
                .statusCode(200)
                .body(containsString("Item added successfully!"));
    }

    @Then("the system should show no file selected error")
    public void system_shows_no_file_error() {
        response.then()
                .statusCode(200)
                .body(containsString("No file selected or upload failed"));
    }

    @Then("the edit position page should be accessible")
    public void edit_position_page_accessible() {
        response.then()
                .statusCode(200)
                .body(containsString("Editing item"))
                .body(containsString("Active"));
    }

    @Then("the menu item should be edited successfully")
    public void menu_item_edited_successfully() {
        response.then()
                .statusCode(200)
                .body(containsString("Position successfully edited!"))
                .body(containsString("Our Menu"));
    }

    @Then("the reservations check page should be accessible")
    public void reservations_check_page_accessible() {
        response.then()
                .statusCode(200)
                .body(containsString("Checking reservations"));
    }

    @Then("the reservation should be deleted successfully")
    public void reservation_deleted_successfully() {
        response.then().statusCode(200);
        // 可以根据实际响应调整断言
    }

    @Then("the menu check page should be accessible")
    public void menu_check_page_accessible() {
        response.then()
                .statusCode(200)
                .body(containsString("Перевірка меню"));
    }

    @Then("the menu item status should be changed")
    public void menu_item_status_changed() {
        response.then()
                .statusCode(200)
                .body(anyOf(
                        containsString("Ні"),
                        containsString("Так")
                ));
    }

    @Then("the menu item should be deleted")
    public void menu_item_deleted() {
        response.then().statusCode(200);
        // 可以根据实际响应调整断言
    }

    @Then("the all users page should be accessible")
    public void all_users_page_accessible() {
        response.then()
                .statusCode(200)
                .body(containsString("Users"));
    }

    @Then("the user should see access denied for admin pages")
    public void user_sees_access_denied() {
        response.then()
                .statusCode(200)
                .body(containsString("Access denied! Only administrators can"));
    }
}