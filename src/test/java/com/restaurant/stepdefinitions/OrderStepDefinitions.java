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

public class OrderStepDefinitions {

    private final SessionManager sessionManager;
    private Response response;
    private String orderId;

    public OrderStepDefinitions(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // 无参构造器
    public OrderStepDefinitions() {
        this.sessionManager = new SessionManager();
    }

    @Given("the user has items in the cart")
    public void user_has_items_in_cart() {
        // 添加一个商品到购物车
        int productId = 2; // 假设产品ID 2存在

        Response productPage = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/position/" + productId);

        String csrfToken = CSRFTokenExtractor.extractFromResponse(productPage);

        sessionManager.getAuthenticatedRequest()
                .formParam("csrf_token", csrfToken)
                .formParam("num", "1")
                .post(ConfigManager.getBaseUrl() + "/position/" + productId);
    }

    @Given("the user has a placed order")
    public void user_has_placed_order() {
        // 确保用户有商品在购物车
        user_has_items_in_cart();

        // 创建订单
        Response orderPage = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/create_order");

        String csrfToken = CSRFTokenExtractor.extractFromResponse(orderPage);

        Response orderResponse = sessionManager.getAuthenticatedRequest()
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/create_order");

        // 从响应中提取订单ID（简化实现）
        this.orderId = "1"; // 需要根据实际响应调整
    }

    @When("the user accesses the create order page")
    public void user_accesses_create_order_page() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/create_order");
    }

    @When("an unauthenticated user accesses the create order page")
    public void unauthenticated_user_accesses_create_order_page() {
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/create_order");
    }

    @When("the user creates an order")
    public void user_creates_order() {
        Response orderPage = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/create_order");

        String csrfToken = CSRFTokenExtractor.extractFromResponse(orderPage);

        response = sessionManager.getAuthenticatedRequest()
                .formParam("csrf_token", csrfToken)
                .post(ConfigManager.getBaseUrl() + "/create_order");
    }

    @When("the user views order list")
    public void user_views_order_list() {
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/my_orders");
    }

    @When("the user views order details for order {string}")
    public void user_views_order_details(String orderId) {
        this.orderId = orderId;
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/my_order/" + orderId);
    }

    @When("the user cancels the order")
    public void user_cancels_order() {
        response = sessionManager.getAuthenticatedRequest()
                .post(ConfigManager.getBaseUrl() + "/cancel_order/" + orderId);
    }

    @When("the user tries to cancel non-existent order {string}")
    public void user_cancels_nonexistent_order(String invalidOrderId) {
        response = sessionManager.getAuthenticatedRequest()
                .post(ConfigManager.getBaseUrl() + "/cancel_order/" + invalidOrderId);
    }

    @Then("the empty cart message should be displayed")
    public void empty_cart_message_displayed() {
        response.then()
                .statusCode(200)
                .body(containsString("Your Cart is Empty"));
    }

    @Then("no order form should be displayed")
    public void no_order_form_displayed() {
        response.then()
                .body(not(containsString("checkout-form")));
    }

    @Then("the order form should be displayed")
    public void order_form_displayed() {
        response.then()
                .statusCode(200)
                .body(containsString("Selected Items"))
                .body(containsString("Quantity:"))
                .body(containsString("Total Price"));
    }

    @Then("the order should be created successfully")
    public void order_created_successfully() {
        response.then()
                .statusCode(200)
                .body(containsString("Items List:"))
                .body(containsString("Date & Time"))
                .body(containsString("Cancel Order"));
    }

    @Then("the order list should be displayed")
    public void order_list_displayed() {
        response.then()
                .statusCode(200)
                .body(containsString("Your Orders"))
                .body(containsString("View Details"));
    }

    @Then("the order details should be displayed")
    public void order_details_displayed() {
        response.then()
                .statusCode(200)
                .body(containsString("Items List:"))
                .body(containsString("Date & Time"))
                .body(containsString("Cancel Order"));
    }

    @Then("the order should be cancelled successfully")
    public void order_cancelled_successfully() {
        response.then()
                .statusCode(200)
                .body(containsString("Order deleted!"));
    }

    @Then("the system should show order not found error")
    public void order_not_found_error() {
        response.then()
                .statusCode(200)
                .body(containsString("Order not found or it is not yours!"));
    }

    @Then("the cart items should be displayed")
    public void cart_items_displayed() {
        response.then()
                .body(containsString("Selected Items"))
                .body(containsString("Quantity:"));
    }
}