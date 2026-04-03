package com.portfolio.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected static final int TIMEOUT = Integer.getInteger("timeout.seconds", 10);
    protected static final int SHORT_TIMEOUT = Integer.getInteger("short.timeout.seconds", 3);
    protected static final String BASE_URL = System.getProperty("base.url", "https://carlosng07.vercel.app");
    private static final By MAIN_CONTENT = By.id("main-content");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    /**
     * Wait for element to be visible and return it
     */
    protected WebElement waitForElement(By locator) {
        logger.info("Waiting for element: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     */
    protected WebElement waitForElementClickable(By locator) {
        logger.info("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Click on element
     */
    protected void click(By locator) {
        waitForElementClickable(locator).click();
        logger.info("Clicked on element: {}", locator);
    }

    /**
     * Get text from element
     */
    protected String getText(By locator) {
        return waitForElement(locator).getText();
    }

    /**
     * Check if element is displayed
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(SHORT_TIMEOUT));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            logger.warn("Element not displayed: {}", locator);
            return false;
        }
    }

    /**
     * Check if element is visible immediately without waiting
     */
    protected boolean isElementVisibleNow(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    /**
     * Get current page URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Navigate to URL
     */
    protected void navigateTo(String url) {
        driver.navigate().to(url);
        waitForDocumentReady();
        waitForElementPresence(MAIN_CONTENT);
        logger.info("Navigated to: {}", url);
    }

    /**
     * Wait for URL to contain text
     */
    protected void waitForUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
        logger.info("URL contains: {}", urlPart);
    }

    /**
     * Get page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Wait for element to be present in DOM
     */
    protected void waitForElementPresence(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        logger.info("Element present: {}", locator);
    }

    /**
     * Return number of elements matching a locator
     */
    protected int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }

    /**
     * Return an attribute from the first visible element
     */
    protected String getAttribute(By locator, String attributeName) {
        return waitForElement(locator).getAttribute(attributeName);
    }

    /**
     * Resolve a relative site path into an absolute URL
     */
    protected String toAbsoluteUrl(String urlOrPath) {
        if (urlOrPath == null || urlOrPath.isBlank()) {
            return urlOrPath;
        }
        if (urlOrPath.startsWith("http://") || urlOrPath.startsWith("https://")) {
            return urlOrPath;
        }
        if (urlOrPath.startsWith("/")) {
            return BASE_URL + urlOrPath;
        }
        return BASE_URL + "/" + urlOrPath;
    }

    /**
     * Wait until the browser reports that the document is fully loaded
     */
    protected void waitForDocumentReady() {
        wait.until(driver -> Objects.equals(
                "complete",
                ((JavascriptExecutor) driver).executeScript("return document.readyState")
        ));
    }
}
