package com.portfolio.tests;

import com.portfolio.base.BaseTest;
import com.portfolio.base.DriverManager;
import com.portfolio.pages.AboutPage;
import com.portfolio.pages.HomePage;
import com.portfolio.utilities.ApiHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AboutTest extends BaseTest {

    @Test
    public void testAboutPageLoads() {
        AboutPage aboutPage = new AboutPage(DriverManager.getDriver());
        aboutPage.navigateToAbout();
        Assert.assertTrue(aboutPage.isAboutPageLoaded(), "About page should be loaded");
    }

    @Test
    public void testAboutPageHeadingDisplayed() {
        AboutPage aboutPage = new AboutPage(DriverManager.getDriver());
        aboutPage.navigateToAbout();
        Assert.assertTrue(aboutPage.isAboutHeadingDisplayed(), "About page heading should be displayed");
    }

    @Test
    public void testIntroSectionVisible() {
        AboutPage aboutPage = new AboutPage(DriverManager.getDriver());
        aboutPage.navigateToAbout();
        Assert.assertTrue(aboutPage.isIntroSectionVisible(), "Intro section should be visible");
    }

    @Test
    public void testHighlightsVisible() {
        AboutPage aboutPage = new AboutPage(DriverManager.getDriver());
        aboutPage.navigateToAbout();
        Assert.assertTrue(aboutPage.areHighlightsVisible(), "Highlight cards should be visible");
    }

    @Test
    public void testAboutPageUrl() {
        AboutPage aboutPage = new AboutPage(DriverManager.getDriver());
        aboutPage.navigateToAbout();
        Assert.assertTrue(aboutPage.isUrlCorrect(), "About page URL should contain /about");
    }

    @Test
    public void testNavigateToAboutFromHome() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        AboutPage aboutPage = homePage.clickAboutLink();
        Assert.assertTrue(aboutPage.isAboutPageLoaded(), "Should navigate to about page");
    }

    @Test
    public void testAboutPageHeadingText() {
        AboutPage aboutPage = new AboutPage(DriverManager.getDriver());
        aboutPage.navigateToAbout();
        String headingText = aboutPage.getPageHeadingText();
        Assert.assertNotNull(headingText, "About page heading should have text");
        Assert.assertTrue(headingText.toLowerCase().contains("about"), "Heading should contain 'about'");
    }

    @Test
    public void testAboutPageReachable() {
        Assert.assertTrue(ApiHelper.isUrlReachable(BASE_URL + "/about"), "About page should be reachable");
    }
}
