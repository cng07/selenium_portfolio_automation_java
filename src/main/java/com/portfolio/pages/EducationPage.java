package com.portfolio.pages;

import com.portfolio.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EducationPage extends BasePage {

    // Locators
    private final By mainContent = By.id("main-content");
    private final By pageHeading = By.xpath("//main//h1[normalize-space()='Education']");
    private final By educationCards = By.xpath("//main//*[self::h2 or self::h3][normalize-space()='Asia Pacific College' or normalize-space()='Makati Science High School']");
    private final By schoolName = educationCards;

    public EducationPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to education page
     */
    public EducationPage navigateToEducation() {
        navigateTo(BASE_URL + "/education");
        logger.info("Navigated to Education Page");
        return this;
    }

    /**
     * Verify education page is loaded
     */
    public boolean isEducationPageLoaded() {
        return isElementDisplayed(mainContent);
    }

    /**
     * Verify education page heading is displayed
     */
    public boolean isEducationHeadingDisplayed() {
        return isElementDisplayed(pageHeading);
    }

    /**
     * Get number of education entries
     */
    public int getEducationCount() {
        waitForElement(pageHeading);
        int educationCount = getElementCount(educationCards);
        logger.info("Found {} education entries", educationCount);
        return educationCount;
    }

    /**
     * Verify education entries are displayed
     */
    public boolean areEducationsDisplayed() {
        return getEducationCount() > 0;
    }

    /**
     * Get page heading text
     */
    public String getPageHeadingText() {
        return getText(pageHeading);
    }

    /**
     * Verify current URL is education page
     */
    public boolean isUrlCorrect() {
        String currentUrl = getCurrentUrl();
        return currentUrl.endsWith("/education");
    }

    /**
     * Get page title
     */
    public String getEducationPageTitle() {
        return getPageTitle();
    }

    /**
     * Verify school names are displayed
     */
    public boolean areSchoolNamesDisplayed() {
        waitForElement(pageHeading);
        return getElementCount(schoolName) > 0;
    }
}
