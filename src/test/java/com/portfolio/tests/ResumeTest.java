package com.portfolio.tests;

import com.portfolio.base.BaseTest;
import com.portfolio.base.DriverManager;
import com.portfolio.pages.HomePage;
import com.portfolio.pages.ResumePage;
import com.portfolio.utilities.ApiHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class ResumeTest extends BaseTest {

    @Test
    public void testResumePageLoads() {
        ResumePage resumePage = new ResumePage(DriverManager.getDriver());
        resumePage.navigateToResume();
        Assert.assertTrue(resumePage.isResumePageLoaded(), "Resume page should be loaded");
    }

    @Test
    public void testResumePageHeadingDisplayed() {
        ResumePage resumePage = new ResumePage(DriverManager.getDriver());
        resumePage.navigateToResume();
        Assert.assertTrue(resumePage.isResumeHeadingDisplayed(), "Resume page heading should be displayed");
    }

    @Test
    public void testResumeViewerVisible() {
        ResumePage resumePage = new ResumePage(DriverManager.getDriver());
        resumePage.navigateToResume();
        Assert.assertTrue(resumePage.isResumeViewerVisible(), "Resume viewer should be visible");
    }

    @Test
    public void testDownloadLinkVisible() {
        ResumePage resumePage = new ResumePage(DriverManager.getDriver());
        resumePage.navigateToResume();
        Assert.assertTrue(resumePage.isDownloadLinkVisible(), "Download link should be visible");
    }

    @Test
    public void testDownloadLinkValid() {
        ResumePage resumePage = new ResumePage(DriverManager.getDriver());
        resumePage.navigateToResume();
        Assert.assertTrue(resumePage.isDownloadLinkValid(), "Download link should be valid");
    }

    @Test
    public void testResumePageUrl() {
        ResumePage resumePage = new ResumePage(DriverManager.getDriver());
        resumePage.navigateToResume();
        Assert.assertTrue(resumePage.isUrlCorrect(), "Resume page URL should contain /resume");
    }

    @Test
    public void testNavigateToResumeFromHome() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHome();
        ResumePage resumePage = homePage.clickResumeLink();
        Assert.assertTrue(resumePage.isResumePageLoaded(), "Should navigate to resume page");
        Assert.assertTrue(resumePage.isResumeHeadingDisplayed(), "Resume page heading should be visible");
    }

    @Test
    public void testResumePdfDetails() {
        ResumePage resumePage = new ResumePage(DriverManager.getDriver());
        resumePage.navigateToResume();
        Map<String, Object> pdfInfo = resumePage.checkPdfDetails();
        Assert.assertNotNull(pdfInfo, "PDF info should not be null");
        Assert.assertEquals(pdfInfo.get("isValid"), Boolean.TRUE, "Resume PDF should be valid");
    }

    @Test
    public void testResumePageReachable() {
        Assert.assertTrue(ApiHelper.isUrlReachable(BASE_URL + "/resume"), "Resume page should be reachable");
    }

    @Test
    public void testResumePageHeadingText() {
        ResumePage resumePage = new ResumePage(DriverManager.getDriver());
        resumePage.navigateToResume();
        String headingText = resumePage.getPageHeadingText();
        Assert.assertNotNull(headingText, "Resume page heading should have text");
        Assert.assertTrue(headingText.contains("Resume"), "Heading should contain 'Resume'");
    }
}
