package com.restaurant.hooks;

import com.restaurant.utils.SessionManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import com.restaurant.utils.ConfigManager;

public class Hooks {

    private SessionManager sessionManager;

    public Hooks() {
        this.sessionManager = new SessionManager();
    }

    @Before(order = 1)
    public void setupRestAssured() {
        RestAssured.baseURI = ConfigManager.getBaseUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Before(value = "@auth", order = 10)
    public void setupAuthentication(Scenario scenario) {
        System.out.println("🔐 Setting up authentication for scenario: " + scenario.getName());
    }

    @Before(value = "@admin", order = 20)
    public void loginAsAdmin() {
        System.out.println("👨‍💼 Logging in as admin");
        sessionManager.login(
                ConfigManager.getAdminNickname(),
                ConfigManager.getAdminPassword()
        );
    }

    @Before(value = "@user", order = 20)
    public void loginAsUser() {
        System.out.println("👤 Logging in as regular user");
        sessionManager.login(
                ConfigManager.getTestUserNickname(),
                ConfigManager.getTestUserPassword()
        );
    }

    @After(value = "@auth")
    public void cleanupAuthentication(Scenario scenario) {
        System.out.println("🧹 Cleaning up authentication for scenario: " + scenario.getName());
        if (sessionManager.isLoggedIn()) {
            sessionManager.logout();
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("❌ Scenario failed: " + scenario.getName());
        }
    }
}