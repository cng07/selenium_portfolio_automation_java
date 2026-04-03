package com.portfolio.pages;

import com.portfolio.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ExperiencePage extends BasePage {

    // Locators
    private final By mainContent = By.id("main-content");
    private final By pageHeading = By.xpath("//main//h1[contains(normalize-space(),'Experience')]");
    private final By experienceCards = By.xpath("//main//h3[a]");
    private final By jobTitle = By.xpath("//main//h3[a]/following-sibling::p[1] | //main//h4");

    public ExperiencePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to experience page
     */
    public ExperiencePage navigateToExperience() {
        navigateTo(BASE_URL + "/experience");
        logger.info("Navigated to Experience Page");
        return this;
    }

    /**
     * Verify experience page is loaded
     */
    public boolean isExperiencePageLoaded() {
        return isElementDisplayed(mainContent);
    }

    /**
     * Verify experience page heading is displayed
     */
    public boolean isExperienceHeadingDisplayed() {
        return isElementDisplayed(pageHeading);
    }

    /**
     * Get number of experience entries
     */
    public int getExperienceCount() {
        waitForElement(pageHeading);
        int experienceCount = getElementCount(experienceCards);
        logger.info("Found {} experience entries", experienceCount);
        return experienceCount;
    }

    /**
     * Verify experience entries are displayed
     */
    public boolean areExperiencesDisplayed() {
        return getExperienceCount() > 0;
    }

    /**
     * Get page heading text
     */
    public String getPageHeadingText() {
        return getText(pageHeading);
    }

    /**
     * Verify current URL is experience page
     */
    public boolean isUrlCorrect() {
        String currentUrl = getCurrentUrl();
        return currentUrl.endsWith("/experience");
    }

    /**
     * Get page title
     */
    public String getExperiencePageTitle() {
        return getPageTitle();
    }

    /**
     * Verify job titles are displayed
     */
    public boolean areJobTitlesDisplayed() {
        waitForElement(pageHeading);
        return getElementCount(jobTitle) > 0;
    }
}
