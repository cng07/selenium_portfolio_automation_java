package com.portfolio.tests;

import com.portfolio.base.BaseTest;
import com.portfolio.base.DriverManager;
import com.portfolio.pages.ContactPage;
import com.portfolio.pages.HomePage;
import com.portfolio.utilities.ApiHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContactTest extends BaseTest {

    @Test
    public void testContactPageLoads() {
        ContactPage contactPage = new ContactPage(DriverManager.getDriver());
        contactPage.navigateToContact();
        Assert.assertTrue(contactPage.isContactPageLoaded(), "Contact page should be loaded");
    }

    @Test
    public void testContactPageHeadingDisplayed() {
        ContactPage contactPage = new ContactPage(DriverManager.getDriver());
        contactPage.navigateToContact();
        Assert.assertTrue(contactPage.isContactHeadingDisplayed(), "Contact page heading should be displayed");
    }

    @Test
    public void testContactMethodsVisible() {
        ContactPage contactPage = new ContactPage(DriverManager.getDriver());
        contactPage.navigateToContact();
        Assert.assertTrue(contactPage.areContactMethodsVisible(), "Contact methods should be visible");
    }

    @Test
    public void testEmailLinkVisible() {
        ContactPage contactPage = new ContactPage(DriverManager.getDriver());
        contactPage.navigateToContact();
        Assert.assertTrue(contactPage.isEmailLinkVisible(), "Email link should be visible");
    }

    @Test
    public void testEmailLinkFormat() {
        ContactPage contactPage = new ContactPage(DriverManager.getDriver());
        contactPage.navigateToContact();
        String emailHref = contactPage.getEmailLinkHref();
        Assert.assertTrue(emailHref.startsWith("mailto:"), "Email link should start with 'mailto:'");
    }

    @Test
    public void testContactPageUrl() {
        ContactPage contactPage = new ContactPage(DriverManager.getDriver());
        contactPage.navigateToContact();
        Assert.assertTrue(contactPage.isUrlCorrect(), "Contact page URL should contain /contact");
    }

    @Test
    public void testNavigateToContactFromHome() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        ContactPage contactPage = homePage.clickContactLink();
        Assert.assertTrue(contactPage.isContactPageLoaded(), "Should navigate to contact page");
    }

    @Test
    public void testContactPageHeadingText() {
        ContactPage contactPage = new ContactPage(DriverManager.getDriver());
        contactPage.navigateToContact();
        String headingText = contactPage.getPageHeadingText();
        Assert.assertNotNull(headingText, "Contact page heading should have text");
        Assert.assertTrue(headingText.toLowerCase().contains("touch"), "Heading should contain 'touch'");
    }

    @Test
    public void testContactPageReachable() {
        Assert.assertTrue(ApiHelper.isUrlReachable(BASE_URL + "/contact"), "Contact page should be reachable");
    }

    @Test
    public void testContactMethodCount() {
        ContactPage contactPage = new ContactPage(DriverManager.getDriver());
        contactPage.navigateToContact();
        Assert.assertTrue(contactPage.getContactMethodCount() >= 4, "Should expose multiple contact methods");
    }
}
