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
import static org.testng.Assert.fail;

public class AuthStepDefinitions {

    private final SessionManager sessionManager;
    private Response response;
    private User testUser;

    public AuthStepDefinitions() {
        this.sessionManager = new SessionManager();
    }

    @Given("I am on the registration page")
    public void on_registration_page() {
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/register");
    }

    @Given("I am on the login page")
    public void on_login_page() {
        System.out.println("=== 步骤: 访问登录页面 ===");
        response = sessionManager.getUnauthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/login");

        System.out.println("响应状态: " + response.getStatusCode());
        System.out.println("页面标题: " + (response.getBody().asString().contains("Account Login") ? "登录页面" : "其他页面"));
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
        System.out.println("=== 步骤: 执行登录 ===");
        this.response = sessionManager.loginAndGetResponse(username, password);
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
                .body(containsString("Delicious dishes"));
    }

    @Then("I should see the profile page")
    public void should_see_profile_page() {
        System.out.println("=== 步骤: 验证个人资料页面 ===");

        // 先打印当前会话状态
        sessionManager.printSessionStatus();

        // 访问个人资料页面
        response = sessionManager.getAuthenticatedRequest()
                .get(ConfigManager.getBaseUrl() + "/profile");

        System.out.println("Profile页面响应状态: " + response.getStatusCode());

        // 打印响应内容的前200个字符用于调试
        String responseBody = response.getBody().asString();
        System.out.println("响应内容预览: " +
                (responseBody.length() > 200 ? responseBody.substring(0, 200) + "..." : responseBody));

        // 验证是否是个人资料页面
        if (responseBody.contains("Personal information")) {
            System.out.println("✅ 成功访问个人资料页面");
            response.then()
                    .statusCode(200)
                    .body(containsString("Personal information"));
        } else if (responseBody.contains("Account Login")) {
            System.out.println("❌ 被重定向到登录页面，登录可能失败");
            fail("Expected to see profile page but got login page instead. Login might have failed.");
        } else {
            System.out.println("❓ 访问到未知页面");
            fail("Unexpected page content: " + responseBody.substring(0, 100));
        }
    }

    @Then("I should see login and register options")
    public void should_see_login_register_options() {
        response.then()
                .body(anyOf(
                        containsString("Login"),
                        containsString("Register")
                ));
    }
}