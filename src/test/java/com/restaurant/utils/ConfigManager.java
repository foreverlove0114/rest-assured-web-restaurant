package com.restaurant.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties(); // 单例的配置存储

    static {
        // 类加载时自动初始化，只执行一次
        try(InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input); // 从文件加载配置到内存
        }catch (IOException e){
            throw new RuntimeException("Error loading configuration", e);
        }
    }

    public static String getBaseUrl(){
        return properties.getProperty("base.url", "http://localhost:8000");
    }

    public static String getTestUserNickname(){
        return properties.getProperty("test.user.nickname", "testuser_1234");
    }

    public static String getTestUserPassword() {
        return properties.getProperty("test.user.password", "test123");
    }

    public static String getAdminNickname() {
        return properties.getProperty("admin.nickname", "superadmin");
    }

    public static String getAdminPassword() {
        return properties.getProperty("admin.password", "admin123");
    }
}


