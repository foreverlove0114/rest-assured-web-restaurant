package com.restaurant.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.restaurant.stepdefinitions", "com.restaurant.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports/smoke-test.html"},
        tags = "@smoke"
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {
}