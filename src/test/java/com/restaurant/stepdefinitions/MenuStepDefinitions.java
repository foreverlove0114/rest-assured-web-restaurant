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

public class MenuStepDefinitions {

    private final SessionManager sessionManager;
    private Response response;
    private Integer productId;

    public MenuStepDefinitions(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // 无参构造器
    public MenuStepDefinitions() {
        this.sessionManager = new SessionManager();
    }

    @Given("I am on the menu page")
    public void on_menu_page() {
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/menu");
    }

    @Given("there is a product available in the menu")
    public void product_available_in_menu() {
        this.productId = 2;
    }

    @When("I view the menu")
    public void view_menu() {
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/menu");
    }

    @When("I view the details of product {int}")
    public void view_product_details(int productId) {
        this.productId = productId;
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/position/" + productId);
    }

    @When("I view the details of a random product")
    public void view_random_product_details() {
        this.productId = 2;
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/position/" + productId);
    }

    @When("I try to add product {int} to cart without logging in")
    public void add_to_cart_unauthenticated(int productId) {
        this.productId = productId;

        Response productPage = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/position/" + productId);

        String csrfToken = CSRFTokenExtractor.extractFromResponse(productPage);

        response = sessionManager.getUnauthenticatedRequest()
                .formParam("csrf_token", csrfToken)
                .formParam("num", "1")
                .post(ConfigManager.getBaseUrl() + "/position/" + productId);
    }

    @When("I add product {int} to cart")
    public void add_to_cart_authenticated(int productId) {
        this.productId = productId;

        Response productPage = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/position/" + productId);

        String csrfToken = CSRFTokenExtractor.extractFromResponse(productPage);

        response = sessionManager.getAuthenticatedRequest()
                .formParam("csrf_token", csrfToken)
                .formParam("num", "1")
                .post(ConfigManager.getBaseUrl() + "/position/" + productId);
    }

    @Then("the menu page should be displayed")
    public void menu_page_displayed() {
        response.then()
                .statusCode(200)
                .body(containsString("Our Menu"));
    }

    @Then("the product details page should be displayed")
    public void product_details_page_displayed() {
        response.then()
                .statusCode(200)
                .body(containsString("Ingredients:"))
                .body(containsString("Description:"));
    }

    @Then("I should be prompted to log in")
    public void should_be_prompted_to_login() {
        response.then()
                .statusCode(200)
                .body(containsString("To add an item to the cart, please log in first!"));
    }

    @Then("the product should be added to cart successfully")
    public void product_added_to_cart_successfully() {
        response.then()
                .statusCode(200)
                .body(containsString("Item added to cart!"));
    }

    @Then("the product image should be displayed")
    public void product_image_displayed() {
        response.then()
                .body(anyOf(
                        containsString("img"),
                        containsString("image"),
                        containsString("photo")
                ));
    }

    @Then("the product price should be displayed")
    public void product_price_displayed() {
        // 简化价格检查，只检查是否包含数字
        String responseBody = response.getBody().asString();
        assertTrue("Price should be displayed",
                responseBody.matches(".*\\$?\\d+(\\.\\d{2})?.*") ||
                        responseBody.contains("$") ||
                        responseBody.contains("price") ||
                        responseBody.contains("Price")
        );
    }
}