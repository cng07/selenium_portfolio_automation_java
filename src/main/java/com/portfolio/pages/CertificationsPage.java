package com.portfolio.pages;

import com.portfolio.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CertificationsPage extends BasePage {

    // Locators
    private final By mainContent = By.id("main-content");
    private final By pageHeading = By.xpath("//main//h1[normalize-space()='Certifications']");
    private final By certificationCards = By.xpath("//main//a[normalize-space()='View Certificate']");
    private final By certTitle = By.xpath("//main//*[self::h2 or self::h3 or self::h4][contains(., 'Certified') or contains(., 'Agile') or contains(., 'Automation Anywhere')]");

    public CertificationsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to certifications page
     */
    public CertificationsPage navigateToCertifications() {
        navigateTo(BASE_URL + "/certifications");
        logger.info("Navigated to Certifications Page");
        return this;
    }

    /**
     * Verify certifications page is loaded
     */
    public boolean isCertificationsPageLoaded() {
        return isElementDisplayed(mainContent);
    }

    /**
     * Verify certifications page heading is displayed
     */
    public boolean isCertificationsHeadingDisplayed() {
        return isElementDisplayed(pageHeading);
    }

    /**
     * Get number of certification entries
     */
    public int getCertificationCount() {
        waitForElement(pageHeading);
        int certificationCount = getElementCount(certificationCards);
        logger.info("Found {} certification entries", certificationCount);
        return certificationCount;
    }

    /**
     * Verify certification entries are displayed
     */
    public boolean areCertificationsDisplayed() {
        return getCertificationCount() > 0;
    }

    /**
     * Get page heading text
     */
    public String getPageHeadingText() {
        return getText(pageHeading);
    }

    /**
     * Verify current URL is certifications page
     */
    public boolean isUrlCorrect() {
        String currentUrl = getCurrentUrl();
        return currentUrl.endsWith("/certifications");
    }

    /**
     * Get page title
     */
    public String getCertificationsPageTitle() {
        return getPageTitle();
    }

    /**
     * Verify certification titles are displayed
     */
    public boolean areCertTitlesDisplayed() {
        waitForElement(pageHeading);
        return getElementCount(certTitle) > 0;
    }
}
