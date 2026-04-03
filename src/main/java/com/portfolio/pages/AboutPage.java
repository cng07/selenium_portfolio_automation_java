package com.portfolio.pages;

import com.portfolio.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AboutPage extends BasePage {

    // Locators
    private final By mainContent = By.id("main-content");
    private final By pageHeading = By.xpath("//main//h1[normalize-space()='About Me']");
    private final By introHeading = By.xpath("//main//*[self::h1 or self::h2][contains(normalize-space(),'Carlos Ng')]");
    private final By highlightCards = By.xpath("//main//*[self::h2 or self::h3][normalize-space()='Fast Execution' or normalize-space()='Maintained Code' or normalize-space()='QA Philosophy']");

    public AboutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to about page
     */
    public AboutPage navigateToAbout() {
        navigateTo(BASE_URL + "/about");
        logger.info("Navigated to About Page");
        return this;
    }

    /**
     * Verify about page is loaded
     */
    public boolean isAboutPageLoaded() {
        return isElementDisplayed(mainContent);
    }

    /**
     * Verify about page heading is displayed
     */
    public boolean isAboutHeadingDisplayed() {
        return isElementDisplayed(pageHeading);
    }

    /**
     * Verify intro section is visible
     */
    public boolean isIntroSectionVisible() {
        return isElementDisplayed(introHeading);
    }

    /**
     * Verify highlight cards are visible
     */
    public boolean areHighlightsVisible() {
        return getHighlightCount() > 0;
    }

    /**
     * Get number of highlight cards
     */
    public int getHighlightCount() {
        waitForElement(pageHeading);
        return getElementCount(highlightCards);
    }

    /**
     * Get page heading text
     */
    public String getPageHeadingText() {
        return getText(pageHeading);
    }

    /**
     * Verify current URL is about page
     */
    public boolean isUrlCorrect() {
        String currentUrl = getCurrentUrl();
        return currentUrl.endsWith("/about");
    }

    /**
     * Get page title
     */
    public String getAboutPageTitle() {
        return getPageTitle();
    }
}
