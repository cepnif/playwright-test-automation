package common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader is a utility class for reading configuration properties.
 * It loads properties from `config.properties` and provides methods to retrieve values.
 */
public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static final Properties properties = new Properties();
    private static final String CONFIG_PATH = System.getProperty("user.dir") + "/../config/config.properties";

    static {
        loadProperties();
    }

    /**
     * Loads the configuration properties from the config file.
     */
    private static void loadProperties() {
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH)) {
            properties.load(fileInputStream);
            logger.info("✅ Successfully loaded configuration from {}", CONFIG_PATH);
        } catch (IOException e) {
            logger.error("❌ ERROR: Failed to load config.properties from {}", CONFIG_PATH, e);
            throw new RuntimeException("⚠️ Error loading config.properties from: " + CONFIG_PATH, e);
        }
    }

    /**
     * Retrieves the property value for the given key.
     *
     * @param key The property key.
     * @return The property value or null if the key is not found.
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            logger.warn("⚠️ Property '{}' not found in config.properties", key);
        } else {
            logger.debug("🔍 Retrieved property: {} = {}", key, value);
        }
        return value;
    }

    /**
     * Retrieves the API base URL from the config file.
     *
     * @return The API base URL.
     */
    public static String getApiBaseUrl() {
        return getProperty("api.base.url.test");
    }

    /**
     * Retrieves the UI base URL from the config file.
     *
     * @return The UI base URL.
     */
    public static String getUiBaseUrl() {
        return getProperty("ui.base.url.test");
    }
}
