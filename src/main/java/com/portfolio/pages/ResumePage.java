package com.portfolio.pages;

import com.portfolio.base.BasePage;
import com.portfolio.utilities.ApiHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;

public class ResumePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ResumePage.class);

    // Locators
    private final By mainContent = By.id("main-content");
    private final By pageHeading = By.xpath("//main//h1[normalize-space()='Resume']");
    private final By resumeViewer = By.xpath("//main//iframe[@title='Carlos Ng Resume']");
    private final By downloadButton = By.xpath("//main//button[normalize-space()='Download PDF']");
    private final By pdfObject = By.xpath("//main//object[contains(@data, '.pdf')]");

    public ResumePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to resume page
     */
    public ResumePage navigateToResume() {
        navigateTo(BASE_URL + "/resume");
        logger.info("Navigated to Resume Page");
        return this;
    }

    /**
     * Verify resume page is loaded
     */
    public boolean isResumePageLoaded() {
        return isElementDisplayed(mainContent);
    }

    /**
     * Verify resume page heading is displayed
     */
    public boolean isResumeHeadingDisplayed() {
        return isElementDisplayed(pageHeading);
    }

    /**
     * Verify resume viewer is visible
     */
    public boolean isResumeViewerVisible() {
        return isElementDisplayed(resumeViewer) || isElementDisplayed(pdfObject);
    }

    /**
     * Verify download link is visible
     */
    public boolean isDownloadLinkVisible() {
        return isElementDisplayed(downloadButton);
    }

    /**
     * Click on download button
     */
    public ResumePage clickDownloadButton() {
        click(downloadButton);
        logger.info("Clicked on download button");
        return this;
    }

    /**
     * Get download link href
     */
    public String getDownloadLinkHref() {
        return toAbsoluteUrl(getAttribute(pdfObject, "data"));
    }

    /**
     * Verify download link is valid
     */
    public boolean isDownloadLinkValid() {
        String href = getDownloadLinkHref();
        return ApiHelper.isLinkValid(href);
    }

    /**
     * Check PDF file details
     */
    public Map<String, Object> checkPdfDetails() {
        String pdfUrl = getDownloadLinkHref();
        if (pdfUrl.startsWith("/")) {
            pdfUrl = BASE_URL + pdfUrl;
        }
        return ApiHelper.checkPdfFile(pdfUrl);
    }

    /**
     * Get page heading text
     */
    public String getPageHeadingText() {
        return getText(pageHeading);
    }

    /**
     * Verify current URL is resume page
     */
    public boolean isUrlCorrect() {
        String currentUrl = getCurrentUrl();
        return currentUrl.endsWith("/resume");
    }

    /**
     * Get page title
     */
    public String getResumePageTitle() {
        return getPageTitle();
    }
}
