package com.portfolio.tests;

import com.portfolio.base.BaseTest;
import com.portfolio.base.DriverManager;
import com.portfolio.pages.HomePage;
import com.portfolio.utilities.ApiHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {

    @Test
    public void testHomePageLoads() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page should be loaded");
    }

    @Test
    public void testSkipToContentLinkVisible() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        Assert.assertTrue(homePage.isSkipToContentVisible(), "Skip to content link should be visible");
    }

    @Test
    public void testNavigationBarVisible() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        Assert.assertTrue(homePage.isNavBarVisible(), "Navigation bar should be visible");
        Assert.assertTrue(homePage.isHomeLinkVisible(), "Home link should be visible");
    }

    @Test
    public void testHomePageUrl() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        Assert.assertTrue(homePage.isUrlCorrect(), "Home page URL should be correct");
    }

    @Test
    public void testHomePageReachable() {
        Assert.assertTrue(ApiHelper.isUrlReachable(BASE_URL), "Home page should be reachable");
    }

    @Test
    public void testNavigationLinks() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        homePage.clickAboutLink();

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.endsWith("/about"), "Should navigate to about page");
    }
}
