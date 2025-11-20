package com.restaurant.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SessionManager {
    private Map<String, String> cookies = new HashMap<>();
    private String csrfToken;
    private boolean isLoggedIn = false;

    public SessionManager() {
        System.out.println("🆕 创建新的会话管理器");
    }

    public Response loginAndGetResponse(String nickname, String password) {
        System.out.println("🔐 登录: " + nickname);

        try {
            // 1. 获取登录页面
            Response loginPage = given().cookies(cookies).get(ConfigManager.getBaseUrl() + "/login");
            this.csrfToken = CSRFTokenExtractor.extractFromResponse(loginPage);
            cookies.putAll(loginPage.getCookies());

            System.out.println("🎫 登录前CSRF: " + (csrfToken != null ? "✓" : "✗"));

            // 2. 执行登录
            Response loginResponse = given()
                    .cookies(cookies)
                    .formParam("nickname", nickname)
                    .formParam("password", password)
                    .formParam("csrf_token", csrfToken)
                    .post(ConfigManager.getBaseUrl() + "/login");

            // 3. 更新cookies
            cookies.putAll(loginResponse.getCookies());

            // 4. 判断登录状态
            String responseBody = loginResponse.getBody().asString();
            if (loginResponse.getStatusCode() == 302 ||
                    (loginResponse.getStatusCode() == 200 && responseBody.contains("Personal information"))) {
                this.isLoggedIn = true;
                System.out.println("✅ 登录成功");
            } else {
                this.isLoggedIn = false;
                System.out.println("❌ 登录失败");
            }

            return loginResponse;

        } catch (Exception e) {
            System.out.println("❌ 登录异常: " + e.getMessage());
            this.isLoggedIn = false;
            return null;
        }
    }

    // 原有的login方法保持不变
    public void login(String nickname, String password) {
        loginAndGetResponse(nickname, password);
    }

    public RequestSpecification getAuthenticatedRequest() {
        RequestSpecification request = given().cookies(cookies);

        if (csrfToken != null) {
            request = request.formParam("csrf_token", csrfToken);
        }

        System.out.println("🎯 返回认证请求 - 登录状态: " + isLoggedIn +
                ", Cookies数量: " + cookies.size() +
                ", CSRF Token: " + (csrfToken != null ? "✓" : "✗"));
        return request;
    }

    public RequestSpecification getUnauthenticatedRequest() {
        System.out.println("🎯 返回未认证请求");
        return given();
    }

    public void logout() {
        System.out.println("🚪 执行登出...");
        if (isLoggedIn) {
            given().cookies(cookies).get(ConfigManager.getBaseUrl() + "/logout");
        }
        cookies.clear();
        csrfToken = null;
        isLoggedIn = false;
        System.out.println("🧹 会话数据已清理");
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public int getCookiesCount() {
        return cookies.size();
    }

    // 调试方法：打印当前会话状态
    public void printSessionStatus() {
        System.out.println("=== 当前会话状态 ===");
        System.out.println("登录状态: " + isLoggedIn);
        System.out.println("Cookies数量: " + cookies.size());
        System.out.println("Cookies: " + cookies.keySet());
        System.out.println("CSRF Token: " + (csrfToken != null ? "存在" : "不存在"));
        System.out.println("==================");
    }
}