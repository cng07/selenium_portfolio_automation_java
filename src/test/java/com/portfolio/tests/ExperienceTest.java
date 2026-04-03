package com.portfolio.tests;

import com.portfolio.base.BaseTest;
import com.portfolio.base.DriverManager;
import com.portfolio.pages.ExperiencePage;
import com.portfolio.pages.HomePage;
import com.portfolio.utilities.ApiHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExperienceTest extends BaseTest {

    @Test
    public void testExperiencePageLoads() {
        ExperiencePage experiencePage = new ExperiencePage(DriverManager.getDriver());
        experiencePage.navigateToExperience();
        Assert.assertTrue(experiencePage.isExperiencePageLoaded(), "Experience page should be loaded");
    }

    @Test
    public void testExperiencePageHeadingDisplayed() {
        ExperiencePage experiencePage = new ExperiencePage(DriverManager.getDriver());
        experiencePage.navigateToExperience();
        Assert.assertTrue(experiencePage.isExperienceHeadingDisplayed(), "Experience page heading should be displayed");
    }

    @Test
    public void testExperienceEntriesDisplayed() {
        ExperiencePage experiencePage = new ExperiencePage(DriverManager.getDriver());
        experiencePage.navigateToExperience();
        Assert.assertTrue(experiencePage.areExperiencesDisplayed(), "Experience entries should be displayed");
    }

    @Test
    public void testExperienceCountGreaterThanZero() {
        ExperiencePage experiencePage = new ExperiencePage(DriverManager.getDriver());
        experiencePage.navigateToExperience();
        Assert.assertTrue(experiencePage.getExperienceCount() > 0, "Should have at least one experience entry");
    }

    @Test
    public void testExperiencePageUrl() {
        ExperiencePage experiencePage = new ExperiencePage(DriverManager.getDriver());
        experiencePage.navigateToExperience();
        Assert.assertTrue(experiencePage.isUrlCorrect(), "Experience page URL should contain /experience");
    }

    @Test
    public void testNavigateToExperienceFromHome() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        ExperiencePage experiencePage = homePage.clickExperienceLink();
        Assert.assertTrue(experiencePage.isExperiencePageLoaded(), "Should navigate to experience page");
    }

    @Test
    public void testExperiencePageHeadingText() {
        ExperiencePage experiencePage = new ExperiencePage(DriverManager.getDriver());
        experiencePage.navigateToExperience();
        String headingText = experiencePage.getPageHeadingText();
        Assert.assertNotNull(headingText, "Experience page heading should have text");
        Assert.assertTrue(headingText.toLowerCase().contains("experience"), "Heading should contain 'experience'");
    }

    @Test
    public void testJobTitlesDisplayed() {
        ExperiencePage experiencePage = new ExperiencePage(DriverManager.getDriver());
        experiencePage.navigateToExperience();
        Assert.assertTrue(experiencePage.areJobTitlesDisplayed(), "Job titles should be displayed");
    }

    @Test
    public void testExperiencePageReachable() {
        Assert.assertTrue(ApiHelper.isUrlReachable(BASE_URL + "/experience"), "Experience page should be reachable");
    }
}
