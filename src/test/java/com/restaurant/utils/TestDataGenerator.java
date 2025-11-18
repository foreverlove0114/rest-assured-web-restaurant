package com.restaurant.utils;

import com.restaurant.models.User;
import com.restaurant.models.MenuItem;
import com.restaurant.models.Reservation;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestDataGenerator {

    // 用户数据生成
    public static User generateUniqueUser() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return new User(
                "testuser_" + uniqueId,
                "testuser_" + uniqueId + "@email.com",
                "0123456789",
                "123 Test Street",
                "test123"
        );
    }

    public static User getDefaultTestUser() {
        return new User(
                ConfigManager.getTestUserNickname(),
                "testuser_1234@email.com",
                "0123456789",
                "123 Test Street",
                ConfigManager.getTestUserPassword()
        );
    }

    public static User getAdminUser() {
        return new User(
                ConfigManager.getAdminNickname(),
                "admin@restaurant.com",
                "0987654321",
                "Admin Address",
                ConfigManager.getAdminPassword()
        );
    }

    // 菜单数据生成
    public static MenuItem generateMenuItem() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 6);
        return new MenuItem(
                "Test Pizza " + uniqueId,
                "Cheese, Tomato, Dough",
                "A delicious test pizza for automated testing",
                25.0,
                "300g"
        );
    }

    public static MenuItem generatePizzaItem() {
        return new MenuItem(
                "Margherita Test",
                "Dough, tomato sauce, mozzarella, basil, olive oil",
                "Classic Italian pizza with delicate tomato sauce",
                190.0,
                "430g"
        );
    }

    // 预约数据生成
    public static Reservation generateReservation() {
        return new Reservation(
                "2", // 2人桌
                LocalDateTime.now().plusDays(1) // 明天
        );
    }

    public static Reservation generateReservation(String tableType, int daysFromNow) {
        return new Reservation(
                tableType,
                LocalDateTime.now().plusDays(daysFromNow)
        );
    }

    // 联系消息生成
    public static java.util.Map<String, String> generateContactMessage() {
        java.util.Map<String, String> message = new java.util.HashMap<>();
        message.put("name", "Test User");
        message.put("email", "testuser@email.com");
        message.put("subject", "Test Inquiry");
        message.put("message", "This is a test message from automated tests");
        return message;
    }

    // 随机数据生成工具
    public static String randomEmail() {
        return "test_" + UUID.randomUUID().toString().substring(0, 8) + "@email.com";
    }

    public static String randomNickname() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String randomPhone() {
        return "01" + (int)(Math.random() * 90000000 + 10000000);
    }
}