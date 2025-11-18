package com.restaurant.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SessionManager {
    // 1. 存储会话数据
    private Map<String, String> cookies = new HashMap<>();
    private String csrfToken;
    private boolean isLoggedIn = false;

    // 2. 构造函数
    public SessionManager() {
        // 初始化时可以做一些设置
        System.out.println("🆕 创建新的会话管理器");
    }

    // 3. 核心方法：用户登录
    public void login(String nickname, String password) {
        System.out.println("🔐 开始登录用户: " + nickname);

        // 步骤1: 获取登录页面，提取CSRF token
        System.out.println("📄 获取登录页面...");
        Response loginPage = given()
                .cookies(cookies)  // 带上现有cookies
                .get(ConfigManager.getBaseUrl() + "/login");

        // 提取CSRF token
        this.csrfToken = CSRFTokenExtractor.extractFromResponse(loginPage);
        System.out.println("🎫 提取到CSRF Token: " + (csrfToken != null ? "✓" : "✗"));

        // 步骤2: 执行登录
        System.out.println("🚀 提交登录表单...");
        Response loginResponse = given()
                .cookies(cookies)                    // 保持cookies
                .formParam("nickname", nickname)     // 表单参数：用户名
                .formParam("password", password)     // 表单参数：密码
                .formParam("csrf_token", csrfToken)  // 表单参数：CSRF token
                .post(ConfigManager.getBaseUrl() + "/login");

        // 步骤3: 保存会话状态
        // 更新cookies（服务器返回的会话cookies）
        Map<String, String> newCookies = loginResponse.getCookies();
        cookies.putAll(newCookies);

        // 更新CSRF token（从重定向后的页面提取）
        this.csrfToken = CSRFTokenExtractor.extractFromResponse(loginResponse);

        this.isLoggedIn = true;
        System.out.println("✅ 登录成功！会话已建立");
    }

    // 4. 获取认证后的请求对象
    public RequestSpecification getAuthenticatedRequest() {
        if (!isLoggedIn) {
            System.out.println("⚠️  用户未登录，返回基础请求");
            return given().cookies(cookies);
        }

        System.out.println("🎯 返回认证请求（包含CSRF token和cookies）");
        return given()
                .cookies(cookies)                    // 自动添加会话cookies
                .formParam("csrf_token", csrfToken); // 自动添加CSRF token
    }

    // 5. 获取未认证的请求对象（用于未登录测试）
    public RequestSpecification getUnauthenticatedRequest() {
        return given(); // 空的请求，没有cookies和CSRF token
    }

    // 6. 登出方法
    public void logout() {
        System.out.println("🚪 执行登出...");
        if (isLoggedIn) {
            given()
                    .cookies(cookies)
                    .get(ConfigManager.getBaseUrl() + "/logout");
        }

        // 清理会话数据
        cookies.clear();
        csrfToken = null;
        isLoggedIn = false;
        System.out.println("🧹 会话数据已清理");
    }

    // 7. 工具方法：检查登录状态
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    // 8. 获取当前CSRF token（用于调试）
    public String getCsrfToken() {
        return csrfToken;
    }

    // 9. 获取cookies数量（用于调试）
    public int getCookiesCount() {
        return cookies.size();
    }
}