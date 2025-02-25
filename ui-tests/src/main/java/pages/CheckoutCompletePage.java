package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Paths;

public class CheckoutCompletePage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutCompletePage.class);
    private final Page page;
    private final Locator backHomeButton;

    public CheckoutCompletePage(Page page) {
        this.page = page;
        this.backHomeButton = page.locator("#back-to-products");
    }

    // ✅ Click 'Back Home' with logging and error handling
    public void clickBackHome() {
        logger.info("🔙 Navigating back to the home page...");
        try {
            // ✅ Wait for the button to be visible
            page.waitForSelector("#back-to-products", new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));
            logger.info("✅ 'Back Home' button is visible.");

            // ✅ Click on the button
            backHomeButton.click();
            logger.info("✅ Successfully navigated back to the home page.");

        } catch (Exception e) {
            // ✅ Capture screenshot on failure
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("debug-back-home.png")));
            logger.error("❌ ERROR: 'Back Home' button is missing or unclickable. Screenshot saved: debug-back-home.png", e);
            throw e; // ✅ Re-throw exception for test failure tracking
        }
    }
}
