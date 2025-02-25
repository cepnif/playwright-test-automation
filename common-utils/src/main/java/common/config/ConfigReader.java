package common.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();
    private static final String CONFIG_PATH = System.getProperty("user.dir") + "/../config/config.properties";

    static {
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("⚠️ Error loading config.properties from: " + CONFIG_PATH, e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("api.base.url.test");
    }
}
