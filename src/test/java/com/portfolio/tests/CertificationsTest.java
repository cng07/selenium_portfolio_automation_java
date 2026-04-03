package com.portfolio.tests;

import com.portfolio.base.BaseTest;
import com.portfolio.base.DriverManager;
import com.portfolio.pages.CertificationsPage;
import com.portfolio.pages.HomePage;
import com.portfolio.utilities.ApiHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CertificationsTest extends BaseTest {

    @Test
    public void testCertificationsPageLoads() {
        CertificationsPage certificationsPage = new CertificationsPage(DriverManager.getDriver());
        certificationsPage.navigateToCertifications();
        Assert.assertTrue(certificationsPage.isCertificationsPageLoaded(), "Certifications page should be loaded");
    }

    @Test
    public void testCertificationsPageHeadingDisplayed() {
        CertificationsPage certificationsPage = new CertificationsPage(DriverManager.getDriver());
        certificationsPage.navigateToCertifications();
        Assert.assertTrue(certificationsPage.isCertificationsHeadingDisplayed(), "Certifications page heading should be displayed");
    }

    @Test
    public void testCertificationsDisplayed() {
        CertificationsPage certificationsPage = new CertificationsPage(DriverManager.getDriver());
        certificationsPage.navigateToCertifications();
        Assert.assertTrue(certificationsPage.areCertificationsDisplayed(), "Certifications should be displayed");
    }

    @Test
    public void testCertificationCountGreaterThanZero() {
        CertificationsPage certificationsPage = new CertificationsPage(DriverManager.getDriver());
        certificationsPage.navigateToCertifications();
        Assert.assertTrue(certificationsPage.getCertificationCount() > 0, "Should have at least one certification entry");
    }

    @Test
    public void testCertificationsPageUrl() {
        CertificationsPage certificationsPage = new CertificationsPage(DriverManager.getDriver());
        certificationsPage.navigateToCertifications();
        Assert.assertTrue(certificationsPage.isUrlCorrect(), "Certifications page URL should contain /certifications");
    }

    @Test
    public void testCertificationsPageHeadingText() {
        CertificationsPage certificationsPage = new CertificationsPage(DriverManager.getDriver());
        certificationsPage.navigateToCertifications();
        String headingText = certificationsPage.getPageHeadingText();
        Assert.assertNotNull(headingText, "Certifications page heading should have text");
        Assert.assertTrue(headingText.contains("Certifications"), "Heading should contain 'Certifications'");
    }

    @Test
    public void testCertificationTitlesDisplayed() {
        CertificationsPage certificationsPage = new CertificationsPage(DriverManager.getDriver());
        certificationsPage.navigateToCertifications();
        Assert.assertTrue(certificationsPage.areCertTitlesDisplayed(), "Certification titles should be displayed");
    }

    @Test
    public void testNavigateToCertificationsFromHome() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        CertificationsPage certificationsPage = homePage.clickCertificationsLink();
        Assert.assertTrue(certificationsPage.isCertificationsPageLoaded(), "Should navigate to certifications page");
    }

    @Test
    public void testCertificationsPageReachable() {
        Assert.assertTrue(ApiHelper.isUrlReachable(BASE_URL + "/certifications"), "Certifications page should be reachable");
    }
}
