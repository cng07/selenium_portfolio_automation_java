package com.portfolio.pages;

import com.portfolio.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProjectsPage extends BasePage {

    // Locators
    private final By mainContent = By.id("main-content");
    private final By pageHeading = By.xpath("//main//h1[normalize-space()='Projects']");
    private final By projectLinks = By.xpath("//main//a[normalize-space()='View Repository']");

    public ProjectsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to projects page
     */
    public ProjectsPage navigateToProjects() {
        navigateTo(BASE_URL + "/projects");
        logger.info("Navigated to Projects Page");
        return this;
    }

    /**
     * Verify projects page is loaded
     */
    public boolean isProjectsPageLoaded() {
        return isElementDisplayed(mainContent);
    }

    /**
     * Verify projects page heading is displayed
     */
    public boolean isProjectsHeadingDisplayed() {
        return isElementDisplayed(pageHeading);
    }

    /**
     * Get number of projects displayed
     */
    public int getProjectCount() {
        waitForElement(pageHeading);
        int projectCount = getElementCount(projectLinks);
        logger.info("Found {} projects", projectCount);
        return projectCount;
    }

    /**
     * Verify projects are displayed
     */
    public boolean areProjectsDisplayed() {
        return getProjectCount() > 0;
    }

    /**
     * Get page heading text
     */
    public String getPageHeadingText() {
        return getText(pageHeading);
    }

    /**
     * Verify current URL is projects page
     */
    public boolean isUrlCorrect() {
        String currentUrl = getCurrentUrl();
        return currentUrl.endsWith("/projects");
    }

    /**
     * Get page title
     */
    public String getProjectsPageTitle() {
        return getPageTitle();
    }

    /**
     * Click on first project
     */
    public ProjectsPage clickFirstProject() {
        click(projectLinks);
        logger.info("Clicked on first project");
        return this;
    }

    /**
     * Get first project repository href
     */
    public String getFirstProjectHref() {
        return getAttribute(projectLinks, "href");
    }
}
