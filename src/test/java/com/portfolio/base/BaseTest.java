package com.portfolio.base;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static final String BASE_URL = System.getProperty("base.url", "https://carlosng07.vercel.app");
    protected static final String BROWSER = System.getProperty("browser", "chrome");

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("=== Test Setup Started: browser={}, baseUrl={} ===", BROWSER, BASE_URL);
        DriverManager.initDriver(BROWSER);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("=== Test Teardown Started ===");
        DriverManager.quitDriver();
    }
}
