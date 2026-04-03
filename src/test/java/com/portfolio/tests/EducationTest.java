package com.portfolio.tests;

import com.portfolio.base.BaseTest;
import com.portfolio.base.DriverManager;
import com.portfolio.pages.EducationPage;
import com.portfolio.pages.HomePage;
import com.portfolio.utilities.ApiHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EducationTest extends BaseTest {

    @Test
    public void testEducationPageLoads() {
        EducationPage educationPage = new EducationPage(DriverManager.getDriver());
        educationPage.navigateToEducation();
        Assert.assertTrue(educationPage.isEducationPageLoaded(), "Education page should be loaded");
    }

    @Test
    public void testEducationPageHeadingDisplayed() {
        EducationPage educationPage = new EducationPage(DriverManager.getDriver());
        educationPage.navigateToEducation();
        Assert.assertTrue(educationPage.isEducationHeadingDisplayed(), "Education page heading should be displayed");
    }

    @Test
    public void testEducationEntriesDisplayed() {
        EducationPage educationPage = new EducationPage(DriverManager.getDriver());
        educationPage.navigateToEducation();
        Assert.assertTrue(educationPage.areEducationsDisplayed(), "Education entries should be displayed");
    }

    @Test
    public void testEducationCountGreaterThanZero() {
        EducationPage educationPage = new EducationPage(DriverManager.getDriver());
        educationPage.navigateToEducation();
        Assert.assertTrue(educationPage.getEducationCount() > 0, "Should have at least one education entry");
    }

    @Test
    public void testEducationPageUrl() {
        EducationPage educationPage = new EducationPage(DriverManager.getDriver());
        educationPage.navigateToEducation();
        Assert.assertTrue(educationPage.isUrlCorrect(), "Education page URL should contain /education");
    }

    @Test
    public void testNavigateToEducationFromHome() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        EducationPage educationPage = homePage.clickEducationLink();
        Assert.assertTrue(educationPage.isEducationPageLoaded(), "Should navigate to education page");
    }

    @Test
    public void testEducationPageHeadingText() {
        EducationPage educationPage = new EducationPage(DriverManager.getDriver());
        educationPage.navigateToEducation();
        String headingText = educationPage.getPageHeadingText();
        Assert.assertNotNull(headingText, "Education page heading should have text");
        Assert.assertTrue(headingText.contains("Education"), "Heading should contain 'Education'");
    }

    @Test
    public void testSchoolNamesDisplayed() {
        EducationPage educationPage = new EducationPage(DriverManager.getDriver());
        educationPage.navigateToEducation();
        Assert.assertTrue(educationPage.areSchoolNamesDisplayed(), "School names should be displayed");
    }

    @Test
    public void testEducationPageReachable() {
        Assert.assertTrue(ApiHelper.isUrlReachable(BASE_URL + "/education"), "Education page should be reachable");
    }
}
