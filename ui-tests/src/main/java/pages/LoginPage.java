package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import common.config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Paths;

public class LoginPage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private final Page page;

    // ✅ Locators
    private final String usernameField = "#user-name";
    private final String passwordField = "#password";
    private final String loginButton = "#login-button";
    private final String errorMessage = "[data-test='error']";
    private final String loginPageLoadedElement = ".login-box";

    public LoginPage(Page page) {
        this.page = page;
    }

    // ✅ Navigate to Login Page
    public void navigateToLoginPage() {
        try {
            logger.info("🔄 Navigating to SauceDemo login page...");
            page.navigate(ConfigReader.getUiBaseUrl());
            page.waitForSelector(loginPageLoadedElement, new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));
            logger.info("✅ Successfully loaded the SauceDemo login page.");
        } catch (Exception e) {
            captureScreenshot("debug-login-page-load.png");
            logger.error("❌ ERROR: Login page did not load properly. Screenshot: debug-login-page-load.png", e);
        }
    }

    // ✅ Enter Username
    public void enterUsername(String username) {
        try {
            logger.info("📝 Entering username: {}", username);
            page.fill(usernameField, username);
        } catch (Exception e) {
            captureScreenshot("debug-login-username.png");
            logger.error("❌ ERROR: Unable to enter username. Screenshot: debug-login-username.png", e);
        }
    }

    // ✅ Enter Password
    public void enterPassword(String password) {
        try {
            logger.info("🔑 Entering password.");
            page.fill(passwordField, password);
        } catch (Exception e) {
            captureScreenshot("debug-login-password.png");
            logger.error("❌ ERROR: Unable to enter password. Screenshot: debug-login-password.png", e);
        }
    }

    // ✅ Click Login Button
    public void clickLogin() {
        try {
            logger.info("🔄 Clicking the login button...");
            page.click(loginButton);
            logger.info("✅ Login button clicked.");
        } catch (Exception e) {
            captureScreenshot("debug-login-click.png");
            logger.error("❌ ERROR: Login button is missing or not clickable. Screenshot: debug-login-click.png", e);
        }
    }

    // ✅ Check If Login Error Message is Displayed
    public boolean isErrorMessageDisplayed() {
        try {
            if (page.isVisible(errorMessage)) {
                String errorText = page.textContent(errorMessage).trim();
                logger.warn("⚠ Login error displayed: {}", errorText);
                return true;
            } else {
                logger.info("✅ No login error message displayed.");
                return false;
            }
        } catch (Exception e) {
            captureScreenshot("debug-login-error.png");
            logger.error("❌ ERROR: Unable to check login error message. Screenshot: debug-login-error.png", e);
            return false;
        }
    }

    // ✅ Utility: Capture Screenshot for Debugging
    private void captureScreenshot(String fileName) {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileName)));
    }
}
