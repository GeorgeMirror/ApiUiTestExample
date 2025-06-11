package org.example.config;

import com.codeborne.selenide.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

    public static void configure() {
        Properties properties = new Properties();

        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.err.println("❌ Не найден файл application.properties");
                return;
            }

            properties.load(input);

            String baseUrl = properties.getProperty("baseUrl");
            if (baseUrl != null) {
                Configuration.baseUrl = baseUrl;
                System.out.println("✅ baseUrl: " + baseUrl);
            }

            String pageLoadTimeout = properties.getProperty("selenide.pageLoadTimeout");
            if (pageLoadTimeout != null) {
                Configuration.pageLoadTimeout = Long.parseLong(pageLoadTimeout);
                System.out.println("✅ pageLoadTimeout: " + pageLoadTimeout);
            }

            String timeout = properties.getProperty("selenide.timeout");
            if (timeout != null) {
                Configuration.timeout = Long.parseLong(timeout);
                System.out.println("✅ timeout: " + timeout);
            }

            String browser = properties.getProperty("selenide.browser");
            if (browser != null) {
                Configuration.browser = browser;
                System.out.println("✅ browser: " + browser);
            }

            String headless = properties.getProperty("selenide.headless");
            if (headless != null) {
                Configuration.headless = Boolean.parseBoolean(headless);
                System.out.println("✅ headless: " + headless);
            }

            String browserSize = properties.getProperty("selenide.browserSize");
            if (browserSize != null) {
                Configuration.browserSize = browserSize;
                System.out.println("✅ browserSize: " + browserSize);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}