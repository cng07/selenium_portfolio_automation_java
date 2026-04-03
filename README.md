# Portfolio Automation Testing - Selenium Java

Automated UI and link-validation tests for `https://carlosng07.vercel.app` using Selenium WebDriver, Java, and TestNG.

## Overview

- Page Object Model structure for maintainable test code
- Browser support for Chrome, Firefox, and Edge
- TestNG execution with Surefire reports
- HTTP helpers for URL and PDF validation
- Log4j2 logging
- Configurable runtime through Maven system properties

## Project Structure

```text
selenium_portfolio_automation_java/
|-- src/
|   |-- main/
|   |   |-- java/com/portfolio/
|   |   |   |-- base/
|   |   |   |   |-- BasePage.java
|   |   |   |   `-- DriverManager.java
|   |   |   |-- pages/
|   |   |   |   |-- AboutPage.java
|   |   |   |   |-- CertificationsPage.java
|   |   |   |   |-- ContactPage.java
|   |   |   |   |-- EducationPage.java
|   |   |   |   |-- ExperiencePage.java
|   |   |   |   |-- HomePage.java
|   |   |   |   |-- ProjectsPage.java
|   |   |   |   `-- ResumePage.java
|   |   |   `-- utilities/
|   |   |       `-- ApiHelper.java
|   |   `-- resources/
|   |       `-- log4j2.xml
|   `-- test/
|       `-- java/com/portfolio/
|           |-- base/
|           |   `-- BaseTest.java
|           `-- tests/
|               |-- AboutTest.java
|               |-- CertificationsTest.java
|               |-- ContactTest.java
|               |-- EducationTest.java
|               |-- ExperienceTest.java
|               |-- HomeTest.java
|               |-- ProjectsTest.java
|               `-- ResumeTest.java
|-- pom.xml
|-- testng.xml
`-- README.md
```

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- A supported browser installed locally

## Dependencies

- Selenium `4.25.0`
- TestNG `7.10.2`
- Log4j2 `2.23.1`
- Apache HttpClient5 `5.3.1`

## Installation

```bash
mvn clean install
```

## Running Tests

Run the full suite:

```bash
mvn test
```

Run a single class:

```bash
mvn -Dtest=HomeTest test
```

Run a single method:

```bash
mvn -Dtest=HomeTest#testHomePageLoads test
```

Run against a different browser:

```bash
mvn -Dbrowser=firefox test
```

Run against a different base URL:

```bash
mvn -Dbase.url=https://example.com test
```

Disable headless mode:

```bash
mvn -Dheadless=false test
```

Adjust waits:

```bash
mvn -Dtimeout.seconds=15 -Dshort.timeout.seconds=5 test
```

## Test Coverage

- `HomeTest`: home page load, navigation, accessibility smoke checks
- `AboutTest`: about-page content and highlight cards
- `ProjectsTest`: project listing and repository links
- `ResumeTest`: resume page, PDF object, and download target
- `ContactTest`: contact page content and contact methods
- `ExperienceTest`: experience entries and job titles
- `EducationTest`: education entries and school names
- `CertificationsTest`: certification listing and navigation

## Reports And Logs

Surefire reports:

```text
target/surefire-reports/
```

HTML report:

```text
target/surefire-reports/index.html
```

Log file:

```text
logs/test-automation.log
```

## Configuration Notes

- `BaseTest` reads `base.url` and `browser` from JVM system properties.
- `DriverManager` reads optional overrides such as `webdriver.chrome.driver`, `webdriver.edge.driver`, `webdriver.gecko.driver`, and `chrome.binary`.
- Chrome and Edge runs use isolated temporary browser profiles to avoid local profile conflicts.
- Headless mode defaults to `true`.

## Troubleshooting

Browser startup issues:

- Verify the target browser is installed.
- If needed, provide the driver path explicitly with JVM properties or environment variables.
- If Chrome discovery is unreliable on your machine, provide `-Dchrome.binary=...`.

Test failures:

- Review `logs/test-automation.log`.
- Review `target/surefire-reports/index.html`.
- Confirm the target site is reachable and the page content has not changed.

## References

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/doc/)
- [Log4j2 Documentation](https://logging.apache.org/log4j/2.x/)
