package com.portfolio.tests;

import com.portfolio.base.BaseTest;
import com.portfolio.base.DriverManager;
import com.portfolio.pages.HomePage;
import com.portfolio.pages.ProjectsPage;
import com.portfolio.utilities.ApiHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectsTest extends BaseTest {

    @Test
    public void testProjectsPageLoads() {
        ProjectsPage projectsPage = new ProjectsPage(DriverManager.getDriver());
        projectsPage.navigateToProjects();
        Assert.assertTrue(projectsPage.isProjectsPageLoaded(), "Projects page should be loaded");
    }

    @Test
    public void testProjectsPageHeadingDisplayed() {
        ProjectsPage projectsPage = new ProjectsPage(DriverManager.getDriver());
        projectsPage.navigateToProjects();
        Assert.assertTrue(projectsPage.isProjectsHeadingDisplayed(), "Projects page heading should be displayed");
    }

    @Test
    public void testProjectsDisplayed() {
        ProjectsPage projectsPage = new ProjectsPage(DriverManager.getDriver());
        projectsPage.navigateToProjects();
        Assert.assertTrue(projectsPage.areProjectsDisplayed(), "Projects should be displayed");
    }

    @Test
    public void testProjectCountGreaterThanZero() {
        ProjectsPage projectsPage = new ProjectsPage(DriverManager.getDriver());
        projectsPage.navigateToProjects();
        Assert.assertTrue(projectsPage.getProjectCount() > 0, "Should have at least one project");
    }

    @Test
    public void testProjectsPageUrl() {
        ProjectsPage projectsPage = new ProjectsPage(DriverManager.getDriver());
        projectsPage.navigateToProjects();
        Assert.assertTrue(projectsPage.isUrlCorrect(), "Projects page URL should contain /projects");
    }

    @Test
    public void testNavigateToProjectsFromHome() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        ProjectsPage projectsPage = homePage.clickProjectsLink();
        Assert.assertTrue(projectsPage.isProjectsPageLoaded(), "Should navigate to projects page");
    }

    @Test
    public void testProjectsPageHeadingText() {
        ProjectsPage projectsPage = new ProjectsPage(DriverManager.getDriver());
        projectsPage.navigateToProjects();
        String headingText = projectsPage.getPageHeadingText();
        Assert.assertNotNull(headingText, "Projects page heading should have text");
        Assert.assertTrue(headingText.contains("Projects"), "Heading should contain 'Projects'");
    }

    @Test
    public void testFirstProjectLinkValid() {
        ProjectsPage projectsPage = new ProjectsPage(DriverManager.getDriver());
        projectsPage.navigateToProjects();
        Assert.assertTrue(ApiHelper.isLinkValid(projectsPage.getFirstProjectHref()), "First project link should be valid");
    }

    @Test
    public void testProjectsPageReachable() {
        Assert.assertTrue(ApiHelper.isUrlReachable(BASE_URL + "/projects"), "Projects page should be reachable");
    }
}
