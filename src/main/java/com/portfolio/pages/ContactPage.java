package com.portfolio.pages;

import com.portfolio.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactPage extends BasePage {

    // Locators
    private final By mainContent = By.id("main-content");
    private final By pageHeading = By.xpath("//main//h1[normalize-space()='Get in Touch']");
    private final By emailLink = By.xpath("//main//a[starts-with(@href, 'mailto:')]");
    private final By contactMethods = By.xpath("//main//a[starts-with(@href,'mailto:') or contains(@href,'linkedin.com') or contains(@href,'github.com') or contains(@href,'ieeexplore.ieee.org') or contains(@href,'atsqa.org')]");

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to contact page
     */
    public ContactPage navigateToContact() {
        navigateTo(BASE_URL + "/contact");
        logger.info("Navigated to Contact Page");
        return this;
    }

    /**
     * Verify contact page is loaded
     */
    public boolean isContactPageLoaded() {
        return isElementDisplayed(mainContent);
    }

    /**
     * Verify contact page heading is displayed
     */
    public boolean isContactHeadingDisplayed() {
        return isElementDisplayed(pageHeading);
    }

    /**
     * Verify contact methods are visible
     */
    public boolean areContactMethodsVisible() {
        return getContactMethodCount() > 0;
    }

    /**
     * Verify email link is visible
     */
    public boolean isEmailLinkVisible() {
        return isElementDisplayed(emailLink);
    }

    /**
     * Get number of contact method cards
     */
    public int getContactMethodCount() {
        waitForElement(pageHeading);
        return getElementCount(contactMethods);
    }

    /**
     * Get email link href
     */
    public String getEmailLinkHref() {
        return getAttribute(emailLink, "href");
    }

    /**
     * Get page heading text
     */
    public String getPageHeadingText() {
        return getText(pageHeading);
    }

    /**
     * Verify current URL is contact page
     */
    public boolean isUrlCorrect() {
        String currentUrl = getCurrentUrl();
        return currentUrl.endsWith("/contact");
    }

    /**
     * Get page title
     */
    public String getContactPageTitle() {
        return getPageTitle();
    }
}
