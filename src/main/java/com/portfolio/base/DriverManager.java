package com.portfolio.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Stream;

public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Path> profileDirectoryThreadLocal = new ThreadLocal<>();
    private static final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("headless", "true"));

    private DriverManager() {
    }

    public static void initDriver(String browserType) {
        WebDriver driver;

        switch (browserType.toLowerCase(Locale.ROOT)) {
            case "chrome":
                driver = createChromeDriver();
                break;
            case "firefox":
                driver = createFirefoxDriver();
                break;
            case "edge":
                driver = createEdgeDriver();
                break;
            default:
                logger.warn("Browser not supported: {}. Defaulting to Chrome", browserType);
                driver = createChromeDriver();
                break;
        }

        if (HEADLESS) {
            driver.manage().window().setSize(new Dimension(1440, 900));
        } else {
            driver.manage().window().maximize();
        }

        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
            driverThreadLocal.remove();
        }

        cleanupProfileDirectory();
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        configureChromiumOptions(options);
        configureTemporaryProfile(options);

        Path chromeDriverPath = resolveChromeDriverPath();
        Path chromeBinaryPath = resolveChromeBinaryPath(chromeDriverPath);
        if (chromeBinaryPath != null) {
            options.setBinary(chromeBinaryPath.toString());
            logger.info("Using Chrome binary: {}", chromeBinaryPath);
        }

        if (chromeDriverPath != null) {
            logger.info("Using ChromeDriver executable: {}", chromeDriverPath);
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(chromeDriverPath.toFile())
                    .build();
            return new ChromeDriver(service, options);
        }

        logger.info("Launching Chrome with Selenium Manager fallback");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("network.proxy.type", 0);
        if (HEADLESS) {
            options.addArguments("-headless");
        }

        String geckoDriverPath = readConfigValue("webdriver.gecko.driver", "GECKODRIVER");
        if (geckoDriverPath != null) {
            System.setProperty("webdriver.gecko.driver", geckoDriverPath);
            logger.info("Using GeckoDriver executable: {}", geckoDriverPath);
        }

        logger.info("Launching Firefox");
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        configureChromiumOptions(options);
        configureTemporaryProfile(options);

        String edgeDriverPath = readConfigValue("webdriver.edge.driver", "EDGEDRIVER");
        if (edgeDriverPath != null) {
            logger.info("Using EdgeDriver executable: {}", edgeDriverPath);
            EdgeDriverService service = new EdgeDriverService.Builder()
                    .usingDriverExecutable(Path.of(edgeDriverPath).toFile())
                    .build();
            return new EdgeDriver(service, options);
        }

        logger.info("Launching Edge with Selenium Manager fallback");
        return new EdgeDriver(options);
    }

    private static void configureChromiumOptions(ChromeOptions options) {
        options.addArguments(
                "--disable-search-engine-choice-screen",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--no-proxy-server",
                "--no-sandbox",
                "--proxy-server=direct://",
                "--proxy-bypass-list=*",
                "--remote-debugging-port=0"
        );
        if (HEADLESS) {
            options.addArguments("--headless=new");
        }
    }

    private static void configureChromiumOptions(EdgeOptions options) {
        options.addArguments(
                "--disable-search-engine-choice-screen",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--no-proxy-server",
                "--no-sandbox",
                "--proxy-server=direct://",
                "--proxy-bypass-list=*",
                "--remote-debugging-port=0"
        );
        if (HEADLESS) {
            options.addArguments("--headless=new");
        }
    }

    private static void configureTemporaryProfile(ChromeOptions options) {
        Path profileDirectory = createTemporaryProfileDirectory("selenium-chrome-profile-");
        if (profileDirectory != null) {
            options.addArguments("--user-data-dir=" + profileDirectory);
        }
    }

    private static void configureTemporaryProfile(EdgeOptions options) {
        Path profileDirectory = createTemporaryProfileDirectory("selenium-edge-profile-");
        if (profileDirectory != null) {
            options.addArguments("--user-data-dir=" + profileDirectory);
        }
    }

    private static Path createTemporaryProfileDirectory(String prefix) {
        try {
            Path profileDirectory = Files.createTempDirectory(prefix);
            profileDirectoryThreadLocal.set(profileDirectory);
            logger.info("Using browser profile directory: {}", profileDirectory);
            return profileDirectory;
        } catch (IOException e) {
            logger.warn("Unable to create temporary browser profile directory: {}", e.getMessage());
            return null;
        }
    }

    private static void cleanupProfileDirectory() {
        Path profileDirectory = profileDirectoryThreadLocal.get();
        if (profileDirectory == null || !Files.exists(profileDirectory)) {
            return;
        }

        try (Stream<Path> paths = Files.walk(profileDirectory)) {
            paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    logger.warn("Unable to delete temporary profile path {}: {}", path, e.getMessage());
                }
            });
        } catch (IOException e) {
            logger.warn("Unable to clean temporary browser profile directory {}: {}", profileDirectory, e.getMessage());
        } finally {
            profileDirectoryThreadLocal.remove();
        }
    }

    private static Path resolveChromeDriverPath() {
        String configuredPath = readConfigValue("webdriver.chrome.driver", "CHROMEDRIVER");
        if (configuredPath != null && Files.exists(Path.of(configuredPath))) {
            return Path.of(configuredPath);
        }

        return findLatestPath(
                Path.of(System.getProperty("user.home"), ".cache", "selenium", "chromedriver", "win64"),
                "chromedriver.exe"
        );
    }

    private static Path resolveChromeBinaryPath(Path chromeDriverPath) {
        String configuredBinary = readConfigValue("chrome.binary", "CHROME_BINARY");
        if (configuredBinary != null && Files.exists(Path.of(configuredBinary))) {
            return Path.of(configuredBinary);
        }

        if (chromeDriverPath != null) {
            Path playwrightBinary = findLatestPlaywrightChromiumBinary();
            if (playwrightBinary != null) {
                return playwrightBinary;
            }
        }

        return firstExistingPath(
                Path.of("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"),
                Path.of("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe")
        );
    }

    private static Path findLatestPlaywrightChromiumBinary() {
        Path playwrightRoot = Path.of(System.getProperty("user.home"), "AppData", "Local", "ms-playwright");
        if (!Files.isDirectory(playwrightRoot)) {
            return null;
        }

        try (Stream<Path> binaries = Files.walk(playwrightRoot)) {
            return binaries
                    .filter(path -> path.getFileName().toString().equalsIgnoreCase("chrome.exe"))
                    .filter(path -> path.toString().contains("chromium-"))
                    .max(Comparator.comparing(Path::toString))
                    .orElse(null);
        } catch (IOException e) {
            logger.warn("Unable to scan Playwright Chromium cache: {}", e.getMessage());
            return null;
        }
    }

    private static String readConfigValue(String propertyName, String envName) {
        String propertyValue = System.getProperty(propertyName);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue;
        }

        String envValue = System.getenv(envName);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        return null;
    }

    private static Path findLatestPath(Path root, String fileName) {
        if (!Files.isDirectory(root)) {
            return null;
        }

        try (Stream<Path> paths = Files.walk(root, 3)) {
            return paths
                    .filter(path -> path.getFileName().toString().equalsIgnoreCase(fileName))
                    .max(Comparator.comparing(Path::toString))
                    .orElse(null);
        } catch (IOException e) {
            logger.warn("Unable to scan {}: {}", root, e.getMessage());
            return null;
        }
    }

    private static Path firstExistingPath(Path... candidates) {
        for (Path candidate : candidates) {
            if (Files.exists(candidate)) {
                return candidate;
            }
        }
        return null;
    }
}
