package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Paths;

public class CheckoutStepTwoPage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutStepTwoPage.class);
    private final Page page;
    private final Locator finishButton;

    public CheckoutStepTwoPage(Page page) {
        this.page = page;
        this.finishButton = page.locator("#finish");
    }

    // ✅ Click Finish Button
    public void clickFinish() {
        logger.info("⏩ Clicking 'Finish' button on Checkout Step Two...");

        try {
            page.waitForSelector("#finish", new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));
            finishButton.click();
            logger.info("✅ Successfully clicked 'Finish' button.");
        } catch (Exception e) {
            captureScreenshot("debug-checkout-finish.png");
            logger.error("❌ ERROR: 'Finish' button is missing or not clickable. Screenshot saved: debug-checkout-finish.png", e);
            throw e; // ✅ Propagate error to fail the test
        }
    }

    // ✅ Utility: Capture Screenshot for Debugging
    private void captureScreenshot(String fileName) {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileName)));
    }
}
