package com.portfolio.pages;

import com.portfolio.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    // Locators
    private final By mainContent = By.id("main-content");
    private final By skipToContent = By.cssSelector("a.skip-link[href='#main-content']");
    private final By navBar = By.xpath("//nav[@aria-label='Primary']");
    private final By homeLink = By.linkText("Home");
    private final By aboutLink = By.linkText("About");
    private final By projectsLink = By.linkText("Projects");
    private final By resumeLink = By.linkText("Resume");
    private final By contactLink = By.linkText("Contact");
    private final By moreButton = By.xpath("//button[normalize-space()='More']");
    private final By experienceLink = By.xpath("//*[@role='menuitem' and normalize-space()='Experience']");
    private final By educationLink = By.xpath("//*[@role='menuitem' and normalize-space()='Education']");
    private final By certificationsLink = By.xpath("//*[@role='menuitem' and normalize-space()='Certifications']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to home page
     */
    public HomePage navigateToHome() {
        navigateTo(BASE_URL);
        logger.info("Navigated to Home Page");
        return this;
    }

    /**
     * Verify home page is loaded
     */
    public boolean isHomePageLoaded() {
        return isElementDisplayed(mainContent);
    }

    /**
     * Verify skip to content link is visible
     */
    public boolean isSkipToContentVisible() {
        return getElementCount(skipToContent) > 0;
    }

    /**
     * Verify navigation bar is visible
     */
    public boolean isNavBarVisible() {
        return isElementDisplayed(navBar);
    }

    /**
     * Click on About link
     */
    public AboutPage clickAboutLink() {
        click(aboutLink);
        waitForUrlContains("/about");
        logger.info("Clicked on About link");
        return new AboutPage(driver);
    }

    /**
     * Click on Projects link
     */
    public ProjectsPage clickProjectsLink() {
        click(projectsLink);
        waitForUrlContains("/projects");
        logger.info("Clicked on Projects link");
        return new ProjectsPage(driver);
    }

    /**
     * Click on Resume link
     */
    public ResumePage clickResumeLink() {
        click(resumeLink);
        waitForUrlContains("/resume");
        logger.info("Clicked on Resume link");
        return new ResumePage(driver);
    }

    /**
     * Click on Experience link
     */
    public ExperiencePage clickExperienceLink() {
        openMoreMenu();
        click(experienceLink);
        waitForUrlContains("/experience");
        logger.info("Clicked on Experience link");
        return new ExperiencePage(driver);
    }

    /**
     * Click on Education link
     */
    public EducationPage clickEducationLink() {
        openMoreMenu();
        click(educationLink);
        waitForUrlContains("/education");
        logger.info("Clicked on Education link");
        return new EducationPage(driver);
    }

    /**
     * Click on Contact link
     */
    public ContactPage clickContactLink() {
        click(contactLink);
        waitForUrlContains("/contact");
        logger.info("Clicked on Contact link");
        return new ContactPage(driver);
    }

    /**
     * Click on Certifications link
     */
    public CertificationsPage clickCertificationsLink() {
        openMoreMenu();
        click(certificationsLink);
        waitForUrlContains("/certifications");
        logger.info("Clicked on Certifications link");
        return new CertificationsPage(driver);
    }

    /**
     * Get page title
     */
    public String getHomePageTitle() {
        return getPageTitle();
    }

    /**
     * Get current URL
     */
    public String getHomePageUrl() {
        return getCurrentUrl();
    }

    /**
     * Verify home page URL is correct
     */
    public boolean isUrlCorrect() {
        String currentUrl = getCurrentUrl();
        return currentUrl.equals(BASE_URL) || currentUrl.equals(BASE_URL + "/");
    }

    /**
     * Verify home link is visible
     */
    public boolean isHomeLinkVisible() {
        return isElementDisplayed(homeLink);
    }

    private void openMoreMenu() {
        if (!isElementVisibleNow(experienceLink) || !isElementVisibleNow(educationLink)) {
            click(moreButton);
            waitForElement(experienceLink);
        }
    }
}
