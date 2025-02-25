package utilities;

import com.microsoft.playwright.*;

public class PlaywrightManager {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    public static void startPlaywright(String browserType) {
        if (playwright == null) {
            playwright = Playwright.create();

            switch (browserType.toLowerCase()) {
                case "firefox":
                    browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                    break;
                case "webkit":  // ✅ WebKit (Safari)
                    browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                    break;
                case "chrome":
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
                    break;
                default:  // ✅ Default to Chromium (if browser is not specified)
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                    break;
            }

            context = browser.newContext();
            page = context.newPage();
        }
    }

    public static Page getPage() {
        if (page == null) {
            throw new RuntimeException("❌ ERROR: Playwright Page is NULL. Ensure Playwright is started before tests.");
        }
        return page;
    }

    public static void closePlaywright() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
            browser = null;
            context = null;
            page = null;
        }
    }
}
